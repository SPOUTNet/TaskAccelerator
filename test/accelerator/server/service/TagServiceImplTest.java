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
package accelerator.server.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ServletTestCase;

import accelerator.server.meta.TagMeta;
import accelerator.shared.model.Tag;

import com.google.appengine.api.datastore.Key;

public class TagServiceImplTest extends ServletTestCase {
    private final TagMeta meta = TagMeta.get();
    private final TagServiceImpl service = new TagServiceImpl();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        // ログイン
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs
            .put("com.google.appengine.api.users.UserService.user_id_key", "1");
        tester.environment.setAttributes(attrs);
        tester.environment.setEmail("example@gmail.com");
    }

    @Test
    public void getTagListでユーザーが作成したタグのリストを取得できるべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("hoge");
        Datastore.put(tag);

        List<Tag> actual = service.getTagList();
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0).getName(), is("hoge"));
    }

    @Test
    public void createTagでタグを作成できるべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("foo");
        Tag result = service.createTag(tag);
        assertThat(result.getName(), is("foo"));
        assertThat(result.getKey(), is(notNullValue()));

        Tag actual = Datastore.get(meta, result.getKey());
        assertThat(actual.getName(), is("foo"));
    }

    @Test
    public void updateTagで指定したタグを更新できるべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("hoge");
        Key key = Datastore.put(tag);

        tag.setName("fuga");
        Tag actual = service.updateTag(tag);

        assertThat(actual.getName(), is("fuga"));
        assertThat(Datastore.get(meta, key).getName(), is("fuga"));
    }

    @Test
    public void deleteTagで指定したタグを削除できるべき() throws Exception {
        Tag tag = new Tag();
        tag.setName("hoge");
        Key key = Datastore.put(tag);
        assertThat(Datastore.query(meta).count(), is(1));

        service.deleteTag(key);
        assertThat(Datastore.query(meta).count(), is(0));
    }
}
