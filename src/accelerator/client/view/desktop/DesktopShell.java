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


import accelerator.client.ClientFactory;
import accelerator.client.ui.StatusPopupPanel;
import accelerator.client.view.Shell;
import accelerator.shared.model.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.Widget;

public class DesktopShell extends Composite implements Shell {

    private static MainViewUiBinder uiBinder = GWT
        .create(MainViewUiBinder.class);

    interface MainViewUiBinder extends UiBinder<Widget, DesktopShell> {
    }

    @UiField
    DeckLayoutPanel deckLayoutPanel;

    @UiField
    TitleView titleView;
    
    @UiField(provided = true)
    DesktopMainMenuView mainMenuView;   

    public DesktopShell(ClientFactory clientFactory) {
        mainMenuView = (DesktopMainMenuView)clientFactory.getMainMenuView();        
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    /**
     * {@inheritDoc}
     */
    public void setLoginInfo(LoginInfo info) {
        assert(info != null);
        titleView.setLoginInfo(info);
    }   

    /**
     * {@inheritDoc}
     */
    public void showStatus(String message) {
        final StatusPopupPanel popup = new StatusPopupPanel();
        popup.setStatus(message);
        popup.setPopupPositionAndShow(new PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
                RootLayoutPanel root = RootLayoutPanel.get();
                int left =
                    root.getAbsoluteLeft()
                        + root.getOffsetWidth()
                        - offsetWidth;
                int top =
                    root.getAbsoluteTop()
                        + root.getOffsetHeight()
                        - offsetHeight;
                popup.setPopupPosition(left, top);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void setWidget(IsWidget w) {
        deckLayoutPanel.setWidget(w);
    }
}
