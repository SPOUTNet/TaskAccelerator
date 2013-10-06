package accelerator.server.controller;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import accelerator.server.util.HttpStatus;
import accelerator.shared.util.CollectionUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public abstract class ApplicationController extends Controller {
    protected ApplicationController() {

    }

    @Override
    public final Navigation run() throws Exception {
        if (isGet()) {
            return doGet();
        } else if (isPost()) {
            return doPost();
        } else if (isPut()) {
            return doPut();
        } else if (isDelete()) {
            return doDelete();
        } else {
            response.setStatus(HttpStatus.MethodNotAllowed);
            return null;
        }
    }

    protected Navigation doGet() throws Exception {
        response.setStatus(HttpStatus.MethodNotAllowed);
        return null;
    }

    protected Navigation doPost() throws Exception {
        response.setStatus(HttpStatus.MethodNotAllowed);
        return null;
    }

    protected Navigation doPut() throws Exception {
        response.setStatus(HttpStatus.MethodNotAllowed);
        return null;
    }

    protected Navigation doDelete() throws Exception {
        response.setStatus(HttpStatus.MethodNotAllowed);
        return null;
    }

    /**
     * Key のリストを取得します。
     * 
     * @param name
     *            パラメータ名。
     * @return Key のリスト。
     */
    protected final List<Key> asKeyList(CharSequence name) {
        String[] list = paramValues(name);
        List<Key> keyList = CollectionUtil.createArrayList();
        for (String s : list) {
            Key key = KeyFactory.stringToKey(s);
            keyList.add(key);
        }
        return keyList;
    }
}
