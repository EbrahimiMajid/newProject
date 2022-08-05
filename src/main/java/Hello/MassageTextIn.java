package Hello;

import javafx.scene.control.Label;

public class MassageTextIn {
    public Label messageLabel;

    public Label messageTimeLabel;


    public void setText(String s) {
        messageLabel.setText(s);
    }

    public void setTime(String s) {
        messageTimeLabel.setText(s);
    }
}
