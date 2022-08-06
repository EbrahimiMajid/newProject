package Hello;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MassageImageOut {

    public Label messageLabel;
    public Label messageTimeLabel;
    public ImageView image;


    public void setText(String username) {
        messageLabel.setText(username);
    }

    public void setTime(String s) {
        messageTimeLabel.setText(s);
    }
    public void setImage(Image image){
        this.image.setImage(image);
    }
}
