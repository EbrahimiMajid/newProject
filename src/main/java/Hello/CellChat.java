package Hello;

import javafx.scene.control.Label;

public class CellChat {
    public Label messageTimeLabel;
    public Label lastMessageLabel;
    public Label userNameLabel;
    public Label numbreMessageLabel;


    public void setText(String s) {
        userNameLabel.setText(s);
    }

    public void setTime(String s) {
        messageTimeLabel.setText(s);
    }
}
