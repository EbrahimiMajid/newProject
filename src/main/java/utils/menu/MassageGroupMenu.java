package utils.menu;
import entity.Chat;
import entity.Massage;
import entity.User;
import service.ChatService;
import service.UserService;
import utils.ApplicationContext;
import utils.input.Input;
import java.util.List;
import static utils.menu.ChatMenu.number;

public class MassageGroupMenu extends Menu{
    private final User user;
    private final ChatService chatService;
    private final Chat chat;
    private final String[] items;
    public final UserService userService = ApplicationContext.getUserService();
    public MassageGroupMenu(User user,Chat chat, ChatService chatService,String[] items) {
        super(items);
        this.items = items;
        this.chat = chat;
        this.user = user;
        this.chatService = chatService;
    }
    //"delete user","close group"
    void runMenu(int i){
        while (true){
            print();
            switch (chooseOperation()) {
                case 1:
                    addMassage();
                    break;
                case 2:
                    deleteMassage();
                    break;
                case 3:
                    editMassage();
                    break;
                case 4:
                    forwardMassage();
                    break;
                case 5:
                    search();
                    break;
                case 6:
                    addUser();
                    break;
                case 7:
                    deleteUser();
                    break;
                case 8:
                    blockUser();
                    break;
                case 9:
                    if(chat.getClosed()){
                        System.out.println("1. Open group");
                    }
                    else{
                        System.out.println("1. Close group");
                    }
                    System.out.println("2. back");
                    int check = ChatMenu.number(2).intValue();
                    if(check==1){
                        closeGroup();
                    }
                    break;
                case 10:
                    showUsers();
                    break;
                case 11:
                    return;
            }
        }
    }

    void runMenu(String s){
        while (true){
            print();
            switch (chooseOperation()) {
                case 1:
                    addMassage();
                    break;
                case 2:
                    deleteMassage();
                    break;
                case 3:
                    editMassage();
                    break;
                case 4:
                    forwardMassage();
                    break;
                case 5:
                    search();
                    break;
                case 6:
                    showUsers();
                case 7:
                    return;
            }
        }
    }

    private void deleteUser(){
        System.out.println("Enter the id of the desired person :");
        Long id = number();
        User user2 = userService.findById(id);;
        if(user2 ==null){
            System.out.println("This user does not exist!");
            return;
        }
        else{
            if(chat.getUsers().contains(user2)){
                chatService.deleteUserFromGroup(user2,chat);
            }
            else{
                System.out.println("This user does not exist in chat");
            }
        }
    }

    private void blockUser(){
        System.out.println("Enter the id of the desired person :");
        Long id = number();
        User user2 = userService.findById(id);
        if(user2 ==null){
            System.out.println("This user does not exist!");
            return;
        }
        else{
            if(chat.getUsers().contains(user2)){
                if(chat.getBlockList().contains(user2))
                    System.out.println("1. UnBlock");
                else
                    System.out.println("1. Block");
                System.out.println("2. back");
                int check = ChatMenu.number(2).intValue();
                chatService.blockUserInGroup(user2,chat);
            }
            else{
                System.out.println("This user does not exist in chat");
            }
        }
    }

    private void addUser(){
        System.out.println("Enter the id of the desired person :");
        Long id = number();
        User user2 = userService.findById(id);
        if(user2 ==null){
            System.out.println("This user does not exist!");
            return;
        }
        else{
            chat.getUsers().add(user2);
            user2.getChats().add(chat);
            System.out.println(user2.getUserProfile().getFirstName()+"  "+
                    user2.getUserProfile().getLastName()+"added");
        }

    }

    private void showUsers(){
        for (User user1 : chat.getUsers()) {
            if(chat.getAdmins().contains(user1)){
                System.out.println(user1.getUserProfile().getFirstName()+"   "+
                        user1.getUserProfile().getLastName()+"----------"+"admin");
                System.out.println("     id : "+user1.getId());
            }
            else{
                System.out.println(user1.getUserProfile().getFirstName()+"   "+
                        user1.getUserProfile().getLastName()+"----------"+"normal user");
                System.out.println("     id : "+user1.getId());
            }
        }
    }

    private void search(){
        System.out.println("Enter text for search :");
        String text = Input.scanner.nextLine();
        System.out.println(text);
        List<Massage> massages = chatService.searchMassageInChat(chat,text);
        if(massages!=null){
            System.out.println("Enter number of massage for show :");
            int id = number(chat.getMassages().size()).intValue()-1;
            if(massages.contains(chat.getMassages().get(id))){
                System.out.println("reply to : "+chat.getMassages().get(id).getReplyText());
                System.out.println(chat.getMassages().get(id).getUser().getUserProfile().getFirstName()+"   "
                        +chat.getMassages().get(id).getUser().getUserProfile().getLastName());
                System.out.println(chat.getMassages().get(id).getText());
                System.out.println(chat.getMassages().get(id).getLastUpdateDateTime().getHour()+":"+
                        chat.getMassages().get(id).getLastUpdateDateTime().getMinute());
            }
            else{
                System.out.println("invalid!");
            }
        }
    }

    private void addMassage(){
        if(chat.getAdmins().contains(user)||chat.getClosed()==false){
            System.out.println("1. reply to another massage");
            System.out.println("2. add Text");
            Long check = number();
            String replyText = null;
            if(check==1){
                System.out.println("Enter number of massage :");
                int id = number(chat.getMassages().size()).intValue();
                replyText = chat.getMassages().get(id).getText();

            }
            System.out.println("Enter massage :");
            String text = Input.scanner.nextLine();
            System.out.println(text.length());
            chatService.addMassage(chat,user,text,replyText);
        }
        else{
            System.out.println("the group is closed");
        }

    }

    private void closeGroup(){
        chatService.closeGroup(chat);
    }

    private void deleteMassage(){
        System.out.println("Enter Number of your massage :");
        int id = number(chat.getMassages().size()).intValue();
        chatService.deleteMassage(id,chat);
    }

    private void editMassage(){
        System.out.println("Enter Number of your massage :");
        int id = number(chat.getMassages().size()).intValue();
        if(user.getId() == chat.getMassages().get(id-1).getUser().getId()){
            String text = Input.scanner.nextLine();
            chatService.editMassage(id-1,chat,text);
        }
        else{
            System.out.println("invalid");
        }
    }

    private void forwardMassage(){
        System.out.println("Enter Number of your massage :");
        int id = number(chat.getMassages().size()).intValue()-1;
        System.out.println(id);
        System.out.println("Enter chat id for forward massage :");
        int chatId = number(user.getChats().size()).intValue();
        Chat chat1 = user.getChats().get(chatId-1);
        chatService.forwardMassage(chat,user,id,chat1);
    }

}
