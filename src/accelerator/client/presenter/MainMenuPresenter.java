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
package accelerator.client.presenter;

import java.util.List;

import accelerator.client.ClientFactory;
import accelerator.client.event.PrepareTaskListEvent;
import accelerator.client.event.ProjectChangeEvent;
import accelerator.client.event.ShowStatusEvent;
import accelerator.client.event.TagChangeEvent;
import accelerator.client.event.UpdateProjectListEvent;
import accelerator.client.event.UpdateTagListEvent;
import accelerator.client.service.ProjectServiceAsync;
import accelerator.client.service.TagServiceAsync;
import accelerator.client.view.MainMenuView;
import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.util.CollectionUtil;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MainMenuPresenter implements MainMenuView.Presenter {
    private final ClientFactory clientFactory;
    private final ProjectServiceAsync projectService;
    private final TagServiceAsync tagService;
    private final EventBus eventBus;
    private final PlaceController placeController;
    private MainMenuView view;
    private List<Project> projectList = CollectionUtil.createArrayList();
    private List<Tag> tagList = CollectionUtil.createArrayList();

    public MainMenuPresenter(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.eventBus = clientFactory.getEventBus();
        this.placeController = clientFactory.getPlaceController();
        this.projectService = clientFactory.getProjectService();
        this.tagService = clientFactory.getTagService();
        this.view = clientFactory.getMainMenuView();
        this.view.setPresenter(this);

        this.eventBus.addHandler(
            PrepareTaskListEvent.TYPE,
            new PrepareTaskListEvent.Handler() {
                public void onPrepareTaskList(PrepareTaskListEvent event) {
                    event.getView().setProjectList(projectList);
                    event.getView().setTagList(tagList);
                }
            });
    }

    public void goTo(Place place) {
        placeController.goTo(place);
    }

    private void showError(Throwable error) {
        showStatus(error.getMessage());
    }

    private void showStatus(String status) {
        clientFactory.getEventBus().fireEvent(new ShowStatusEvent(status));
    }

    public void createProject(Project input) {
        projectService.createProject(input, new AsyncCallback<Project>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Project result) {
                showStatus("プロジェクト \"" + result.getName() + "\" を作成しました。");

                projectList.add(result);
                view.setProjectList(projectList);

                eventBus.fireEvent(new ProjectChangeEvent(result));
                eventBus.fireEvent(new UpdateProjectListEvent(projectList));
            }
        });
    }

    public void createTag(Tag input) {
        tagService.createTag(input, new AsyncCallback<Tag>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Tag result) {
                showStatus("タグ \"" + result.getName() + "\" を作成しました。");

                tagList.add(result);
                view.setTagList(tagList);

                eventBus.fireEvent(new TagChangeEvent(result));
                eventBus.fireEvent(new UpdateTagListEvent(tagList));
            }
        });
    }

    public void updateProject(Project input) {
        projectService.updateProject(input, new AsyncCallback<Project>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Project result) {
                showStatus("プロジェクト \"" + result.getName() + "\" を更新しました。");

                // ビュー更新
                int index = projectList.indexOf(result);
                projectList.set(index, result);
                view.setProjectList(projectList);

                eventBus.fireEvent(new ProjectChangeEvent(result));
                eventBus.fireEvent(new UpdateProjectListEvent(projectList));
            }
        });
    }

    public void updateTag(Tag input) {
        tagService.updateTag(input, new AsyncCallback<Tag>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Tag result) {
                showStatus("タグ \"" + result.getName() + "\" を更新しました。");

                // key が同じなら同じエンティティと判断する
                int index = tagList.indexOf(result);
                tagList.set(index, result);
                view.setTagList(tagList);

                eventBus.fireEvent(new TagChangeEvent(result));
                eventBus.fireEvent(new UpdateTagListEvent(tagList));
            }
        });
    }

    public void deleteProject(final Project project) {
        projectService.deleteProject(
            project.getKey(),
            new AsyncCallback<Key>() {
                public void onFailure(Throwable caught) {
                    showError(caught);
                }

                public void onSuccess(Key result) {
                    showStatus("プロジェクト \"" + project.getName() + "\" を削除しました。");

                    projectList.remove(project);
                    view.setProjectList(projectList);

                    eventBus.fireEvent(new ProjectChangeEvent(project));
                    eventBus.fireEvent(new UpdateProjectListEvent(projectList));
                }
            });
    }

    public void deleteTag(final Tag tag) {
        tagService.deleteTag(tag.getKey(), new AsyncCallback<Key>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(Key result) {
                showStatus("タグ \"" + tag.getName() + "\" を削除しました。");

                tagList.remove(tag);
                view.setTagList(tagList);

                eventBus.fireEvent(new TagChangeEvent(tag));
                eventBus.fireEvent(new UpdateTagListEvent(tagList));
            }
        });
    }

    public void loadProjectList() {
        assert view != null : "MainMenuPresenter.view が null です。";

        projectService.getProjectList(new AsyncCallback<List<Project>>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(List<Project> result) {
                projectList = result;
                view.setProjectList(result);
                eventBus.fireEvent(new UpdateProjectListEvent(result));
            }
        });
    }

    public void loadTagList() {
        assert view != null : "MainMenuPresenter.view が null です。";

        tagService.getTagList(new AsyncCallback<List<Tag>>() {
            public void onFailure(Throwable caught) {
                showError(caught);
            }

            public void onSuccess(List<Tag> result) {
                tagList = result;
                view.setTagList(result);
                eventBus.fireEvent(new UpdateTagListEvent(result));
            }
        });
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }
}
