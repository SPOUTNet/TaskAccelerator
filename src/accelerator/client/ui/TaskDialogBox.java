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
package accelerator.client.ui;

import java.util.List;
import java.util.Set;

import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.model.Task;
import accelerator.shared.util.CollectionUtil;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

public class TaskDialogBox extends DialogBox {

    private static TaskDialogBoxUiBinder uiBinder = GWT
        .create(TaskDialogBoxUiBinder.class);

    interface TaskDialogBoxUiBinder extends UiBinder<Widget, TaskDialogBox> {
    }

    public interface Handler {
        public void onOk(Task task);
    }

    public class TagCell extends AbstractCell<Tag> {
        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context,
                Tag value, SafeHtmlBuilder sb) {
            if (value != null) {
                sb.appendEscaped(value.getName());
            }
        }
    }

    /**
     * 新規作成時のタイトル。
     */
    public static final String NEW_TASK_TITLE = "新規作成";

    /**
     * 編集時のタイトル。
     */
    public static final String EDIT_TASK_TITLE = "タスクの編集";

    @UiField
    TextBox nameTextBox;

    @UiField
    Button cancelButton;

    @UiField
    Button okButton;

    @UiField
    ListBox projectListBox;

    @UiField
    Label nameErrorLabel;

    @UiField
    ScrollPanel scrollPanel;

    @UiField
    DateBox dueDateBox;

    /**
     * Task を表示する CellList。
     */
    private final CellList<Tag> tagCellList;

    private final DefaultSelectionEventManager<Tag> selectionManager =
        DefaultSelectionEventManager.createCheckboxManager();

    private final MultiSelectionModel<Tag> selectionModel =
        new MultiSelectionModel<Tag>();

    /**
     * コールバックハンドラ。
     */
    private Handler handler;

    /**
     * ListBox に表示するプロジェクトの一覧。
     */
    private List<Project> projectList;

    /**
     * CellList に表示するタグの一覧。
     */
    private List<Tag> tagList;

    /**
     * ユーザーが入力したタスク情報。
     */
    private Task inputTask = new Task();

    public static TaskDialogBox createDialog(String title,
            List<Project> projects, List<Tag> tags, Handler handler) {
        TaskDialogBox dlg = new TaskDialogBox();
        dlg.setText(title);
        dlg.setProjectList(projects);
        dlg.setTagList(tags);
        dlg.setHandler(handler);
        return dlg;
    }

    public static TaskDialogBox createDialog(String title,
            List<Project> projects, List<Tag> tags, Handler handler, Task target) {
        TaskDialogBox dlg = createDialog(title, projects, tags, handler);
        dlg.initializeFromTask(target);
        return dlg;
    }

    /**
     * コンストラクタ。
     */
    public TaskDialogBox() {
        setWidget(uiBinder.createAndBindUi(this));

        projectListBox.addItem("(なし)", "");

        List<HasCell<Tag, ?>> hasCells = CollectionUtil.createArrayList();
        hasCells.add(new HasCell<Tag, Boolean>() {
            private final CheckboxCell cell = new CheckboxCell();

            public Cell<Boolean> getCell() {
                return cell;
            }

            public FieldUpdater<Tag, Boolean> getFieldUpdater() {
                return null;
            }

            public Boolean getValue(Tag object) {
                return selectionModel.isSelected(object);
            }
        });
        hasCells.add(new HasCell<Tag, Tag>() {
            private final TagCell cell = new TagCell();

            public Cell<Tag> getCell() {
                return cell;
            }

            public FieldUpdater<Tag, Tag> getFieldUpdater() {
                return null;
            }

            public Tag getValue(Tag object) {
                return object;
            }

        });
        CompositeCell<Tag> tagCell = new CompositeCell<Tag>(hasCells);

        tagCellList = new CellList<Tag>(tagCell);
        tagCellList.setSelectionModel(selectionModel, selectionManager);
        scrollPanel.add(tagCellList);

        nameErrorLabel.setVisible(false);
        setText("新規作成");
        setHandler(null);

        // DateBox の初期化
        dueDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat
            .getFormat("yyyy/MM/dd")));
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        nameTextBox.setFocus(true);
    }

    @UiHandler("projectListBox")
    void onProjectListBoxChange(ChangeEvent e) {
        updateButtons();
    }

    @UiHandler("okButton")
    void onOkButtonClick(ClickEvent e) {
        if (validate() == false) {
            return;
        }

        Task input = getInputTask();
        handler.onOk(input);
        hide(true);
    }

    private boolean validate() {
        // タスク名の検証
        boolean validName;
        String name = nameTextBox.getText();
        if ("".equals(name) == false) {
            nameErrorLabel.setText("");
            nameErrorLabel.setVisible(false);
            validName = true;
        } else {
            nameErrorLabel.setText("name が空です。");
            nameErrorLabel.setVisible(true);
            validName = false;
        }

        return validName;
    }

    private Task getInputTask() {
        inputTask.setName(nameTextBox.getText());
        inputTask.setProject(getSelectedProjectKey());
        inputTask.setTags(getSelectedTagKeyList());
        inputTask.setDueDate(dueDateBox.getValue());
        return inputTask;
    }

    @UiHandler("cancelButton")
    void onCancelButtonClick(ClickEvent e) {
        this.hide(true);
    }

    /**
     * ボタンの状態を更新します。
     */
    private void updateButtons() {
        if (projectListBox.getSelectedIndex() < 0) {
            okButton.setEnabled(false);
            return;
        }
        okButton.setEnabled(true);
    }

    /**
     * コールバックハンドラを設定します。
     * 
     * @param handler
     *            コールバックハンドラ。
     */
    public void setHandler(Handler handler) {
        if (handler == null) {
            this.handler = new Handler() {
                public void onOk(Task input) {
                    // 何もしない
                }
            };
        } else {
            this.handler = handler;
        }
    }

    /**
     * タスクをもとにダイアログのコントロールを初期化します。
     * 
     * @param task
     *            表示する情報を格納したタスク。
     */
    public void initializeFromTask(Task task) {
        inputTask = task;

        nameTextBox.setText(task.getName());
        dueDateBox.setValue(task.getDueDate());
        setSelectedProjectKey(task.getProject());

        // 選択されてるタグの初期化
        List<Key> tagKeys = task.getTags();
        if (tagKeys != null && tagKeys.isEmpty() == false) {
            setSelectedTagKeyList(tagKeys);
        }
    }

    /**
     * 選択されているプロジェクトのキーを取得します。
     * 
     * @return 選択されているプロジェクトのキー。
     */
    private Key getSelectedProjectKey() {
        int index = projectListBox.getSelectedIndex();
        if (0 <= index) {
            String value = projectListBox.getValue(index);
            for (Project p : projectList) {
                if (p.getKey().toString().equals(value)) {
                    return p.getKey();
                }
            }
        }
        return null;
    }

    /**
     * 指定したプロジェクトのキーを選択状態にします。
     * 
     * @param projectKey
     *            選択状態にするプロジェクトのキー。
     */
    public void setSelectedProjectKey(Key projectKey) {
        if (projectKey != null) {
            for (int i = 0; i < projectListBox.getItemCount(); i++) {
                String key = projectListBox.getValue(i);
                if (projectKey.toString().equals(key)) {
                    projectListBox.setSelectedIndex(i);
                    return;
                }
            }
        } else {
            // (なし) を選択
            projectListBox.setSelectedIndex(0);
        }
    }

    /**
     * プロジェクトの変更を許可するかどうか指定します。
     * 
     * @param changeProject
     *            プロジェクトの変更を許可するとき true。それ以外のとき false。
     */
    public void setChangeProject(Boolean changeProject) {
        projectListBox.setEnabled(changeProject);
    }

    /**
     * 選択されているタグのキー一覧を取得します。
     * 
     * @return 選択されているタグのキー一覧。
     */
    private List<Key> getSelectedTagKeyList() {
        List<Key> keyList = CollectionUtil.createArrayList();
        Set<Tag> tagSet = selectionModel.getSelectedSet();
        for (Tag tag : tagSet) {
            keyList.add(tag.getKey());
        }
        return keyList;
    }

    /**
     * 選択されているタグのキー一覧を設定します。
     * 
     * @param keys
     */
    private void setSelectedTagKeyList(List<Key> keys) {
        for (Tag tag : tagList) {
            Key tagKey = tag.getKey();
            Boolean isSelected = keys.contains(tagKey);
            selectionModel.setSelected(tag, isSelected);
        }
    }

    /**
     * 表示するプロジェクトのリストを設定します。
     * 
     * @param projects
     *            表示するプロジェクトのリスト。
     */
    public void setProjectList(List<Project> projects) {
        this.projectList = projects;

        this.projectListBox.clear();
        this.projectListBox.addItem("(なし)", "");
        for (Project p : this.projectList) {
            this.projectListBox.addItem(p.getName(), p.getKey().toString());
        }
    }

    /**
     * 表示するタグのリストを設定します。
     * 
     * @param tags
     *            表示するタグのリスト。
     */
    public void setTagList(List<Tag> tags) {
        this.tagList = tags;
        this.tagCellList.setRowData(this.tagList);
    }
}
