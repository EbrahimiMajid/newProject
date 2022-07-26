package utils.menu;

import java.util.List;

public class ShowUsersInformation<E> extends Menu{

    private List<E> es;
    private boolean haveAllowedEdit;
    public ShowUsersInformation(String[] texts , List<E> es) {
        super(texts);
        this.es = es;
    }

    public ShowUsersInformation(String[] texts , List<E> es , boolean haveAllowEdit) {
        this(texts , es);
        this.haveAllowedEdit = haveAllowEdit;
    }

    public E runMenu() {
        while (true) {
            print();
            int chooseOption = chooseOperation();
            if (chooseOption == getItems().length)
                return null;
            else if (haveAllowedEdit){
                showPost(chooseOption);
                return es.get(chooseOption -1);
            }
            else
                showPost(chooseOption);

        }
    }

    public void showPost(int chooseOption) {

        System.out.println(es.get(chooseOption - 1).toString());
    }
}
