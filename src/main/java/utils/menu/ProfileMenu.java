package utils.menu;
import entity.User;
import entity.dto.SearchUserDto;
import service.CommentService;
import service.PostService;
import service.UserService;
import utils.ApplicationContext;
import utils.input.Input;
import java.util.List;
import java.util.Objects;

public class ProfileMenu extends Menu{
    private final User user;
    private static final UserService userService = ApplicationContext.getUserService();
    private static final service.ChatService chatService = ApplicationContext.getChatService();
    private static final PostService postService = ApplicationContext.getPostService();
    private static final CommentService commentService = ApplicationContext.getCommentService();
    public ProfileMenu(User user) {
        super(new String[]{"Edit Profile","Delete Account","Post","Comment",
                "like","Show Post Of All Users","Explore","Chat",
                "Show Followers","Show Followings","Log out"});
        this.user = user;
        System.out.println("Welcome to your work bench... \n"
                +user.getUserProfile().getFirstName() +"  "
                + user.getUserProfile().getLastName());
    }

    public void runMenu() {
        while (true){
            print();
            switch (chooseOperation()) {
                case 1:
                    new EditInformationUserMenu(user, userService).runMenu();
                    break;
                case 2:
                    new DeleteAccountMenu(user , userService).runMenu();
                    break;
                case 3:
                    new PostMenu(user , postService).runMenu();
                    break;
                case 4:
                    new CommentMenu(user , commentService  , userService).runMenu();
                    break;
                case 5:
                    new LikeMenu(user , userService , postService).runMenu();
                case 6:
                    List<User> users = userService.showPostAllOfUsers();
                    for (User user1 : users) {
                        System.out.println(user1.getPosts());
                    }
                    break;
                case 7:
                    search();
                    break;
                case 8:
                    new ChatMenu(user,chatService).runMenu();
                    break;
                case 9:
                    showFollowers(user);
                    break;
                case 10:
                    showFollowings(user);
                    break;
                case 11:
                    return;
            }
        }
    }

    public void search() {
        String username = new Input("Enter your username :").getInputString();
        SearchUserDto search = new SearchUserDto(username);
        User user = userService.findByUsername(search);
        if (Objects.isNull(user))
            System.out.println("User not found...");
        else
            System.out.println(user);
    }

    public void showFollowers(User user){
        List<User> users = user.getFollowers();
        System.out.println("Followers :");
        for (int i = 0; i < users.size(); i++) {
            System.out.print(i+1 +".");
            System.out.println(users.get(i).getUserProfile().getFirstName()+"  "+
                    users.get(i).getUserProfile().getLastName());
        }
        System.out.println("........................................");
    }

    public void showFollowings(User user){
        List<User> users = user.getFollowings();
        System.out.println("Followings :");
        for (int i = 0; i < users.size(); i++) {
            System.out.print(i+1 +".");
            System.out.println(users.get(i).getUserProfile().getFirstName()+"  "+
                    users.get(i).getUserProfile().getLastName());
        }
        System.out.println("........................................");
    }

    public void userProfile(Long id){
        User user = userService.findById(id);
        if(user!=null){
            System.out.println(user.getUserProfile().getFirstName()
                    +"  "+user.getUserProfile().getLastName()+" :");
            System.out.println("id :"+id);
            System.out.println("followers :"+user.getFollowers().size());
        }

    }
}
