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
package accelerator.client.ui.cell;


import accelerator.client.ui.widget.JQueryUI;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class IconButtonCell extends ButtonCell {
    private String icon;
    
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void render(Context context, SafeHtml data, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<span");
        
        // クラスを追加
        sb.appendHtmlConstant(" class=\"");
        sb.appendEscaped(JQueryUI.UI_ICON);
        sb.appendHtmlConstant(" ");
        sb.appendEscaped(icon);
        sb.appendHtmlConstant("\"");
        
        // ツールチップを追加
        if (data != null) {
            sb.appendHtmlConstant(" title=\"");
            sb.append(data);
            sb.appendHtmlConstant("\"");
        }
        
        sb.appendHtmlConstant(">");
        sb.appendHtmlConstant("</span>");
    }
}
