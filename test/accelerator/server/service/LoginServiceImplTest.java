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

import java.util.HashMap;
import java.util.Map;

import org.slim3.tester.ServletTestCase;
import org.junit.Test;

import accelerator.server.service.LoginServiceImpl;
import accelerator.shared.model.LoginInfo;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LoginServiceImplTest extends ServletTestCase {

    private LoginServiceImpl service = new LoginServiceImpl();
    
    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        // ログアウト
        tester.environment.setEmail(null);
        tester.environment.setAttributes(null);
    }

    @Test
    public void ログインしていないとき() throws Exception {
        LoginInfo info = service.login("http://example.com");
        assertThat(info, is(notNullValue()));
        assertFalse(info.isLoggedIn());
    }
    
    @Test
    public void ログインしているとき() throws Exception {
        // ログイン
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put("com.google.appengine.api.users.UserService.user_id_key", "1");
        tester.environment.setAttributes(attrs);
        tester.environment.setEmail("example@gmail.com");
        
        LoginInfo info = service.login("http://example.com");
        assertThat(info, is(notNullValue()));
        assertTrue(info.isLoggedIn());
    }
}
