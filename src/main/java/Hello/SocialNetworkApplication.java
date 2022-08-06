package Hello;

import entity.Chat;
import entity.Massage;
import entity.User;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import utils.menu.ShowAndRunMenu;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Hello.ChatHandle.user;

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
    public List<MassageImageOut> massageImageOut = new ArrayList<>();
    public List<MassageImageIn> massageImageIn = new ArrayList<>();
    public HashMap<GridPane,Chat> ChatList = new HashMap<>();
    public HashMap<GridPane,User> userGroupList = new HashMap<>();
    public HashMap<GridPane,User> userList = new HashMap<>();
    public HashMap<GridPane,Massage> massageList = new HashMap<>();
    public TextField searchBox;
    public ImageView okey;
    public TextField nameGroup;
    public GridPane createGroup;
    public ListView userGroupListView;
    public ImageView yeeeeees;

    public static void main(String[] args) {
        new ShowAndRunMenu().runMenu();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(SocialNetworkApplication.class.getResource("ChatR.fxml"));
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
        massageListView.setFixedCellSize(250);
        usersListView.setFixedCellSize(100);
        userGroupListView.setFixedCellSize(100);
        right.minWidthProperty().bind(divider.widthProperty().multiply(0.4));
        left.minWidthProperty().bind(divider.widthProperty().multiply(0.4));
        chats = ChatHandle.chatService.showChats(user);
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
                    for (Chat chat : user.getChats()) {
                        User user;
                        if(chat.getUsers().get(0)== ChatHandle.user)
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
                        ChatHandle.chatService.deleteChat(chats1.get(i), user);
                    }
                }
                else{
                    cellChats.clear();
                    ChatList.clear();
                    usersListView.getItems().clear();
                    if(ChatHandle.userService.existByUsername(searchBox.getText())!=null){
                        User user = ChatHandle.userService.existByUsername(searchBox.getText());
                        Boolean check = true;
                        for (Chat chat1 : ChatHandle.user.getChats()){
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
                if(usersListView.getSelectionModel().getSelectedIndex()!= -1){
                    if(chatByFollower==false){
                        if(usersListView.getItems().size()!=0 ){
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
                            for (Chat userChat : ChatHandle.user.getChats()) {
                                try {
                                    cellChats.add(addChatToList(userChat));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        chatByFollower=false;
                    }
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
        if(user.getFollowers().size()!=0){
            createGroup.setVisible(true);
            for (User user : user.getFollowers()) {
                cellGroup.add(newGroup(user));
            }
        }


    }

    public void searchChatRoom(MouseEvent mouseEvent) {
    }

    public void settingsButtonClicked(MouseEvent mouseEvent) {
    }

    public void attachFile(MouseEvent mouseEvent) throws IOException {
        if(chat!=null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Profile Picture");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
            File selectedFile = fileChooser.showOpenDialog(null);
            Image image = new Image(selectedFile.toURI().toString());
            if(chat.getUsers().size()==2){
                User user;
                if(chat.getUsers().get(0)== ChatHandle.user){
                    user = chat.getUsers().get(0);
                }
                else{
                    user = chat.getUsers().get(1);
                }
                if(!user.getBlockList().contains(ChatHandle.user)){
                    ChatHandle.chatService.addMassage(chat, ChatHandle.user,selectedFile.toURI().toString(),null);
                    FXMLLoader loader5 =new FXMLLoader(getClass().getResource("massage_image_out.fxml"));
                    GridPane gridPane  =  loader5.load();
                    MassageImageOut controller = loader5.getController();
                    massageImageOut.add(controller);
                    controller.setText(ChatHandle.user.getUsername());
                    controller.setTime(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
                    controller.setImage(image);
                    massageListView.getItems().add(gridPane);
                    massageListView.scrollTo(chat.getMassages().size());
                }

            }
            else{
                if(chat.getAdmins().contains(user)||chat.getClosed()==false){
                    ChatHandle.chatService.addMassage(chat, user,selectedFile.toURI().toString(),null);
                    FXMLLoader loader4 =new FXMLLoader(getClass().getResource("massage_image_out.fxml"));
                    GridPane gridPane  =  loader4.load();
                    MassageImageOut controller = loader4.getController();
                    massageImageOut.add(controller);
                    controller.setText(user.getUsername());
                    controller.setTime(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
                    controller.setImage(image);
                    massageListView.getItems().add(gridPane);
                    massageListView.scrollTo(chat.getMassages().size());
                }
                else{
                    System.out.println("the group is closed");
                }
            }
        }



    }

    public void smileyButtonClicked(MouseEvent mouseEvent) {
    }

    public void vocalMessageClicked(MouseEvent mouseEvent) {
    }

    public void sendMassage(MouseEvent mouseEvent) throws IOException {
        if(chat!=null){
            if(chat.getUsers().size()==2){
                User user;
                if(chat.getUsers().get(0)== ChatHandle.user){
                    user = chat.getUsers().get(0);
                }
                else{
                    user = chat.getUsers().get(1);
                }
                if(!user.getBlockList().contains(ChatHandle.user)){
                    ChatHandle.chatService.addMassage(chat, ChatHandle.user,messageField.getText(),null);
                    FXMLLoader loader = new FXMLLoader();
                    GridPane gridPane;
                    gridPane  =  loader.load(getClass().getResource("massage_text_out.fxml").openStream());
                    MassageTextOut controller = loader.getController();
                    massagesTextOut.add(controller);
                    controller.setText(ChatHandle.user.getUsername()+"\n"+messageField.getText());
                    controller.setTime(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
                    massageListView.getItems().add(gridPane);
                    massageListView.scrollTo(chat.getMassages().size());
                }

            }
            else{
                if(chat.getAdmins().contains(user)||chat.getClosed()==false){
                    ChatHandle.chatService.addMassage(chat, user,messageField.getText(),null);
                    FXMLLoader loader = new FXMLLoader();
                    GridPane gridPane;
                    gridPane  =  loader.load(getClass().getResource("massage_text_out.fxml").openStream());
                    MassageTextOut controller = loader.getController();
                    massagesTextOut.add(controller);
                    controller.setText(user.getUsername()+"\n"+messageField.getText());
                    controller.setTime(LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
                    massageListView.getItems().add(gridPane);
                    massageListView.scrollTo(chat.getMassages().size());
                }
                else{
                    System.out.println("the group is closed");
                }
            }
        }



    }

    public CellChat newGroup(User user) throws IOException {
        FXMLLoader loader2 =new FXMLLoader(getClass().getResource("cell_chat.fxml"));
        GridPane gridPane  =  loader2.load();
        CellChat controller = loader2.getController();
        controller.setText(user.getUsername());
        controller.setTime("");
        controller.setImage("okey");
        controller.avatarImage.setVisible(false);
        userGroupListView.getItems().add(gridPane);
        userGroupList.put(gridPane,user);
        return controller;
    }

    public CellChat addChatToList(Chat chat) throws IOException {
        FXMLLoader loader1 =new FXMLLoader(getClass().getResource("cell_chat.fxml"));
        GridPane gridPane  =  loader1.load();
        CellChat controller = loader1.getController();
        if(chat.getName()!=null)
            controller.setText(chat.getName());
        else{
            if(chat.getUsers().get(0)== user)
                controller.setText(chat.getUsers().get(1).getUsername());
            else{
                controller.setText(chat.getUsers().get(0).getUsername());
            }
        }
        if(chat.getMassages().size()!=0)
            controller.setTime(chat.getMassages().get(chat.getMassages().size()-1).
                getCreateDateTime().getHour()+":"+chat.getMassages()
                .get(chat.getMassages().size()-1).getCreateDateTime().getMinute());
        else
            controller.setTime("");
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
            GridPane gridPane = null;
            if(massage.getText().indexOf(".png")==-1 && massage.getText().indexOf("file:")==-1  ){
                if(massage.getUser()== user){
                    gridPane  =  loader.load(getClass().getResource("massage_text_out.fxml").openStream());
                    MassageTextOut controller = loader.getController();
                    massagesTextOut.add(controller);
                    controller.setText(massage.getUser().getUsername()+"\n"+massage.getText());
                    controller.setTime(massage.getCreateDateTime().getHour()+":"+massage.getCreateDateTime().getMinute());
                }
                else{
                    gridPane  =  loader.load(getClass().getResource("massage_text_in.fxml").openStream());
                    MassageTextIn controller = loader.getController();
                    massagesTextIn.add(controller);
                    controller.setText(massage.getUser().getUsername()+"\n"+massage.getText());
                    controller.setTime(massage.getCreateDateTime().getHour()+":"+massage.getCreateDateTime().getMinute());
                }
            }
            else{
                if(massage.getUser()== user){
                    FXMLLoader loader6 =new FXMLLoader(getClass().getResource("massage_image_out.fxml"));
                    gridPane  =  loader6.load();
                    MassageImageOut controller = loader6.getController();
                    massageImageOut.add(controller);
                    controller.setText(massage.getUser().getUsername());
                    controller.setImage(new Image(massage.getText()));
                    controller.setTime(massage.getCreateDateTime().getHour()+":"+massage.getCreateDateTime().getMinute());
                }
                else{
                    FXMLLoader loader7 =new FXMLLoader(getClass().getResource("massage_image_in.fxml"));
                    gridPane  =  loader7.load();
                    MassageImageIn controller = loader.getController();
                    massageImageIn.add(controller);
                    controller.setText(massage.getUser().getUsername());
                    controller.setImage(new Image(massage.getText()));
                    controller.setTime(massage.getCreateDateTime().getHour()+":"+massage.getCreateDateTime().getMinute());
                }
            }

            massageListView.getItems().add(gridPane);
        }
        massageListView.scrollTo(chat.getMassages().size());

    }

    public void chatByFollower(MouseEvent mouseEvent) throws IOException {
        List<User> users = ChatHandle.userService.getUserForShowPosts(user);
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
                FXMLLoader loader3 =new FXMLLoader(getClass().getResource("cell_chat.fxml"));
                GridPane gridPane  =  loader3.load();
                CellChat controller = loader3.getController();
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
                users.add(user);
                for (CellChat controller : cellGroup) {
                    if(controller.avatarImage.isVisible()){
                        users.add(userGroupList.get(controller.gridpane));
                    }
                }
                ChatHandle.chatService.addGroup(users,nameGroup.getText());
                try {
                    cellChats.add(addChatToList(ChatHandle.user.getChats().get(ChatHandle.user.getChats().size()-1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            createGroup.setVisible(false);
            cellGroup.clear();
            userGroupList.clear();
            userGroupListView.getItems().clear();

        }
    }
}
