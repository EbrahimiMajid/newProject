package utils.menu;

import entity.Comment;
import entity.User;
import service.CommentService;
import service.UserService;
import utils.ApplicationContext;

public class EditCommentMenu extends Menu{
    private Comment comment;
    private User user;

    private final CommentService commentService;
    private final UserService userService = ApplicationContext.getUserService();

    public EditCommentMenu(Comment comment, CommentService commentService, User user1) {
        super(new String[] {"Edit Comment Text" ,"Exit"});
        this.comment = comment;
        this.commentService = commentService;
        this.user = user1;
    }

    public void runMenu() {
        while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    commentService.editComment(comment);
                    userService.save(user);
                    break;
                case 2:
                    return;
            }
        }
    }
}
