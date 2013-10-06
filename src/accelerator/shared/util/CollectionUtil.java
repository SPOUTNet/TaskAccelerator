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
package accelerator.shared.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * コレクションを生成する機能を提供します。
 */
public class CollectionUtil {
    /**
     * コンストラクタ。
     */
    private CollectionUtil() {

    }

    /**
     * ArrayList を生成します。
     * 
     * @return 新しい ArrayList。
     */
    public static <T> List<T> createArrayList() {
        return new ArrayList<T>();
    }

    /**
     * ArrayList を生成します。
     * @param arg 初期化に使用するコレクション。
     * @return 新しい ArrayList。
     */
    public static <T> List<T> createArrayList(Collection<? extends T> arg) {
        return new ArrayList<T>(arg);
    }

    /**
     * HashMap を生成します。
     * 
     * @return 新しい HashMap。
     */
    public static <TKey, TValue> Map<TKey, TValue> createHashMap() {
        return new HashMap<TKey, TValue>();
    }

    /**
     * HashSet を生成します。
     * 
     * @return 新しい HashSet。
     */
    public static <T> Set<T> createHashSet() {
        return new HashSet<T>();
    }
}
