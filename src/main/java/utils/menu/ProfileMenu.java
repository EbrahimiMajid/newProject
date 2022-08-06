package utils.menu;
import entity.Post;
import entity.User;
import entity.dto.SearchUserDto;
import service.CommentService;
import service.PostService;
import service.UserService;
import utils.ApplicationContext;
import utils.input.Input;

import java.util.*;

public class ProfileMenu extends Menu{
    private final User user;
    private static final UserService userService = ApplicationContext.getUserService();
    private static final service.ChatService chatService = ApplicationContext.getChatService();
    private static final PostService postService = ApplicationContext.getPostService();
    private static final CommentService commentService = ApplicationContext.getCommentService();
    public ProfileMenu(User user) {
        super(new String[]{"Edit Profile","Delete Account","Post","Comment",
                "like","Show Post Of All Users","Explore","Chat",
                "Show Followers","Show Followings","User suggestion","Advertisement offer","Log out"});
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
                    for (User user1 : users){
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
                    userSuggestion(user);
                    break;
                case 12:
                    AdvertisementOffer(user,user.getAdvertise());
                case 13:
                    return;
            }
        }
    }

    public void AdvertisementOffer(User user,Post post){
        List<Post> posts = postService.businessPosts();
        if(posts.size()==0){
            System.out.println("no result.");
            return;
        }
        Post star = posts.get(0);
        if(post != null){
            List<Post> removes = new ArrayList<>();
            for (int i = 0; i < posts.size(); i++) {
                if(posts.get(i).getLikes().contains(user))
                    removes.add(posts.get(i));
            }
            for (int i = 0; i < removes.size(); i++) {
                posts.remove(removes.get(i));
            }
            if(posts.size()==0){
                System.out.println("no result.");
                return;
            }
            else{
                ArrayList<Integer> numbers = new ArrayList<>();
                List<User> likeUsers = post.getLikes();
                List<User> disLikes = post.getDisLikes();
                List<User> onReaction = post.getNoReaction();
                int number;
                for (int i = 0; i < posts.size(); i++) {
                    number = 0;

                    for (User likeUser : likeUsers) {
                        if(posts.get(i).getLikes().contains(likeUser))
                            number++;
                    }

                    for (User disLike : disLikes) {
                        if(posts.get(i).getDisLikes().contains(disLike))
                            number++;
                    }

                    for (User user1 : onReaction) {
                        if(posts.get(i).getNoReaction().contains(user1))
                            number++;
                    }

                    numbers.add(number);
                }
                int n = numbers.indexOf(Collections.max(numbers));
                star = posts.get(n);
        }}


            user.setAdvertise(star);

            System.out.println(star.getUser().getUserProfile().getFirstName()
                    +"  "+star.getUser().getUserProfile().getLastName()+":");
            System.out.println(star.getText());
            System.out.println();
            System.out.println("1.like");
            System.out.println("2.dislike");
            System.out.println("3.back");
            int check = ChatMenu.number(3).intValue();
            if(check==1){
                postService.saveLikeAndDislikeInBusinessPost(star,user,true);
            }
            else if(check==2){
                postService.saveLikeAndDislikeInBusinessPost(star,user,false);
            }
            else{
                postService.saveLikeAndDislikeInBusinessPost(star,user,null);
            }


    }

    public void userSuggestion(User user){
        List<User> friend = userService.getUserForShowPosts(user);
        List<User> fFriend = new ArrayList<>();
        for (User user1 : friend) {
            for (User user2 : userService.getUserForShowPosts(user1)) {
                if(!friend.contains(user2)&&!fFriend.contains(user2))
                    fFriend.add(user2);
            }
        }
        List<Integer> finish = new ArrayList<>();
        int number;
        for (int i = 0; i < fFriend.size(); i++) {
            number=0;
            for (User user1 : userService.getUserForShowPosts(fFriend.get(i))) {
                if(friend.contains(user1))
                    number+=1;
            }
            finish.add(number);
        }
        List<User> Final = new ArrayList<>();
        for(int i=0; i< finish.size() ; i++){
            int k = Collections.max(finish);
            int j = finish.indexOf(k);
            Final.add(fFriend.get(j));
            finish.set(k,-1);
        }
        for (int i = 0; i < Final.size(); i++) {
            System.out.print(i+1 +". ");
            System.out.println(Final.get(i).getUserProfile().getFirstName()
                    +"  "+Final.get(i).getUserProfile().getLastName());
            System.out.println("  id : "+Final.get(i).getId());
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
        User user1 = userService.findById(id);
        if(user1!=null){
            System.out.println(user1.getUserProfile().getFirstName()
                    +"  "+user1.getUserProfile().getLastName()+" :");
            System.out.println("id :"+id);
            System.out.println("followers :"+user1.getFollowers().size());
            System.out.println();
            if(user.getFollowers().contains(user1)){
                System.out.println("1. unfollow");
                System.out.println("2. back");
                int check = ChatMenu.number(2).intValue();
                if(check==1){
                    userService.addFollower(user,user1);
                }
            }
            else{
                System.out.println("1. follow");
                System.out.println("2. back");
                int check = ChatMenu.number(2).intValue();
                if(check==1){
                    userService.addFollower(user,user1);
                }
            }
        }
    }
}
