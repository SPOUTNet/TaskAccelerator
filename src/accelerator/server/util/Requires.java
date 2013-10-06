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

/**
 * 前提条件を定義します。
 */
public class Requires {
    /**
     * コンストラクタ。
     */
    private Requires() {        
    }
    
    /**
     * パラメータは null ではいけない。
     * @param value チェックするパラメータ。
     * @param name　パラメータの名前。
     */
    public static <T> void notNull(T value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " が null です。");
        }
    }
    
    /**
     * パラメータは null または空の文字列ではいけない。
     * @param value チェックするパラメータ。
     * @param name　パラメータの名前。
     */
    public static void notNullOrEmpty(String value, String name) {
        notNull(value, name);
        if (value.isEmpty()) {
            throw new IllegalArgumentException(name + " が空の文字列です。");
        }
    }
    
    /**
     * パラメータは null でなければいけない。
     * @param value チェックするパラメータ。
     * @param name　パラメータの名前。
     */
    public static <T> void isNull(T value, String name) {
        if (value != null) {
            throw new IllegalArgumentException(name + " が null ではありません。");
        }
    }
}
