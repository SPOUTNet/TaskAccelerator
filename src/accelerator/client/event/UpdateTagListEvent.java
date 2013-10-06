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

import java.util.List;


import accelerator.shared.model.Tag;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * タグ一覧更新イベント。
 */
public class UpdateTagListEvent extends GwtEvent<UpdateTagListEvent.Handler> {
    /**
     * UpdateTagList イベントのハンドラ。
     */
    public static interface Handler extends EventHandler {
        /**
         * UpdateTabList イベントを処理します。
         * @param event イベントデータ。
         */
        void onUpdateProjectList(UpdateTagListEvent event);
    }

    /**
     * イベントの型。
     */
    public static final Type<UpdateTagListEvent.Handler> TYPE =
        new Type<UpdateTagListEvent.Handler>();

    /**
     * 更新されたタグの一覧。
     */
    private final List<Tag> tagList;
    
    /**
     * コンストラクタ。
     * @param tags 更新されたタグの一覧。
     */
    public UpdateTagListEvent(List<Tag> tags) {
        this.tagList = tags;
    }

    /**
     * 更新されたタグの一覧を取得します。
     * @return 更新されたタグの一覧。
     */
    public List<Tag> getTagList() {
        return tagList;
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
        handler.onUpdateProjectList(this);
    }
}
