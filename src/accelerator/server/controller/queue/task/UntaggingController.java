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
package accelerator.server.controller.queue.task;

import java.util.logging.Logger;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

import accelerator.server.controller.validator.Validators;
import accelerator.server.service.TaskServiceImpl;
import accelerator.server.util.HttpStatus;

public class UntaggingController extends Controller {
    private final TaskServiceImpl service = new TaskServiceImpl();
    private final Logger logger = Logger.getLogger(UntaggingController.class
        .getName());

    @Override
    public Navigation run() throws Exception {
        if (isPost() == false) {
            response.setStatus(HttpStatus.MethodNotAllowed);
            return null;
        }
        if (validate() == false) {
            response.setStatus(HttpStatus.BadRequest);
            return null;
        }
        Key key = asKey("tagKey");
        service.untaggingTask(key);
        response.setStatus(HttpStatus.OK);
        return null;
    }

    private boolean validate() {
        Validators v = new Validators(request);
        v.add("tagKey", v.required(), v.keyType());
        if (v.validate()) {
            return true;
        } else {
            // 例外をスローすると、タスクキューがリトライしてしまうので、
            // ログを出力するだけにする
            logger.severe(v.getErrorMessage());
            return false;
        }
    }
}