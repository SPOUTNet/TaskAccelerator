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
package accelerator.server.controller.validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slim3.controller.validator.Errors;

public class Validators extends org.slim3.controller.validator.Validators {

    public Validators(HttpServletRequest request) {
        super(request);
    }

    public Validators(Map<String, Object> parameters) {
        super(parameters);
    }

    public KeyTypeValidator keyType() {
        return new KeyTypeValidator();
    }

    public KeyTypeValidator keyType(String message) {
        return new KeyTypeValidator(message);
    }

    /**
     * エラーメッセージを取得します。
     * 
     * @return エラーメッセージ。
     */
    public String getErrorMessage() {
        StringBuilder sb = new StringBuilder();
        Errors errors = this.getErrors();
        sb.append(errors.toString());
        return sb.toString();
    }
}
