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
package accelerator.client;

import accelerator.client.activity.AppActivityMapper;
import accelerator.client.place.AppPlaceHistoryMapper;
import accelerator.client.place.InboxPlace;
import accelerator.client.presenter.MainPresenter;
import accelerator.client.view.Shell;
import accelerator.client.view.desktop.LoginView;
import accelerator.shared.model.LoginInfo;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Main implements EntryPoint {
    private ClientFactory clientFactory;
    private MainPresenter mainPresenter;
    private Place defaultPlace = new InboxPlace();

    public void onModuleLoad() {
        clientFactory = GWT.create(ClientFactory.class);

        // 例外ハンドラを登録
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                GWT.log("例外発生", e);
                clientFactory.getLogService().writeLog(
                    e.toString(),
                    new AsyncCallback<Void>() {
                        public void onFailure(Throwable caught) {
                            // 何もしない
                        }

                        public void onSuccess(Void result) {
                            // 何もしない
                        }
                    });
            }
        });

        // ログイン
        clientFactory.getLoginService().login(
            GWT.getHostPageBaseURL(),
            new AsyncCallback<LoginInfo>() {
                public void onFailure(Throwable caught) {
                }

                public void onSuccess(LoginInfo result) {
                    if (result.isLoggedIn()) {
                        loadMain(result);
                    } else {
                        loadLogin(result);
                    }
                }
            });
    }

    private void loadMain(LoginInfo loginInfo) {
        mainPresenter = new MainPresenter(clientFactory);
        Shell shell = mainPresenter.getShell();
        shell.setLoginInfo(loginInfo);

        EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        ActivityManager activityManager =
            new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(shell);

        AppPlaceHistoryMapper historyMapper =
            GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler =
            new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootLayoutPanel.get().add(shell);
        historyHandler.handleCurrentHistory();
    }

    private void loadLogin(LoginInfo loginInfo) {
        LoginView view = new LoginView();
        view.setLoginInfo(loginInfo);
        RootLayoutPanel.get().add(view);
    }
}