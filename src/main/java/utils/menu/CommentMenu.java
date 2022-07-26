package utils.menu;

import entity.Comment;
import entity.Post;
import entity.User;
import service.CommentService;
import service.PostService;
import service.UserService;
import utils.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentMenu extends Menu {
    private final User user;
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService = ApplicationContext.getPostService();

    public CommentMenu(User user, CommentService commentService, UserService userService) {
        super(new String[]{"Add Comment", "Show My Comments", "Edit Comment", "Delete Comment", "Back"});
        this.user = user;
        this.commentService = commentService;
        this.userService = userService;
    }

    public void runMenu() {
        User user1 = userService.findById(user.getId());
        while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    Post post = addComment();
                    if (!Objects.isNull(post)){
                        commentService.addComment(post , user1);
                        postService.save(post);

                    }
                    break;
                case 2:
//                    commentService.commentsOfUser(user.getId()).forEach(System.out::println);
                  user1.getPosts().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("choose your option for edit...");
                    Comment comment = editOrDeleteComment();
                    if (!Objects.isNull(comment))
                        new EditCommentMenu(comment, commentService , user1).runMenu();
                    break;
                case 4:
                    System.out.println("choose your option for delete...");
                    Comment deleteComment = editOrDeleteComment();
                    if (!Objects.isNull(deleteComment))
                        commentService.delete(deleteComment);
                    break;
                case 5:
                    return;
            }
        }
    }

    private Comment editOrDeleteComment() {
        User user1 = userService.findById(user.getId());
        List<Comment> comments = user1.getComments();
        List<String> commentText = new ArrayList<>();
        for (Comment comment : comments) {
            commentText.add(comment.getTextComment());
        }
        commentText.add("Back");

        String[] texts = commentText.toArray(new String[0]);

        Comment comment = new ShowUsersInformation<Comment>(texts, comments, true).runMenu();
        comments.remove(comment);
        return comment;

    }

    private Post addComment() {
        List<List<Post>> posts = new ArrayList<>();
        for (User showPostAllOfUser : userService.showPostAllOfUsers()) {
            posts.add(showPostAllOfUser.getPosts());
        }
        List<Post> postList = new ArrayList<>();
        for (List<Post> post : posts) {
            postList.addAll(post);
        }

        List<String> texts = new ArrayList<>();
        for (Post post : postList) {
            texts.add(post.getText());
        }
        texts.add("Back");
        String[] textPosts = texts.toArray(new String[0]);
        System.out.println("Enter your tweet for add comment :");
       return new ShowUsersInformation<Post>(textPosts, postList, true).runMenu();

    }


}
