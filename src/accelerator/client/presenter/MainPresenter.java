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
package accelerator.client.presenter;

import accelerator.client.ClientFactory;
import accelerator.client.event.ShowStatusEvent;
import accelerator.client.view.MainMenuView;
import accelerator.client.view.Shell;

/**
 * シェルのプレゼンター。
 */
public class MainPresenter {
    /**
     * シェル。
     */
    private Shell shell;

    /**
     * クライアントファクトリ。
     */
    private final ClientFactory clientFactory;

    /**
     * MainMenuView のプレぜンター。
     */
    private final MainMenuView.Presenter mainMenuPresenter;

    /**
     * コストラクタ。
     */
    public MainPresenter(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.mainMenuPresenter = new MainMenuPresenter(clientFactory);

        // ステータス表示イベントをハンドルする
        clientFactory.getEventBus().addHandler(
            ShowStatusEvent.TYPE,
            new ShowStatusEvent.Handler() {
                public void onShowStatus(ShowStatusEvent event) {
                    shell.showStatus(event.getStatus());
                }
            });
    }

    /**
     * シェルを取得します。
     * 
     * @return シェル。
     */
    public Shell getShell() {
        shell = clientFactory.getShell();
        clientFactory.getMainMenuView().setPresenter(mainMenuPresenter);
        return this.shell;
    }
}
