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


import accelerator.shared.model.Tag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TagDialogBox extends DialogBox {

    public interface Handler {
        /**
         * OK ボタンが押されたときの処理。
         * 
         * @param input
         *            ユーザーの入力。
         */
        void onOk(Tag input);
    }

    private static TagDialogBoxUiBinder uiBinder = GWT
        .create(TagDialogBoxUiBinder.class);

    interface TagDialogBoxUiBinder extends UiBinder<Widget, TagDialogBox> {
    }
    
    private final Tag tag;

    public TagDialogBox() {
        this("タグ新規作成", new Tag());
    }

    public TagDialogBox(Tag tag) {
        this("タグ変更", tag);
    }
    
    private TagDialogBox(String title, Tag tag) {
        setWidget(uiBinder.createAndBindUi(this));
        
        this.tag = tag;
        setText(title);
        setHandler(null);
        nameTextBox.setText(tag.getName());
        nameErrorLabel.setVisible(false);
        
        nameTextBox.setFocus(true);        
    }

    @UiField
    TextBox nameTextBox;

    @UiField
    Label nameErrorLabel;

    private Handler handler;

    public void setHandler(Handler handler) {
        if (handler == null) {
            this.handler = new Handler() {
                public void onOk(Tag input) {
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

        Tag input = getInputTag();
        handler.onOk(input);
        hide();
    }

    private boolean validate() {
        boolean validName = true;
        nameErrorLabel.setVisible(false);
        nameErrorLabel.setText("");
        String name = nameTextBox.getText();
        if (name.isEmpty()) {
            validName = false;
            nameErrorLabel.setText("タグ名が空です。");
            nameErrorLabel.setVisible(true);
        }
        return validName;
    }

    private Tag getInputTag() {
        tag.setName(nameTextBox.getText());
        return tag;
    }

    @UiHandler("cancelButton")
    void onCancelButtonClick(ClickEvent e) {
        hide();
    }
}
