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
package accelerator.client.view;


import accelerator.shared.model.LoginInfo;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * メイン画面が実装するインタフェース。
 */
public interface Shell extends IsWidget, AcceptsOneWidget {
   
    /**
     * ステータスを表示します。
     * @param message 表示するステータス。
     */
    void showStatus(String message);
    
    /**
     * ログイン情報を設定します。
     * @param loginInfo ログイン情報。
     */
    void setLoginInfo(LoginInfo loginInfo);       
}
