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
package accelerator.shared.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Before;
import org.junit.Test;


import accelerator.shared.model.Task;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TaskTest extends AppEngineTestCase {

    private Task model;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        model = new Task();

        // ログイン
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs
            .put("com.google.appengine.api.users.UserService.user_id_key", "1");
        tester.environment.setAttributes(attrs);
        tester.environment.setEmail("example@gmail.com");
    }

    @Test
    public void インスタンスを生成できる() throws Exception {
        assertThat(model, is(notNullValue()));
    }

    @Test
    public void キーを設定できる() throws Exception {
        Key key = KeyFactory.createKey("Task", "foo");
        model.setKey(key);

        assertThat(model.getKey(), is(key));
        assertThat(model.getKey().getName(), equalTo("foo"));
    }

    @Test
    public void 名前を設定できる() throws Exception {
        model.setName("foo");
        assertThat(model.getName(), equalTo("foo"));
    }

    @Test
    public void ユーザーを設定できる() throws Exception {
        UserService userSvc = UserServiceFactory.getUserService();
        User user = userSvc.getCurrentUser();
        model.setUser(user);

        assertThat(model.getUser(), is(user));
        assertThat(model.getUser().getEmail(), equalTo("example@gmail.com"));
    }

    @Test
    public void ノートを設定できる() throws Exception {
        model.setNote("hoge");
        assertThat(model.getNote(), equalTo("hoge"));
    }

    @Test
    public void 初期状態は未完了() throws Exception {
        assertFalse(model.getCompleted());
    }

    @Test
    public void 完了状態を設定できる() throws Exception {
        model.setCompleted(true);
        assertTrue(model.getCompleted());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void 期日を設定できる() throws Exception {
        model.setDueDate(new Date(2011, 6, 30));

        assertThat(model.getDueDate().getYear(), equalTo(2011));
        assertThat(model.getDueDate().getMonth(), equalTo(6));
        assertThat(model.getDueDate().getDate(), equalTo(30));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void 作成日付を設定できる() throws Exception {
        model.setCreatedDate(new Date(2011, 6, 30));

        assertThat(model.getCreatedDate().getYear(), equalTo(2011));
        assertThat(model.getCreatedDate().getMonth(), equalTo(6));
        assertThat(model.getCreatedDate().getDate(), equalTo(30));
    }

    @Test
    public void プロジェクトのキーを設定できる() throws Exception {
        Key key = KeyFactory.createKey("Project", "bar");
        model.setProject(key);

        assertThat(model.getProject(), is(key));
        assertThat(model.getProject().getName(), equalTo("bar"));
    }
    
    @Test
    public void タグのキーを設定できる() throws Exception {
        List<Key> tagKeyList = new ArrayList<Key>();
        tagKeyList.add(KeyFactory.createKey("Tag", "foo"));
        tagKeyList.add(KeyFactory.createKey("Tag", "bar"));        
        model.setTags(tagKeyList);
        
        assertThat(model.getTags().size(), equalTo(2));
        assertThat(model.getTags().get(0).getName(), equalTo("foo"));
        assertThat(model.getTags().get(1).getName(), equalTo("bar"));
    }
}
