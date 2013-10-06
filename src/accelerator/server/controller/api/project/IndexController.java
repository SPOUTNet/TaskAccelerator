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
package accelerator.server.controller.api.project;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import accelerator.server.meta.ProjectMeta;
import accelerator.server.service.ProjectServiceImpl;
import accelerator.shared.model.Project;

public class IndexController extends Controller {
    private final ProjectServiceImpl service = new ProjectServiceImpl();
    private final ProjectMeta meta = ProjectMeta.get();

    @Override
    public Navigation run() throws Exception {
        List<Project> projects = service.getProjectList();
        String json = meta.modelsToJson(projects.toArray());
        response.getWriter().write(json);
        return null;
    }
}
