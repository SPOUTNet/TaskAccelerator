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


import accelerator.shared.model.LoginInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * 未ログイン時に表示するビュー。
 */
public class LoginView extends Composite {

    private static LoginViewUiBinder uiBinder = GWT
        .create(LoginViewUiBinder.class);

    interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
    }

    /**
     * コンストラクタ。
     */
    public LoginView() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    /**
     * タイトルを表示するビュー。
     */
    @UiField
    TitleView titleView;
    
    /**
     * ログイン情報を設定します。
     * @param info ログイン情報。
     */
    public void setLoginInfo(LoginInfo info) {
        titleView.setLoginInfo(info);
    }
}
