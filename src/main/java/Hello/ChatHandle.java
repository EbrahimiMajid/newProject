package Hello;

import entity.User;
import service.CommentService;
import service.PostService;
import service.UserService;
import utils.ApplicationContext;

public class ChatHandle {
    public static User user = null;
    public static final UserService userService = ApplicationContext.getUserService();
    public static final service.ChatService chatService = ApplicationContext.getChatService();
    public static final PostService postService = ApplicationContext.getPostService();
    public static final CommentService commentService = ApplicationContext.getCommentService();

    public ChatHandle(User user) {
        this.user = user;
    }
}
