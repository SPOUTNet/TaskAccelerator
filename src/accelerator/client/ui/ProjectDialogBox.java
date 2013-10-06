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
package accelerator.client.ui;


import accelerator.shared.model.Project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ProjectDialogBox extends DialogBox {

    public interface Handler {
        /**
         * OK ボタンが押されたときの処理。
         * 
         * @param input
         *            ユーザーの入力。
         */
        void onOk(Project input);
    }

    private static ProjectDialogBoxUiBinder uiBinder = GWT
        .create(ProjectDialogBoxUiBinder.class);

    interface ProjectDialogBoxUiBinder extends
            UiBinder<Widget, ProjectDialogBox> {
    }

    @UiField
    TextBox nameTextBox;

    @UiField
    Label nameErrorLabel;

    private Handler handler;
    
    private final Project project;

    public ProjectDialogBox() {
        this("プロジェクト新規作成", new Project());
    }

    public ProjectDialogBox(Project project) {
        this("プロジェクト変更", project);
    }

    private ProjectDialogBox(String title, Project project) {
        setWidget(uiBinder.createAndBindUi(this));
        
        this.project = project;
        
        setText(title);
        setHandler(null);
        nameTextBox.setText(project.getName());
        nameErrorLabel.setVisible(false);
    }
    
    public void setHandler(Handler handler) {
        if (handler == null) {
            this.handler = new Handler() {
                public void onOk(Project input) {
                    // 何もしない
                }
            };
        } else {
            this.handler = handler;
        }
    }
    
    @Override
    protected void onLoad() {
        super.onLoad();
        nameTextBox.setFocus(true);
    }

    @UiHandler("okButton")
    void onOkButtonClick(ClickEvent e) {
        if (validate() == false) {
            return;
        }

        Project input = getInputProject();
        handler.onOk(input);

        hide();
    }

    private boolean validate() {
        boolean validName = true;
        nameErrorLabel.setText("");
        nameErrorLabel.setVisible(false);
        String name = nameTextBox.getText();
        if (name.isEmpty()) {
            validName = false;
            nameErrorLabel.setText("プロジェクト名が空です。");
            nameErrorLabel.setVisible(true);
        }
        return validName;
    }

    private Project getInputProject() {
        this.project.setName(nameTextBox.getText());
        return this.project;
    }

    @UiHandler("cancelButton")
    void onCancelButtonClick(ClickEvent e) {
        hide();
    }
}
