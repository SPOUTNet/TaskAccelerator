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

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * ステータスを表示するポップアップ。
 */
public class StatusPopupPanel extends PopupPanel {   
    
    /**
     * ステータスを表示するラベル。
     */
    private final Label statusLabel;
    
    /**
     * コンストラクタ。
     */
    public StatusPopupPanel() {
        super(true, false);                
        statusLabel = new Label();        
        setWidget(statusLabel);
    }
    
    /**
     * 表示するステータスを設定します。
     * @param message ステータスを表すメッセージ。
     */
    public void setStatus(String message) {
        statusLabel.setText(message);
    }
}
