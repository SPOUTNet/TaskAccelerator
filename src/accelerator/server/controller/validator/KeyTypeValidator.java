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

import org.slim3.controller.validator.AbstractValidator;

import com.google.appengine.api.datastore.KeyFactory;

public class KeyTypeValidator extends AbstractValidator {

    public KeyTypeValidator() {
        super();
    }

    public KeyTypeValidator(String message) {
        super(message);
    }

    public String validate(Map<String, Object> parameters, String name) {
        Object value = parameters.get(name);
        if (value == null || "".equals(value)) {
            return null;
        }

        try {
            String keyString = (String) value;
            KeyFactory.stringToKey(keyString);
            return null;
        } catch (Throwable ignore) {
            if (message != null) {
                return message;
            }
            return getMessageKey();
        }
    }

    @Override
    protected String getMessageKey() {
        return "validator.keyType";
    }

}
