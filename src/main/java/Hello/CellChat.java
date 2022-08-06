package Hello;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CellChat {
    public Label messageTimeLabel;
    public Label lastMessageLabel;
    public Label userNameLabel;
    public Label numbreMessageLabel;
    public Image profileImage;
    public ImageView avatarImage;
    public GridPane gridpane;


    public void setText(String s) {
        userNameLabel.setText(s);
    }

    public void setTime(String s) {
        messageTimeLabel.setText(s);
    }

    public void setImage(String s){
        avatarImage.setImage(new Image(s+".png"));
    }
}
