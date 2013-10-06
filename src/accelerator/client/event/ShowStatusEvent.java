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
package accelerator.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * ステータス表示イベントを表します。
 */
public class ShowStatusEvent extends GwtEvent<ShowStatusEvent.Handler> {
    /**
     * ShowStatus イベントのハンドラ。
     */
    public static interface Handler extends EventHandler {
        /**
         * ShowStatus イベントを処理します。
         * @param event イベントデータ。
         */
        void onShowStatus(ShowStatusEvent event);
    }
    
    /**
     * ステータス。
     */
    private final String status;
    
    /**
     * イベントの種類。
     */
    public static final Type<ShowStatusEvent.Handler> TYPE = new Type<ShowStatusEvent.Handler>();
    
    /**
     * コンストラクタ。
     * @param status 表示するステータス。
     */
    public ShowStatusEvent(String status) {
        this.status = status;
    }
    
    /**
     * ステータスを取得します。
     * @return ステータス。
     */
    public String getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Type<Handler> getAssociatedType() {
        return TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatch(Handler handler) {
        handler.onShowStatus(this);
    }
}
