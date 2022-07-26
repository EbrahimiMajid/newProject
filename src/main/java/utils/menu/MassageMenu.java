package utils.menu;

import entity.Chat;
import entity.Massage;
import entity.User;
import service.ChatService;
import service.UserService;
import utils.ApplicationContext;
import utils.input.Input;

import java.util.List;

public class MassageMenu extends Menu{
    private final User user;
    private final User user1;
    private final ChatService chatService;
    private final Chat chat;
    public final UserService userService = ApplicationContext.getUserService();
    public MassageMenu(User user,Chat chat, ChatService chatService) {
        super(new String[]{"add massage","Delete massage"
                ,"edit massage","forward massage","search massage",
                "show userId","Block User","back"});
        this.chat = chat;
        this.user = user;
        this.chatService = chatService;
        if(chat.getUsers().get(0)==user)
            user1 = chat.getUsers().get(1);
        else
            user1 = chat.getUsers().get(0);
    }
    void runMenu(){
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
                    System.out.println("id : "+user1.getId());
                    break;
                case 7:
                    userService.blockUser(user,user1);
                    break;
                case 8:
                    return;
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
            int id = ChatMenu.number(chat.getMassages().size()).intValue()-1;
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
        if (user1.getBlockList().contains(user)){
            System.out.println("Sorry, you are blocked!");
        }
        else{
            System.out.println("1. reply to another massage");
            System.out.println("2. add Text");
            Long check = ChatMenu.number();
            String replyText = null;
            if(check==1){
                System.out.println("Enter number of massage :");
                int id = ChatMenu.number(chat.getMassages().size()).intValue();
                replyText = chat.getMassages().get(id).getText();

            }
            System.out.println("Enter massage :");
            String text = Input.scanner.nextLine();
            System.out.println(text.length());
            chatService.addMassage(chat,user,text,replyText);
        }
    }

    private void deleteMassage(){
        System.out.println("Enter Number of your massage :");
        int id = ChatMenu.number(chat.getMassages().size()).intValue();
        chatService.deleteMassage(id,chat);
    }

    private void editMassage(){
        System.out.println("Enter Number of your massage :");
        int id = ChatMenu.number(chat.getMassages().size()).intValue();
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
        int id = ChatMenu.number(chat.getMassages().size()).intValue()-1;
        System.out.println(id);
        System.out.println("Enter chat id for forward massage :");
        int chatId = ChatMenu.number(user.getChats().size()).intValue();
        Chat chat1 = user.getChats().get(chatId-1);
        chatService.forwardMassage(chat,user,id,chat1);
    }
}
