package utils;

import repository.ChatRepository;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;
import repository.impl.ChatRepositoryImpl;
import repository.impl.CommentRepositoryImpl;
import repository.impl.PostRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.ChatService;
import service.CommentService;
import service.PostService;
import service.UserService;
import service.impl.ChatServiceImpl;
import service.impl.CommentServiceImpl;
import service.impl.PostServiceImpl;
import service.impl.UserServiceImpl;

import javax.persistence.EntityManager;

public class ApplicationContext {

    private static final UserRepository userRepository;

    private static final UserService userService;

    private static final PostRepository postRepository;

    private static final PostService postService;

    private static final ChatRepository chatRepository;

    private static final ChatService chatService;

    private static final CommentRepository commentRepository;

    private static final CommentService commentService;

    static {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        userRepository = new UserRepositoryImpl(entityManager);
        userService = new UserServiceImpl(userRepository,entityManager);

        postRepository = new PostRepositoryImpl(entityManager);
        postService = new PostServiceImpl(postRepository);

        chatRepository = new ChatRepositoryImpl(entityManager);
        chatService = new ChatServiceImpl(chatRepository,entityManager);

        commentRepository = new CommentRepositoryImpl(entityManager);
        commentService = new CommentServiceImpl(commentRepository);

    }

    public static ChatService getChatService() {
        return chatService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static PostService getPostService() {
        return postService;
    }

    public static CommentService getCommentService() {
        return commentService;
    }

}
