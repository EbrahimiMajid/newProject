
package utils.menu;

import entity.Chat;
import entity.User;
import service.ChatService;
import service.UserService;
import utils.ApplicationContext;
import utils.input.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class ChatMenu extends Menu{
    private final User user;
    private final ChatService chatService;
    public final UserService userService = ApplicationContext.getUserService();
    public ChatMenu(User user,ChatService chatService) {
        super(new String[]{"Add Chat","Show Chats and Groups","Create Group","Back"});
        this.user = user;
        this.chatService = chatService;
    }
    public void runMenu() {
        User user1 = userService.findById(user.getId());
        while (true){
            print();
            switch (chooseOperation()) {
                case 1:
                    addChat(user1);
                    break;
                case 2:
                    chatService.showChats(user1);
                    System.out.println("1. go to a chat or group");
                    System.out.println("2. delete a chat or group");
                    System.out.println("3. back");
                    int check = number(2).intValue();
                    if(check ==1){
                        System.out.println("Enter number of chat or group :");
                        int chatId = number(user1.getChats().size()).intValue();
                        chatService.showChat(user1.getChats().get(chatId-1));
                        if(user1.getChats().get(chatId-1).getName()==null)
                            new MassageMenu(user1,user1.getChats().get(chatId-1),chatService).runMenu();
                        else{
                            if(user1.getChats().get(chatId-1).getAdmins().contains(user1)){
                                new MassageGroupMenu(user1,user1.getChats().get(chatId-1),chatService,
                                        new String[]{"add massage","Delete massage"
                                        ,"edit massage","forward massage","search massage",
                                        "add user","delete user","block user","close group",
                                                "show users","back"}).runMenu(1);
                            }
                            else{
                                if(user1.getChats().get(chatId-1).getBlockList().contains(user1)){
                                    System.out.println("you are blocked");
                                }
                                else {
                                    new MassageGroupMenu(user1,user1.getChats().get(chatId-1),chatService,
                                            new String[]{"add massage","Delete massage"
                                                    ,"edit massage","forward massage",
                                                    "search massage","show users","back"}).runMenu("o");
                                }
                            }
                        }

                    }
                    else if(check == 2){
                        deleteChat(user);
                    }
                    break;
                case 3:
                    createGroup();
                    break;
                case 5:
                    return;
            }
        }
    }
    private void addChat(User user1){
        System.out.println("Enter the id of the desired person :");
        Long id = number();
        User user2 = userService.findById(id);
        if(user2 ==null){
            System.out.println("This user does not exist!");
            return;
        }
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        chatService.addChat(users);
    }
    private void createGroup(){
        List<User> persons = new ArrayList<>();
        persons.add(user);
        while (true){
            System.out.println("1. add person");
            System.out.println("2. finish");
            System.out.println("3. back");
            int check = number(3).intValue();
            if(check == 1){
                while (true){
                    System.out.println("Enter a person's ID :");
                    Long id = number();
                    if(userService.findById(id)!=null){
                        persons.add(userService.findById(id));
                        break;
                    }
                    else{
                        System.out.println("invalid!");
                    }
                }

            }
            else if(check == 2){
                if(persons.size()==1){
                    System.out.println("invalid!");
                }
                else{
                    System.out.println("Enter name of group :");
                    String name = Input.scanner.nextLine();
                    chatService.addGroup(persons,name);
                    return;
                }
            }
            else if(check == 3){
                return;
            }
        }
    }

    private void deleteChat(User user1){
        System.out.println("Enter the id of the desired person :");
        int id = number(user1.getChats().size()).intValue()-1;
        Chat chat = user1.getChats().get(id);
        chatService.deleteChat(chat,user1);
    }

    public static Long number(int k){
        Scanner scanner = Input.scanner;
        Long id ;
        Boolean test = true;
        while (true){
            test = true;
            String text = scanner.nextLine();
            if((int)text.charAt(0)!=48){
                for (int i = 0; i < text.length(); i++) {
                    if((int)text.charAt(i)>57||(int)text.charAt(i)<48){
                        test=false;
                        break;
                    }
                }
            }else
                test=false;
            if(test){
                id = Long.parseLong(text);
                if(id <= k )
                    break;
            }
            else
                System.out.println("invalid!");

        }
        return id;
    }
    public static Long number(){
        Scanner scanner = Input.scanner;
        Long id ;
        Boolean test = true;
        while (true){
            test = true;
            String text = scanner.nextLine();
            if((int)text.charAt(0)!=48){
                for (int i = 0; i < text.length(); i++) {
                    if((int)text.charAt(i)>57||(int)text.charAt(i)<48){
                        test=false;
                        break;
                    }
                }
            }else
                test=false;
            if(test){
                id = Long.parseLong(text);
                break;
            }
            else
                System.out.println("invalid!");

        }
        return id;
    }
}
