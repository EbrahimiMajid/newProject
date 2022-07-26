package utils.menu;

import entity.User;
import service.UserService;
import utils.InputInformation;
import utils.input.Input;

public class EditInformationUserMenu extends Menu {
    private final User user;
    private final UserService userService;

    public EditInformationUserMenu(User user, UserService userService) {
        super(new String[]{"Edit FirstName", "Edit LastName", "Edit Username", "Edit Bio", "Edit Password", "Edit PhoneNumber", "Edit email", "Edit Age", "Back"});
        this.user = user;
        this.userService = userService;
    }

    public void runMenu() {
        while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    editFirstName();
                    break;
                case 2:
                    editLastName();
                    break;
                case 3:
                    userService.save(userService.editUsername(user));
                    break;
                case 4:
                    editBio();
                    break;
                case 5:
                    editPassword();
                    break;
                case 6:
                    editPhoneNumber();
                    break;
                case 7:
                    editEmail();
                    break;
                case 8:
                    editAge();
                    break;

                case 9:
                    return;
            }
        }
    }

    private void editFirstName() {
        user.getUserProfile().setFirstName(InputInformation.getFirstName());
        user.getUserProfile().setUser(user);
        userService.save(user);
    }

    private void editLastName() {
        user.getUserProfile().setLastName(InputInformation.getLastName());
        user.getUserProfile().setUser(user);
        userService.save(user);
    }

    private void editBio() {
        user.getUserProfile().setBio(new Input("Enter your bio :").getInputString());
        user.getUserProfile().setUser(user);
        userService.save(user);

    }

    private void editPassword() {
        user.setPassword(new Input("Enter your password :").getInputString());
        userService.save(user);
    }

    private void editPhoneNumber() {
        user.getUserProfile().setPhoneNumber(InputInformation.getPhoneNumber());
        user.getUserProfile().setUser(user);
        userService.save(user);
    }


    private void editEmail() {
        user.getUserProfile().setEmail(new Input("Enter your email :").getInputString());
        user.getUserProfile().setUser(user);
        userService.save(user);
    }

    private void editAge() {
        user.getUserProfile().setAge(new Input("Enter your age :").getInputInt());
        user.getUserProfile().setUser(user);
        userService.save(user);
    }


}
