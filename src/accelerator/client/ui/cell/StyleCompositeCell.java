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

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import java.util.ArrayList;
import java.util.List;

public class StyleCompositeCell<C> extends CompositeCell<C> {
    /**
     * The style name.
     */
    private String styleName;

    /**
     * The cells that compose this {@link Cell}.
     * 
     * NOTE: Do not add add/insert/remove hasCells methods to the API. This cell
     * assumes that the index of the cellParent corresponds to the index in the
     * hasCells array.
     */
    private final List<HasCell<C, ?>> hasCells;

    /**
     * Construct a new {@link CompositeCell}.
     * 
     * @param hasCells
     *            the cells that makeup the composite
     */
    public StyleCompositeCell(List<HasCell<C, ?>> hasCells) {
        super(hasCells);

        this.hasCells = new ArrayList<HasCell<C, ?>>(hasCells);
    }

    /**
     * @return the styleName
     */
    public String getStyleName() {
        return styleName;
    }

    /**
     * @param styleName
     *            the styleName to set
     */
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(Context context, C value, SafeHtmlBuilder sb) {
        String divTag = "<div class=\"" + styleName + "\">";
        sb.appendHtmlConstant(divTag);
        for (HasCell<C, ?> hasCell : hasCells) {
            render(context, value, sb, hasCell);
        }
        sb.appendHtmlConstant("</div>");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Element getContainerElement(Element parent) {
        return parent.getFirstChildElement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <X> void render(Context context, C value, SafeHtmlBuilder sb,
            HasCell<C, X> hasCell) {
        Cell<X> cell = hasCell.getCell();
        sb.appendHtmlConstant("<span>");
        cell.render(context, hasCell.getValue(value), sb);
        sb.appendHtmlConstant("</span>");
    }
}
