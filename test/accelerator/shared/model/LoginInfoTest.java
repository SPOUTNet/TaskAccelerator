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

import org.slim3.tester.AppEngineTestCase;
import org.junit.Before;
import org.junit.Test;


import accelerator.shared.model.LoginInfo;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LoginInfoTest extends AppEngineTestCase {

    private LoginInfo model;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        
        model = new LoginInfo();
    }
    
    @Test
    public void インスタンスを生成できる() throws Exception {
        assertThat(model, is(notNullValue()));
    }
    
    @Test
    public void Emailを設定できる() throws Exception {
        model.setEmail("example@gmail.com");
        assertThat(model.getEmail(), equalTo("example@gmail.com"));
    }
    
    @Test
    public void ニックネームを設定できる() throws Exception {
        model.setNickname("foo");
        assertThat(model.getNickname(), equalTo("foo"));
    }
    
    @Test
    public void ログインURLを設定できる() throws Exception {
        model.setLoginUrl("http://example.com/login");
        assertThat(model.getLoginUrl(), equalTo("http://example.com/login"));
    }
    
    @Test
    public void ログアウトURLを設定できる() throws Exception {
        model.setLogoutUrl("http://example.com/logout");
        assertThat(model.getLogoutUrl(), equalTo("http://example.com/logout"));
    }
    
    @Test
    public void デフォルトは未ログイン状態() throws Exception {
        assertFalse(model.isLoggedIn());
    }
    
    @Test
    public void ログイン状態にできる() throws Exception {
        model.setLoggedIn(true);
        assertTrue(model.isLoggedIn());
    }
    
    @Test
    public void キーを設定できる() throws Exception {
        Key key = KeyFactory.createKey(LoginInfo.class.getName(), "foo");
        model.setKey(key);
        
        assertThat(model.getKey(), is(key));
        assertThat(model.getKey().getName(), equalTo("foo"));
    }
}
