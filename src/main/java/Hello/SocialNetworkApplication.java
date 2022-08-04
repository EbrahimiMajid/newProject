package Hello;

import entity.Chat;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.menu.ShowAndRunMenu;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocialNetworkApplication extends Application{

    public Label userNameLabel;
    public SplitPane divider;
    public GridPane left;
    public ListView usersListView;
    public Label chatRoomNameLabel;
    public Label lastMessageLabel;
    public ListView massageListView;
    public HBox hBoxSend;
    public TextField messageField;
    public static Stage stage1;
    public ImageView send;
    public GridPane right;

    public List<Chat> chats;
    public List<CellChat> cellChats = new ArrayList<>();
    public HashMap<GridPane,Chat> ChatList = new HashMap<>();

    public static void main(String[] args) {
        new ShowAndRunMenu().runMenu();
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
        stage1 = stage;
        stage1.show();
    }

    public void initialize() throws IOException {
        usersListView.setFixedCellSize(100);
        right.minWidthProperty().bind(divider.widthProperty().multiply(0.4));
        left.minWidthProperty().bind(divider.widthProperty().multiply(0.4));
        chats = ChatHandle.chatService.showChats(ChatHandle.user);
        send.setVisible(false);

        messageField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if(messageField.getText().length()==0){
                    send.setVisible(false);
                }
                else{
                    send.setVisible(true);
                }
            }
        });

        usersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ChatList.get(usersListView.getItems().get(usersListView.getSelectionModel().getSelectedIndex()));
            }
        });

        for (Chat chat : chats) {
            cellChats.add(addChatToList(chat));
        }


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

    public void smileyButtonClicked(MouseEvent mouseEvent) {
    }

    public void vocalMessageClicked(MouseEvent mouseEvent) {
    }

    public void sendMassage(MouseEvent mouseEvent) {
    }

    public CellChat addChatToList(Chat chat) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        GridPane gridPane  =  loader.load(getClass().getResource("cell_chat.fxml").openStream());
        CellChat controller = loader.getController();
        if(chat.getName()!=null)
            controller.setText(chat.getName());
        else{
            if(chat.getUsers().get(0)== ChatHandle.user)
                controller.setText(chat.getUsers().get(1).getUsername());
            else{
                controller.setText(chat.getUsers().get(0).getUsername());
            }
        }
        controller.setTime(chat.getMassages().get(chat.getMassages().size()-1).
                getCreateDateTime().getHour()+":"+chat.getMassages()
                .get(chat.getMassages().size()-1).getCreateDateTime().getMinute());
        usersListView.getItems().add(gridPane);
        ChatList.put(gridPane,chat);
        usersListView.setFixedCellSize(160);
        return controller;
    }

    public void showChat(Chat chat){

    }
}
