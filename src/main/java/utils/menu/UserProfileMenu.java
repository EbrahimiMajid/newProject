package utils.menu;

import entity.User;
import service.UserService;
import utils.ApplicationContext;

public class UserProfileMenu extends Menu{
    private User user;
    private User user1;
    private static final UserService userService = ApplicationContext.getUserService();
    public UserProfileMenu(User user,User user1) {
        super(new String[]{"Follow or OnFollow","back"});//دیدن پست های فرد
        this.user = user;
        this.user1 = user1;
    }

    public void runMenu(){
        while (true){
            print();
            switch (chooseOperation()){
                case 1:
                    userService.addFollower(user,user1);
                    break;
                case 2:
                    return;
            }
        }
    }
}
