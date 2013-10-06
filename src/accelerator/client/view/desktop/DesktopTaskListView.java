/**
 * Task Accelerator - Full ajax task management application run on Google App Engine -
 * Copyright (C) 2011 tnakamura
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation;
 * either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package accelerator.client.view.desktop;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import accelerator.client.presenter.InboxPresenter;
import accelerator.client.presenter.ProjectPresenter;
import accelerator.client.ui.TaskDialogBox;
import accelerator.client.ui.cell.IconButtonCell;
import accelerator.client.ui.cell.StyleCompositeCell;
import accelerator.client.ui.widget.IconButton;
import accelerator.client.ui.widget.JQueryUI;
import accelerator.client.view.TaskListView;
import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;
import accelerator.shared.util.CollectionUtil;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/**
 * タスク一覧を表示するビュー。
 */
public class DesktopTaskListView extends Composite implements TaskListView {

    private static TaskListViewUiBinder uiBinder = GWT
        .create(TaskListViewUiBinder.class);

    interface TaskListViewUiBinder extends
            UiBinder<Widget, DesktopTaskListView> {
    }

    /**
     * CompositCell 用の HasCell のリストを生成します。
     * 
     * @param handler
     *            タスクの操作を要求するためのコールバック。
     * @return HasCell のリスト。
     */
    private List<HasCell<Task, ?>> createHasCellList() {
        List<HasCell<Task, ?>> hasCellList = CollectionUtil.createArrayList();
        // 完了・未完了を表示するチェックボックスを追加
        hasCellList.add(new HasCell<Task, Boolean>() {
            private final CheckboxCell cell = new CheckboxCell();

            public Cell<Boolean> getCell() {
                return cell;
            }

            public FieldUpdater<Task, Boolean> getFieldUpdater() {
                return new FieldUpdater<Task, Boolean>() {
                    public void update(int index, Task object, Boolean value) {
                        object.setCompleted(value);
                        DesktopTaskListView.this.presenter.updateTask(object);
                    }
                };
            }

            public Boolean getValue(Task object) {
                return object.getCompleted();
            }

        });
        // プロジェクトを表示するセルを追加
        hasCellList.add(new HasCell<Task, SafeHtml>() {
            private final SafeHtmlCell cell = new SafeHtmlCell();

            public Cell<SafeHtml> getCell() {
                return cell;
            }

            public FieldUpdater<Task, SafeHtml> getFieldUpdater() {
                return null;
            }

            public SafeHtml getValue(Task object) {
                SafeHtmlBuilder sb = new SafeHtmlBuilder();
                
                // 受信箱またはプロジェクトのときは表示しない
                if (presenter instanceof InboxPresenter) {
                    return sb.toSafeHtml();
                } else if (presenter instanceof ProjectPresenter) {
                    return sb.toSafeHtml();
                }
                
                Key projectKey = object.getProject();
                if ((projectKey != null)
                    && projectList.containsKey(projectKey)) {
                    Project p = projectList.get(projectKey);
                    sb.appendHtmlConstant("<span class=\"project\">");
                    sb.appendEscaped(p.getName());
                    sb.appendEscaped(":");
                    sb.appendHtmlConstant("</span>");
                }
                return sb.toSafeHtml();
            }
        });
        // タスク名の追加
        hasCellList.add(new HasCell<Task, SafeHtml>() {
            private final SafeHtmlCell cell = new SafeHtmlCell();

            public Cell<SafeHtml> getCell() {
                return cell;
            }

            public FieldUpdater<Task, SafeHtml> getFieldUpdater() {
                return null;
            }

            public SafeHtml getValue(Task object) {
                SafeHtmlBuilder sb = new SafeHtmlBuilder();
                if (object.getCompleted()) {
                    sb.appendHtmlConstant("<s>");
                    sb.appendEscaped(object.getName());
                    sb.appendHtmlConstant("</s>");
                } else {
                    sb.appendEscaped(object.getName());
                }
                return sb.toSafeHtml();
            }

        });
        // 削除ボタンのセルを追加
        hasCellList.add(new HasCell<Task, String>() {
            private final IconButtonCell cell = new IconButtonCell();

            public Cell<String> getCell() {
                cell.setIcon(JQueryUI.UI_ICON_CLOSE);
                return cell;
            }

            public FieldUpdater<Task, String> getFieldUpdater() {
                return new FieldUpdater<Task, String>() {
                    public void update(int index, Task object, String value) {
                        List<Task> tasks = CollectionUtil.createArrayList();
                        tasks.add(object);
                        DesktopTaskListView.this.presenter.deleteTask(tasks);
                    }
                };
            }

            public String getValue(Task object) {
                return "タスクの削除";
            }
        });
        // 編集ボタンのセルを追加
        hasCellList.add(new HasCell<Task, String>() {
            private final IconButtonCell cell = new IconButtonCell();

            public Cell<String> getCell() {
                cell.setIcon(JQueryUI.UI_ICON_PENCIL);
                return cell;
            }

            public FieldUpdater<Task, String> getFieldUpdater() {
                return new FieldUpdater<Task, String>() {
                    public void update(int index, Task object, String value) {
                        DesktopTaskListView.this.editTask(object);
                    }
                };
            }

            public String getValue(Task object) {
                return "タスクの編集";
            }
        });
        // 期日を表示するセルの追加
        hasCellList.add(new HasCell<Task, SafeHtml>() {
            private final SafeHtmlCell cell = new SafeHtmlCell();
            private final DateTimeFormat format = DateTimeFormat
                .getFormat("yyyy年MM月dd日");

            public Cell<SafeHtml> getCell() {
                return cell;
            }

            public FieldUpdater<Task, SafeHtml> getFieldUpdater() {
                return null;
            }

            public SafeHtml getValue(Task object) {
                SafeHtmlBuilder sb = new SafeHtmlBuilder();
                Date d = object.getDueDate();
                if (d != null) {
                    sb.appendHtmlConstant("<span class=\"duedate\">");
                    sb.appendEscaped(format.format(d));
                    sb.appendHtmlConstant("</span>");
                }
                return sb.toSafeHtml();
            }
        });
        // タグを表示するセルの追加
        hasCellList.add(new HasCell<Task, SafeHtml>() {
            private final SafeHtmlCell cell = new SafeHtmlCell();

            public Cell<SafeHtml> getCell() {
                return cell;
            }

            public FieldUpdater<Task, SafeHtml> getFieldUpdater() {
                return null;
            }

            public SafeHtml getValue(Task object) {
                SafeHtmlBuilder sb = new SafeHtmlBuilder();
                if (DesktopTaskListView.this.tagMap != null) {
                    List<Key> tagKeys = object.getTags();
                    for (Key tagKey : tagKeys) {
                        if (DesktopTaskListView.this.tagMap.containsKey(tagKey)) {
                            Tag tag =
                                DesktopTaskListView.this.tagMap.get(tagKey);
                            sb.appendHtmlConstant("<span class=\"tag\">");
                            sb.appendEscaped(tag.getName());
                            sb.appendHtmlConstant("</span>");
                        }
                    }
                }
                return sb.toSafeHtml();
            }
        });
        return hasCellList;
    }

    /**
     * タスクを表示するためのセル。
     */
    private class TaskCell extends StyleCompositeCell<Task> {
        public TaskCell() {
            super(createHasCellList());
            setStyleName("taskcell");
        }
    }

    /**
     * コールバックハンドラ。
     */
    private Presenter presenter;

    @UiField
    ScrollPanel scrollPanel;

    /**
     * タスク作成ボタン。
     */
    @UiField
    IconButton createTaskButton;

    /**
     * タスク編集ボタン。
     */
    @UiField
    IconButton editTaskButton;

    /**
     * タスク削除ボタン。
     */
    @UiField
    IconButton deleteTaskButton;

    /**
     * TaskDialogBox に表示するプロジェクトのリスト。
     */
    private final Map<Key, Project> projectList = CollectionUtil
        .createHashMap();

    /**
     * TaskDialogBox に表示するタグのリスト。
     */
    private final Map<Key, Tag> tagMap = CollectionUtil.createHashMap();

    /**
     * Task を表示する CellList。
     */
    private final CellList<Task> taskCellList;

    /**
     * タスクを表示するセル。
     */
    private final TaskCell taskCell = new TaskCell();

    /**
     * 選択されたタスクを管理する MultiSelectionModel。
     */
    private final MultiSelectionModel<Task> selectionModel =
        new MultiSelectionModel<Task>();;

    /**
     * コンストラクタ。
     */
    public DesktopTaskListView() {
        initWidget(uiBinder.createAndBindUi(this));

        taskCellList = new CellList<Task>(taskCell);
        taskCellList.setStyleName("taskcelllist", true);
        taskCellList.setSelectionModel(selectionModel);
        scrollPanel.add(taskCellList);

        selectionModel
            .addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
                public void onSelectionChange(SelectionChangeEvent event) {
                    updateToolBar();
                }
            });
    }

    /**
     * {@inheritDoc}
     */
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("createTaskButton")
    void onCreateTaskButtonClick(ClickEvent e) {
        assert projectList != null;
        assert tagMap != null;

        final TaskDialogBox dialog =
            TaskDialogBox.createDialog(
                TaskDialogBox.NEW_TASK_TITLE,
                CollectionUtil.createArrayList(projectList.values()),
                CollectionUtil.createArrayList(tagMap.values()),
                new TaskDialogBox.Handler() {
                    public void onOk(Task input) {
                        presenter.createTask(input);
                    }
                });
        dialog.center();
    }

    /**
     * 編集ボタンをクリックしたときの処理。
     * 
     * @param e
     *            イベントデータ。
     */
    @UiHandler("editTaskButton")
    void onEditTaskButtonClick(ClickEvent e) {
        Set<Task> taskSet = selectionModel.getSelectedSet();
        if (taskSet.isEmpty()) {
            return;
        }

        final List<Task> taskArray = CollectionUtil.createArrayList();
        taskArray.addAll(taskSet);
        editTask(taskArray.get(0));
    }

    private void editTask(Task task) {
        TaskDialogBox dialog =
            TaskDialogBox.createDialog(
                TaskDialogBox.EDIT_TASK_TITLE,
                CollectionUtil.createArrayList(projectList.values()),
                CollectionUtil.createArrayList(tagMap.values()),
                new TaskDialogBox.Handler() {
                    public void onOk(Task input) {
                        presenter.updateTask(input);
                    }
                },
                task);
        dialog.center();
    }

    /**
     * 削除ボタンをクリックしたときの処理。
     * 
     * @param e
     *            イベントデータ。
     */
    @UiHandler("deleteTaskButton")
    void onDeleteTaskButtonClick(ClickEvent e) {
        Set<Task> taskSet = selectionModel.getSelectedSet();
        if (taskSet.isEmpty() == false) {
            this.presenter.deleteTask(taskSet);
        }
    }

    /**
     * ツールバーの状態を更新します。
     */
    private void updateToolBar() {
        Set<Task> taskSet = this.selectionModel.getSelectedSet();
        boolean isTaskSelected = !taskSet.isEmpty();

        editTaskButton.setEnabled(isTaskSelected);
        deleteTaskButton.setEnabled(isTaskSelected);
    }

    /**
     * {@inheritDoc}
     */
    public void setTaskList(List<Task> tasks) {
        selectionModel.clear();
        taskCellList.setRowData(tasks);
    }

    /**
     * {@inheritDoc}
     */
    public void setProjectList(List<Project> projects) {
        projectList.clear();
        for (Project p : projects) {
            projectList.put(p.getKey(), p);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setTagList(List<Tag> tags) {
        tagMap.clear();
        for (Tag t : tags) {
            tagMap.put(t.getKey(), t);
        }
        taskCellList.redraw();
    }
}
