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
package accelerator.client.ui.widget;

import static accelerator.client.ui.widget.JQueryUI.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class IconButton extends AbstractButton {

    private static IconButtonUiBinder uiBinder = GWT
        .create(IconButtonUiBinder.class);

    interface IconButtonUiBinder extends UiBinder<Widget, IconButton> {
    }

    @UiField
    FocusPanel focusPanel;
    
    @UiField
    Label iconLabel;
    
    public IconButton() {
        super();
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public void setIcon(String styleName) {
        styleName = styleName != null
            ? styleName
            : UI_ICON_EMPTY;
        iconLabel.setStyleName(UI_ICON);
        iconLabel.addStyleName(styleName);
    }
    
    public void setTabIndex(int index) {
        focusPanel.setTabIndex(index);
    }
}
