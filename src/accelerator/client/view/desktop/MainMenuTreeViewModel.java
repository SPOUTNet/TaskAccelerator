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


import accelerator.client.place.InboxPlace;
import accelerator.client.place.ProjectPlace;
import accelerator.client.place.SchedulePlace;
import accelerator.client.place.TagPlace;
import accelerator.client.place.TodayPlace;
import accelerator.shared.model.Project;
import accelerator.shared.model.Tag;
import accelerator.shared.util.CollectionUtil;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.place.shared.Place;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class MainMenuTreeViewModel implements TreeViewModel {

    public class MainMenuItem {
        private final String name;
        private final Place place;
        private final ListDataProvider<MainMenuItem> subMenuItems =
            new ListDataProvider<MainMenuItem>();
        private NodeInfo<MainMenuItem> nodeInfo;

        public MainMenuItem(String name) {
            this(name, null);
        }

        public MainMenuItem(String name, Place place) {
            this.name = name;
            this.place = place;
        }

        public String getName() {
            return name;
        }

        public Place getPlace() {
            return place;
        }

        public boolean isLeaf() {
            return place != null;
        }

        public boolean mapsToPlace(Place place) {
            return true;
        }

        public void addSubMenuItem(MainMenuItem source) {
            subMenuItems.getList().add(source);
        }

        public List<MainMenuItem> getSubMenuItems() {
            return subMenuItems.getList();
        }

        public void clearSubMenuItems() {
            subMenuItems.getList().clear();
            nodeInfo = null;
        }

        public NodeInfo<MainMenuItem> getNodeInfo() {
            if (nodeInfo == null) {
                nodeInfo =
                    new DefaultNodeInfo<MainMenuItem>(
                        subMenuItems,
                        new MainMenuItemCell(),
                        selectionModel,
                        null);
            }
            return nodeInfo;
        }

        public void reload() {
            subMenuItems.refresh();
        }
    }

    /**
     * 受信箱
     */
    private class InboxMenuItem extends MainMenuItem {
        public InboxMenuItem() {
            super("受信箱", new InboxPlace());
        }

        @Override
        public boolean mapsToPlace(Place place) {
            return place instanceof InboxPlace;
        }
    }
    
    /**
     * 今日
     */
    private class TodayMenuItem extends MainMenuItem {
        public TodayMenuItem() {
            super("今日", new TodayPlace());
        }
        
        @Override
        public boolean mapsToPlace(Place place) {
            return place instanceof TodayPlace;
        }
    }

    /**
     * カレンダー
     */
    private class CalendarMenuItem extends MainMenuItem {
        public CalendarMenuItem() {
            super("カレンダー", new SchedulePlace());
        }

        @Override
        public boolean mapsToPlace(Place place) {
            return place instanceof SchedulePlace;
        }
    }

    public class ProjectMenuItem extends MainMenuItem {
        private final Project project;

        public ProjectMenuItem(Project project) {
            super(
                project.getName(),
                new ProjectPlace(project.getKey(), project));
            this.project = project;
        }

        public Project getProject() {
            return project;
        }

        @Override
        public boolean mapsToPlace(Place place) {
            if (place instanceof ProjectPlace) {
                ProjectPlace projectPlace = (ProjectPlace) place;
                return projectPlace.getProjectKey().equals(project.getKey());
            }
            return false;
        }
    }

    public class TagMenuItem extends MainMenuItem {
        private final Tag tag;

        public TagMenuItem(Tag tag) {
            super(tag.getName(), new TagPlace(tag.getKey(), tag));
            this.tag = tag;
        }

        public Tag getTag() {
            return tag;
        }

        @Override
        public boolean mapsToPlace(Place place) {
            if (place instanceof TagPlace) {
                TagPlace tagPlace = (TagPlace) place;
                return tagPlace.getTagKey().equals(tag.getKey());
            }
            return false;
        }
    }

    private static class MainMenuItemCell extends AbstractCell<MainMenuItem> {
        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context,
                MainMenuItem value, SafeHtmlBuilder sb) {
            if (value != null) {
                sb.appendEscaped(value.getName());
            }
        }
    }

    private final ListDataProvider<MainMenuItem> menuItems =
        new ListDataProvider<MainMenuItem>();
    private final SelectionModel<MainMenuItem> selectionModel;
    private final MainMenuItem focusCategory = new MainMenuItem("Focus");
    private final MainMenuItem projectsCategory = new MainMenuItem("Projects");
    private final MainMenuItem tagsCategory = new MainMenuItem("Tags");

    public MainMenuTreeViewModel(SelectionModel<MainMenuItem> selectionModel) {
        this.selectionModel = selectionModel;
        initializeTree();
    }

    public <T> NodeInfo<?> getNodeInfo(T value) {
        if (value == null) {
            return new DefaultNodeInfo<MainMenuItem>(
                menuItems,
                new MainMenuItemCell());
        } else if (value instanceof MainMenuItem) {
            MainMenuItem category = (MainMenuItem) value;
            if (category.isLeaf() == false) {
                return category.getNodeInfo();
            }
        }
        return null;
    }

    public boolean isLeaf(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof MainMenuItem) {
            MainMenuItem item = (MainMenuItem) value;
            return item.isLeaf();
        } else {
            return false;
        }
    }

    private void initializeTree() {
        List<MainMenuItem> catList = menuItems.getList();
        focusCategory.addSubMenuItem(new InboxMenuItem());
        focusCategory.addSubMenuItem(new TodayMenuItem());
        focusCategory.addSubMenuItem(new CalendarMenuItem());
        catList.add(focusCategory);
        catList.add(projectsCategory);
        catList.add(tagsCategory);
    }

    public List<MainMenuItem> getMainMenuItemList() {
        List<MainMenuItem> sourceList = CollectionUtil.createArrayList();
        sourceList.addAll(focusCategory.getSubMenuItems());
        sourceList.addAll(projectsCategory.getSubMenuItems());
        sourceList.addAll(tagsCategory.getSubMenuItems());
        return sourceList;
    }

    public void setProjectList(List<Project> projects) {
        projectsCategory.clearSubMenuItems();
        for (Project p : projects) {
            ProjectMenuItem s = new ProjectMenuItem(p);
            projectsCategory.addSubMenuItem(s);
        }
        projectsCategory.reload();
    }

    public void setTagList(List<Tag> tags) {
        tagsCategory.clearSubMenuItems();
        for (Tag t : tags) {
            TagMenuItem s = new TagMenuItem(t);
            tagsCategory.addSubMenuItem(s);
        }
        tagsCategory.reload();
    }
}
