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
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * タイトルを表示するビュー。
 */
public class TitleView extends Composite {

    private static TitleViewUiBinder uiBinder = GWT
        .create(TitleViewUiBinder.class);

    interface TitleViewUiBinder extends UiBinder<Widget, TitleView> {
    }

    /**
     * タイトルを表示するラベル。
     */
    @UiField
    Label titleLabel;
    
    /**
     * ログインページへのリンク。
     */
    @UiField
    Anchor authAnchor;

    /**
     * コンストラクタ。
     */
    public TitleView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    /**
     * ログイン情報を設定します。
     * @param info ログイン情報。
     */
    public void setLoginInfo(LoginInfo info) {
        assert (info != null);
   
        if (info.isLoggedIn()) {
            authAnchor.setText("Logout");
            authAnchor.setHref(info.getLogoutUrl());
        } else {
            authAnchor.setText("Login");
            authAnchor.setHref(info.getLoginUrl());
        }
    }
}
