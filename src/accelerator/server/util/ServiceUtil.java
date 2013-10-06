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
package accelerator.server.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ServiceUtil {
    /**
     * コンストラクタ。
     */
    private ServiceUtil() {
        
    }
    
    /**
     * ログインユーザーを取得します。ログインしていないときは例外を発生させます。
     * @return ログインユーザー。
     * @throws Exception ログインしていないとき。
     */
    public static User getUserOrException() throws Exception {
        UserService service = UserServiceFactory.getUserService();
        User user = service.getCurrentUser();
        if (user == null) {
            throw new Exception("ログインしてください。");
        }
        return user;
    }
}
