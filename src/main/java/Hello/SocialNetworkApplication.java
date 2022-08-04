package Hello;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.menu.ShowAndRunMenu;

import java.io.IOException;

public class SocialNetworkApplication extends Application {
    public Label userNameLabel;
    public SplitPane divider;
    public GridPane left;
    public ListView usersListView;
    public Label chatRoomNameLabel;
    public Label lastMessageLabel;
    public ListView massageListView;
    public HBox hBoxSend;
    public TextField messageField;

    //public static Stage stage1;


    public static void main(String[] args) {
        //new ShowAndRunMenu().runMenu();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setTitle("Chatroom");
        stage.setScene(scene);
        //stage1 = stage;
        stage.show();
    }

    public void minimizeApp(MouseEvent mouseEvent) {
    }

    public void closeApp(MouseEvent mouseEvent) {
    }

    public void slideMenuClicked(MouseEvent mouseEvent) {
    }

    public void searchChatRoom(MouseEvent mouseEvent) {
    }

    public void settingsButtonClicked(MouseEvent mouseEvent) {
    }

    public void attachFile(MouseEvent mouseEvent) {
    }

    public void sendMessage(ActionEvent actionEvent) {
    }

    public void mouseclicked(MouseEvent mouseEvent) {
    }

    public void smileyButtonClicked(MouseEvent mouseEvent) {
    }

    public void vocalMessageClicked(MouseEvent mouseEvent) {
    }
}
