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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;

/**
 * ボタンのベースクラス。
 */
public class AbstractButton extends Composite implements HasClickHandlers {

    protected static boolean isKeyAssigned(int code) {
        switch (code) {
        case KeyCodes.KEY_ENTER:
            return true;
        default:
            return false;
        }
    }

    /**
     * ボタンが無効かどうか。
     */
    private boolean disabled = false;

    /**
     * 最後にキーが押されたかどうか。
     */
    private boolean lastWasKeyDown = false;

    /**
     * コンストラクタ。
     */
    public AbstractButton() {
        sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.KEYEVENTS);
    }

    /**
     * ボタンが無効かどうか設定します。
     * 
     * @param disabled
     *            ボタンが無効のとき true。それ以外のとき false。
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        setStyleName(this.getElement(), UI_STATE_DISABLED, disabled);
    }
    
    /**
     * ボタンが有効かどうか設定します。
     * @param enabled ボタンが有効のとき true。それ以外のとき false。
     */
    public void setEnabled(boolean enabled) {
        setDisabled(!enabled);
    }

    /**
     * ボタンが無効かどうか示す値を取得します。
     * 
     * @return ボタンが無効のとき true。それ以外のとき false。
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * ID を設定します。
     * 
     * @param id
     *            設定する ID。
     */
    public void setElementId(String id) {
        getElement().setId(id);
    }

    /**
     * {@inheritDoc}
     */
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    /**
     * {@inheritDoc}
     */
    public void onBrowserEvent(Event event) {
        int eventType = DOM.eventGetType(event);

        switch (eventType) {
        case Event.ONMOUSEOVER:
            addStyleName(UI_STATE_HOVER);
            break;
        case Event.ONMOUSEOUT:
            removeStyleName(UI_STATE_HOVER);
            break;
        case Event.ONCLICK:
            if (disabled) {
                return;
            }
        }

        switch (eventType) {
        case Event.ONKEYDOWN:
        case Event.ONKEYPRESS:
        case Event.ONKEYUP:
            if (DOM.eventGetAltKey(event) || DOM.eventGetMetaKey(event)) {
                super.onBrowserEvent(event);
                return;
            }
        }

        switch (eventType) {
        case Event.ONKEYDOWN: {
            keyboardNavigation(event);
            lastWasKeyDown = true;
            break;
        }
        case Event.ONKEYPRESS:
            if (!lastWasKeyDown) {
                keyboardNavigation(event);
            }
            lastWasKeyDown = false;
            break;
        case Event.ONKEYUP:
            lastWasKeyDown = false;
            break;
        }

        switch (eventType) {
        case Event.ONKEYDOWN:
        case Event.ONKEYUP:
            if (isKeyAssigned(DOM.eventGetKeyCode(event))) {
                DOM.eventCancelBubble(event, true);
                DOM.eventPreventDefault(event);
                return;
            }
        }

        super.onBrowserEvent(event);
    }

    private void keyboardNavigation(Event event) {
        if (disabled) {
            return;
        }

        int code = DOM.eventGetKeyCode(event);
        switch (code) {
        case KeyCodes.KEY_ENTER:
            NativeEvent ne =
                Document.get().createClickEvent(
                    0,
                    0,
                    0,
                    0,
                    0,
                    false,
                    false,
                    false,
                    false);
            ClickEvent.fireNativeEvent(ne, this);
            break;
        }
    }
}
