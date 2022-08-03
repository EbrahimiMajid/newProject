package utils.menu;

import entity.Post;
import entity.User;
import service.PostService;
import service.UserService;
import utils.ApplicationContext;
import utils.input.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostMenu extends Menu{
    private final User user;
    private final PostService postService;
    public final UserService userService = ApplicationContext.getUserService();
    public PostMenu(User user, PostService postService) {
        super(new String[]{"Add Post","Show My Posts","Edit Post","Delete Post","Back"});
        this.user = user;
        this.postService = postService;
    }


    public void runMenu() {
        User user1 = userService.findById(user.getId());
        while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    postService.addPost(user);
                    break;
                case 2:
                    for (Post post : user1.getPosts()) {
                        System.out.println(post);
                    }

                    break;
                case 3:
                    System.out.println("choose option for edit...");
                    Post post = getPostForEditOrDelete();
                    new EditPostMenu(post , postService , user1).runMenu();
                    break;

                case 4:
                    System.out.println("choose option for delete...");
                    Post deletePost = getPostForEditOrDelete();
                    if (!Objects.isNull(deletePost))
                        postService.delete(deletePost);

                    break;
                case 5:
                    return;

            }
        }
    }

    private Post getPostForEditOrDelete() {
        User user1 = userService.findById(user.getId());
        List<Post> posts = user1.getPosts();
        List<String> postTexts = new ArrayList<>();
        for (Post post : posts) {
            postTexts.add(post.getText());
        }
        postTexts.add("Back");
        String[] texts = postTexts.toArray(new String[0]);
        Post post = new ShowUsersInformation<Post>(texts , posts , true).runMenu();
        posts.remove(post);
        return post;
    }

    private void showVisits(Post post){
        while (true){
            System.out.println("Enter the desired date(****-**-**) :");
            String s= Input.scanner.nextLine();
            boolean test = true;
            if(s.length()==10){
                if(s.charAt(2)=='-'&&s.charAt(5)=='-'){
                    for (int i = 0; i < 10 ; i++) {
                        if(!((i!=2&&i!=5)&&((int)s.charAt(i)>=48&&(int)s.charAt(i)<=57))){
                            test=false;
                            break;
                        }
                    }
                }
                else
                    test=false;
            }
            else{
                test=false;
            }

            if(test){
                if(Integer.parseInt(s.substring(5,7))<=12&&Integer.parseInt(s.substring(8,10))<=30){
                    postService.showVisits(post,s);
                    break;
                }

            }
        }
    }

}
