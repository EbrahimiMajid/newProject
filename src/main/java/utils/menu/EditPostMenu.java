package utils.menu;

import entity.Post;
import entity.User;
import service.PostService;
import service.UserService;
import utils.ApplicationContext;


public class EditPostMenu extends Menu{
    private final Post post;
    private final PostService postService;
    private final UserService userService = ApplicationContext.getUserService();
    private User user;

    public EditPostMenu(Post post, PostService postService, User user1) {
        super(new String[]{"Edit Text","Back"});
        this.postService = postService;
        this.post = post;
        this.user = user1;
    }

    public void runMenu() {
        while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    postService.editPost(post);
                    userService.save(user);
                    break;
                case 2:
                    return;

            }
        }
    }
}
