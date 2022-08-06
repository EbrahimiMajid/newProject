package Hello;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MassageImageIn {
    public Label messageTimeLabel;
    public Label massageLabel;
    public ImageView image;


    public void setText(String username) {
        massageLabel.setText(username);
    }

    public void setTime(String s) {
        messageTimeLabel.setText(s);
    }
    public void setImage(Image image){
        this.image.setImage(image);
    }

}
