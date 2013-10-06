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

import java.util.List;

import accelerator.client.ui.ProjectDialogBox;
import accelerator.client.ui.TagDialogBox;
import accelerator.client.ui.widget.IconButton;
import accelerator.client.view.MainMenuView;
import accelerator.client.view.desktop.MainMenuTreeViewModel.MainMenuItem;
import accelerator.client.view.desktop.MainMenuTreeViewModel.ProjectMenuItem;
import accelerator.client.view.desktop.MainMenuTreeViewModel.TagMenuItem;
import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class DesktopMainMenuView extends Composite implements MainMenuView {

    private static MainMenuViewUiBinder uiBinder = GWT
        .create(MainMenuViewUiBinder.class);

    interface MainMenuViewUiBinder extends
            UiBinder<Widget, DesktopMainMenuView> {
    }

    @UiField(provided = true)
    CellTree mainMenu;

    @UiField
    IconButton createButton;

    @UiField
    IconButton editButton;

    private final SingleSelectionModel<MainMenuTreeViewModel.MainMenuItem> selectionModel =
        new SingleSelectionModel<MainMenuTreeViewModel.MainMenuItem>();

    private final MainMenuTreeViewModel treeViewModel =
        new MainMenuTreeViewModel(selectionModel);

    private Presenter handler;

    public DesktopMainMenuView() {
        mainMenu = new CellTree(treeViewModel, null);
        mainMenu.setAnimationEnabled(true);
        mainMenu.ensureDebugId("mainMenu");

        initWidget(uiBinder.createAndBindUi(this));

        selectionModel
            .addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
                public void onSelectionChange(SelectionChangeEvent event) {
                    final MainMenuTreeViewModel.MainMenuItem selected =
                        selectionModel.getSelectedObject();
                    Place where =
                        handler
                            .getClientFactory()
                            .getPlaceController()
                            .getWhere();
                    if ((selected != null) && !selected.mapsToPlace(where)) {
                        handler.goTo(selected.getPlace());
                    }
                }
            });
    }

    public void setPresenter(Presenter presenter) {
        this.handler = presenter;

        presenter
            .getClientFactory()
            .getEventBus()
            .addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
                public void onPlaceChange(PlaceChangeEvent event) {
                    Place place = event.getNewPlace();
                    for (MainMenuItem s : treeViewModel.getMainMenuItemList()) {
                        if (s.mapsToPlace(place)) {
                            selectionModel.setSelected(s, true);
                            return;
                        }
                    }
                    selectionModel.setSelected(null, true);
                }
            });
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        assert handler != null : "MainMenuPresenter がセットされていません。";
        handler.loadProjectList();
        handler.loadTagList();

        mainMenu.getRootTreeNode().setChildOpen(0, true);
        mainMenu.getRootTreeNode().setChildOpen(1, true);
        mainMenu.getRootTreeNode().setChildOpen(2, true);
    }

    @UiHandler("createButton")
    void onCreateButtonClick(ClickEvent e) {
        final PopupPanel popup = new PopupPanel(true, false);
        MenuBar menu = new MenuBar(true);
        menu.addItem("プロジェクト作成", new Command() {
            public void execute() {
                popup.hide();
                ProjectDialogBox dlg = new ProjectDialogBox();
                dlg.setHandler(new ProjectDialogBox.Handler() {
                    public void onOk(Project input) {
                        handler.createProject(input);
                    }
                });
                dlg.center();
            }
        });
        menu.addItem("タグ作成", new Command() {
            public void execute() {
                popup.hide();
                TagDialogBox dlg = new TagDialogBox();
                dlg.setHandler(new TagDialogBox.Handler() {
                    public void onOk(Tag input) {
                        handler.createTag(input);
                    }
                });
                dlg.center();
            }
        });
        popup.setWidget(menu);
        popup.setPopupPositionAndShow(new PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
                int left = createButton.getAbsoluteLeft();
                int top = createButton.getAbsoluteTop() - offsetHeight;
                popup.setPopupPosition(left, top);
            }
        });
    }

    @UiHandler("editButton")
    void onEditButtonClick(ClickEvent e) {
        final PopupPanel popup = new PopupPanel(true, false);
        MenuBar menu = new MenuBar(true);

        {
            final Project p = getSelectedProject();
            final boolean isProjectSelected = p != null;

            // プロジェクト編集
            MenuItem edit = new MenuItem("プロジェクト編集", new Command() {
                public void execute() {
                    assert (p != null);
                    popup.hide();
                    ProjectDialogBox dlg = new ProjectDialogBox(p);
                    dlg.setHandler(new ProjectDialogBox.Handler() {
                        public void onOk(Project input) {
                            handler.updateProject(input);
                        }
                    });
                    dlg.center();
                }
            });
            edit.setEnabled(isProjectSelected);
            menu.addItem(edit);

            // プロジェクト削除
            MenuItem delete = new MenuItem("プロジェクト削除", new Command() {
                public void execute() {
                    assert (p != null);
                    popup.hide();
                    handler.deleteProject(p);
                }
            });
            delete.setEnabled(isProjectSelected);
            menu.addItem(delete);
        }

        menu.addSeparator();

        {
            final Tag t = getSelectedTag();
            final boolean isTagSelected = t != null;

            // タグ編集
            MenuItem edit = new MenuItem("タグ編集", new Command() {
                public void execute() {
                    assert (t != null);
                    popup.hide();
                    TagDialogBox dlg = new TagDialogBox(t);
                    dlg.setHandler(new TagDialogBox.Handler() {
                        public void onOk(Tag input) {
                            handler.updateTag(input);
                        }
                    });
                    dlg.center();
                }
            });
            edit.setEnabled(isTagSelected);
            menu.addItem(edit);

            // タグ削除
            MenuItem delete = new MenuItem("タグ削除", new Command() {
                public void execute() {
                    assert (t != null);
                    popup.hide();
                    handler.deleteTag(t);
                }
            });
            delete.setEnabled(isTagSelected);
            menu.addItem(delete);
        }

        popup.setWidget(menu);
        popup.setPopupPositionAndShow(new PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
                int left = editButton.getAbsoluteLeft();
                int top = editButton.getAbsoluteTop() - offsetHeight;
                popup.setPopupPosition(left, top);
            }
        });
    }

    private Project getSelectedProject() {
        MainMenuItem selected = selectionModel.getSelectedObject();
        if (selected == null) {
            return null;
        }
        if (selected instanceof ProjectMenuItem) {
            ProjectMenuItem ps = (ProjectMenuItem) selected;
            return ps.getProject();
        } else {
            return null;
        }
    }

    private Tag getSelectedTag() {
        MainMenuItem selected = selectionModel.getSelectedObject();
        if (selected == null) {
            return null;
        }
        if (selected instanceof TagMenuItem) {
            TagMenuItem ts = (TagMenuItem) selected;
            return ts.getTag();
        } else {
            return null;
        }
    }

    public void setProjectList(List<Project> projects) {
        treeViewModel.setProjectList(projects);
    }

    public void setTagList(List<Tag> tags) {
        treeViewModel.setTagList(tags);
    }
}
