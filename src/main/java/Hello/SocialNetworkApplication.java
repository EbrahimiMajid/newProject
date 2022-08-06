package Hello;

import entity.Chat;
import entity.Massage;
import entity.User;
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
import utils.input.Input;
import utils.menu.ShowAndRunMenu;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static utils.menu.ChatMenu.number;

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
    public Boolean chatByFollower = false;
    public Chat chat;
    public List<Chat> chats;
    public List<CellChat> cellChats = new ArrayList<>();
    public List<CellChat> cellGroup = new ArrayList<>();
    public List<MassageTextIn> massagesTextIn = new ArrayList<>();
    public List<MassageTextOut> massagesTextOut = new ArrayList<>();
    public HashMap<GridPane,Chat> ChatList = new HashMap<>();
    public HashMap<GridPane,User> userGroupList = new HashMap<>();
    public HashMap<GridPane,User> userList = new HashMap<>();
    public HashMap<GridPane,Massage> massageList = new HashMap<>();
    public TextField searchBox;
    public ImageView okey;
    public TextField nameGroup;
    public GridPane createGroup;
    public ListView userGroupListView;

    public static void main(String[] args) {
        //new ShowAndRunMenu().runMenu();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);
        stage.setMinHeight(640);
        stage.setMinWidth(1040);
        stage.setTitle("Chatroom");
        stage.setScene(scene);
        stage1 = stage;
        stage1.show();
    }

    public void initialize() throws IOException {
        createGroup.setVisible(false);
        massageListView.setFixedCellSize(160);
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

        searchBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if(searchBox.getText().length()==0){
                    cellChats.clear();
                    ChatList.clear();
                    usersListView.getItems().clear();
                    List<Chat> chats1 = new ArrayList<>();
                    for (Chat chat : chats) {
                        User user;
                        if(chat.getUsers().get(0)==ChatHandle.user)
                            user = chat.getUsers().get(1);
                        else
                            user = chat.getUsers().get(0);
                        if(chat.getMassages().size()==0 && chat.getName()==null && !ChatHandle.userService.getUserForShowPosts(ChatHandle.user).contains(user)){
                            chats1.add(chat);
                        }
                        else{
                                try {
                                    cellChats.add(addChatToList(chat));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                        }
                    }
                    int size = chats1.size();
                    for(int i=0; i < size; i++){
                        ChatHandle.chatService.deleteChat(chats1.get(i),ChatHandle.user);
                    }
                }
                else{
                    cellChats.clear();
                    ChatList.clear();
                    usersListView.getItems().clear();
                    if(ChatHandle.userService.existByUsername(searchBox.getText())!=null){
                        User user = ChatHandle.userService.existByUsername(searchBox.getText());
                        Boolean check = true;
                        for (Chat chat1 : chats){
                            if(chat1.getUsers().size()==2 && chat1.getUsers().contains(user)
                                    && chat1.getUsers().contains(ChatHandle.user)){
                                check = false;
                                try {
                                    cellChats.add(addChatToList(chat1));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(check){
                            List<User> users = new ArrayList<>();
                            users.add(ChatHandle.user);
                            users.add(user);
                            ChatHandle.chatService.addChat(users);
                            try {
                                cellChats.add(addChatToList(user.getChats().get(user.getChats().size()-1)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                }
            }
        });

        usersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(chatByFollower==false){
                    if(usersListView.getItems().size()!=0){
                        try {
                            showChat(ChatList.get(usersListView.getItems().get(usersListView.getSelectionModel().getSelectedIndex())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{

                    }
                }
                else{
                    if(usersListView.getItems().size()!=0){
                        User user = userList.get(usersListView.getItems().get(usersListView.getSelectionModel().getSelectedIndex()));
                        List<User> users = new ArrayList<>();
                        users.add(ChatHandle.user);
                        users.add(user);
                        ChatHandle.chatService.addChat(users);
                        try {
                            cellChats.add(addChatToList(user.getChats().get(user.getChats().size()-1)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    chatByFollower=false;
                }
            }
        });
        userGroupListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(cellGroup.get(userGroupListView.getSelectionModel().getSelectedIndex()).avatarImage.isVisible())
                    cellGroup.get(userGroupListView.getSelectionModel().getSelectedIndex()).avatarImage.setVisible(false);
                else
                    cellGroup.get(userGroupListView.getSelectionModel().getSelectedIndex()).avatarImage.setVisible(true);
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

    public void slideMenuClicked(MouseEvent mouseEvent) throws IOException {
        if(ChatHandle.user.getFollowers().size()!=0){
            createGroup.setVisible(true);
            for (User user : ChatHandle.user.getFollowers()) {
                cellGroup.add(newGroup(user));
            }
        }


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

    public void sendMassage(MouseEvent mouseEvent) throws IOException {
        if(chat!=null){
            if(chat.getUsers().size()==2){
                User user;
                if(chat.getUsers().get(0)==ChatHandle.user){
                    user = chat.getUsers().get(0);
                }
                else{
                    user = chat.getUsers().get(1);
                }
                if(!user.getBlockList().contains(ChatHandle.user)){
                    ChatHandle.chatService.addMassage(chat,ChatHandle.user,messageField.getText(),null);
                    FXMLLoader loader = new FXMLLoader();
                    GridPane gridPane;
                    gridPane  =  loader.load(getClass().getResource("massage_text_in.fxml").openStream());
                    MassageTextOut controller = loader.getController();
                    massagesTextOut.add(controller);
                    controller.setText(ChatHandle.user.getUsername()+"\n"+messageField.getText());
                    controller.setTime(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
                    massageListView.getItems().add(gridPane);
                }

            }
            else{
                if(chat.getAdmins().contains(ChatHandle.user)||chat.getClosed()==false){
                    ChatHandle.chatService.addMassage(chat,ChatHandle.user,messageField.getText(),null);
                    FXMLLoader loader = new FXMLLoader();
                    GridPane gridPane;
                    gridPane  =  loader.load(getClass().getResource("massage_text_in.fxml").openStream());
                    MassageTextOut controller = loader.getController();
                    massagesTextOut.add(controller);
                    controller.setText(ChatHandle.user.getUsername()+"\n"+messageField.getText());
                    controller.setTime(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
                    massageListView.getItems().add(gridPane);
                }
                else{
                    System.out.println("the group is closed");
                }
            }
        }



    }

    public CellChat newGroup(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        GridPane gridPane  =  loader.load(getClass().getResource("cell_chat.fxml").openStream());
        CellChat controller = loader.getController();
        controller.setText(user.getUsername());
        controller.setTime("");
        controller.setImage("okey");
        controller.avatarImage.setVisible(false);
        userGroupListView.getItems().add(gridPane);
        userGroupList.put(gridPane,user);
        return controller;
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
        return controller;
    }

    public void showChat(Chat chat) throws IOException {
        this.chat = chat;
        massageList.clear();
        massagesTextIn.clear();
        massagesTextOut.clear();
        massageListView.getItems().clear();
        for (Massage massage : chat.getMassages()) {
            FXMLLoader loader = new FXMLLoader();
            GridPane gridPane;
            if(massage.getUser()==ChatHandle.user){
                gridPane  =  loader.load(getClass().getResource("massage_text_in.fxml").openStream());
                MassageTextOut controller = loader.getController();
                massagesTextOut.add(controller);
                controller.setText(massage.getUser().getUsername()+"\n"+massage.getText());
                controller.setTime(massage.getCreateDateTime().getHour()+":"+massage.getCreateDateTime().getMinute());
            }
            else{
                gridPane  =  loader.load(getClass().getResource("massage_text_out.fxml").openStream());
                MassageTextIn controller = loader.getController();
                massagesTextIn.add(controller);
                controller.setText(massage.getUser().getUsername()+"\n"+massage.getText());
                controller.setTime(massage.getCreateDateTime().getHour()+":"+massage.getCreateDateTime().getMinute());
            }
            massageListView.getItems().add(gridPane);
        }

    }

    public void addGroup(){

    }

    public void chatByFollower(MouseEvent mouseEvent) throws IOException {
        List<User> users = ChatHandle.userService.getUserForShowPosts(ChatHandle.user);
        List<User> temp = new ArrayList<>();
        for (User user : users) {
            for (Chat chat1 : chats) {
                if (chat1.getUsers().contains(user) && chat1.getUsers().size()==2){
                    temp.add(user);
                }
            }
        }
        for (User user : temp) {
            users.remove(user);
        }
        if(users.size()!=0){
            chatByFollower = true;
            cellChats.clear();
            ChatList.clear();
            usersListView.getItems().clear();
            for (User user : users) {
                FXMLLoader loader = new FXMLLoader();
                GridPane gridPane  =  loader.load(getClass().getResource("cell_chat.fxml").openStream());
                CellChat controller = loader.getController();
                controller.setText(user.getUsername());
                controller.setTime("");
                usersListView.getItems().add(gridPane);
                userList.put(gridPane,user);
            }
        }
    }

    public void createNewGroup(MouseEvent mouseEvent) {
        if(nameGroup.getText().length()!=0){
            Boolean test = false;
            for (CellChat controller : cellGroup) {
                if(controller.avatarImage.isVisible()){
                    test = true;
                    break;
                }
            }
            if(test){
                List<User> users = new ArrayList<>();
                users.add(ChatHandle.user);
                for (CellChat controller : cellGroup) {
                    if(controller.avatarImage.isVisible()){
                        users.add(userGroupList.get(controller.gridpane));
                    }
                }
                ChatHandle.chatService.addChat(users);
            }
            else{
                createGroup.setVisible(false);
                cellGroup.clear();
                userGroupList.clear();
                userGroupListView.getItems().clear();
            }
        }
    }
}
