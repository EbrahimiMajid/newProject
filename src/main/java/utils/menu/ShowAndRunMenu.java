package utils.menu;

public class ShowAndRunMenu extends Menu{

    public ShowAndRunMenu(){
        super(new String[]{"Add Account","Exit"});
    }

    public void runMenu() {
        //while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    new AccountMenu().runMenu();
                    break;
                case 2:
                    return;
            }
        //}
    }
}
