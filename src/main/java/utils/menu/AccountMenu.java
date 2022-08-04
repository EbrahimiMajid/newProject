package utils.menu;

import Hello.ChatHandle;
import entity.User;
import utils.ApplicationContext;
import utils.input.Input;

import java.util.Objects;
import java.util.Scanner;

public class AccountMenu extends Menu{
    public AccountMenu() {
        super(new String[]{"Log Into Existing Account","Create New Account","Back"});
    }

    public void runMenu() {
        //while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    User user = ApplicationContext.getUserService().login();
                    while (Objects.isNull(user)){
                        System.out.println("Your password or username is wrong...");
                        user = ApplicationContext.getUserService().login();
                    }
                    ApplicationContext.getUserService().save(user);
                    new ChatHandle(user);
                    //new ProfileMenu(user).runMenu();
                    break;
                case 2:
                    ApplicationContext.getUserService().signUp();
                    break;
                case 3:
                    return;
            }
        //}
    }
}
