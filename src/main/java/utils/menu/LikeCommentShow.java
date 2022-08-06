package utils.menu;

import entity.Comment;
import entity.NestedComment;
import entity.Post;
import entity.User;
import service.CommentService;
import service.PostService;
import utils.ApplicationContext;
import utils.input.Input;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LikeCommentShow extends Menu{
    private final User user;
    private final Post post;
    LikeCommentShow(User user, Post post)
    {
        super(new String[]{"Like/Dislike","Comment","Show Likes","Back"});
        this.user=user;
        this.post=post;
    }
    private final CommentService commentService = ApplicationContext.getCommentService();
    PostService postService=ApplicationContext.getPostService();
    Scanner scanner=new Scanner(System.in);
    public void runMenu()
    {
        while (true)
        {
            print();
            switch (chooseOperation())
            {
                case 1:
                    boolean isLiked=false;
                    for (int i=0;i<post.getLikes().size();i++)
                    {
                        if (post.getLikes().get(i).equals(user)) {
                            isLiked = true;
                            break;
                        }
                    }
                    if (isLiked)
                    {
                        post.getLikes().remove(user);
                        System.out.println("You dislike this post.");
                    }
                    else
                    {
                        post.getLikes().add(user);
                        System.out.println("You liked this post");
                    }
                        postService.save(post);
                    break;
                case 2:
                    System.out.println("1-Like comment 2-show comments 3-add comment 4-add comment to comment");
                    System.out.println("Please choose one item :");
                    int addOrLike=scanner.nextInt();
                        if (addOrLike==1)
                        {
                            if (post.getComments()!=null)
                            {
                                List<Comment> comments = new ArrayList<>(post.getComments());
                                for (int i=1;i<=comments.size();i++)
                                {
                                    System.out.println(i+" "+comments.get(i-1).getTextComment());
                                }
                                System.out.println("Please enter your comment number to like/dislike: ");
                                int commentNumber=scanner.nextInt();
                                boolean isLiked1=false;
                                if (comments.get(commentNumber-1)!=null)
                                {
                                    for (int i=0;i<comments.get(commentNumber-1).getLikes().size();i++)
                                    {
                                        if (comments.get(commentNumber - 1).getLikes().get(i).equals(user)) {
                                            isLiked1 = true;
                                            break;
                                        }
                                    }
                                    if (isLiked1)
                                    {
                                        comments.get(commentNumber-1).getLikes().remove(user);
                                        System.out.println("You disliked this comment.");
                                    }
                                    else
                                    {
                                        comments.get(commentNumber-1).getLikes().add(user);
                                        System.out.println("You liked this comment");
                                    }
                                    commentService.save(comments.get(commentNumber-1));
                                }
                                else
                                {
                                    System.out.println("invalid comment Number ...");
                                }
                            }
                        }
                        else if (addOrLike==2)
                        {
                            for (int i=0;i<post.getComments().size();i++)
                            {
                                System.out.println(post.getComments().get(i).getTextComment());
                                System.out.println(post.getComments().get(i).getLastUpdateDateTime().getHour()+" : "+post.getComments().get(i).getLastUpdateDateTime().getMinute());
                                System.out.println(post.getComments().get(i).getUser().getUsername());
                            }
                        }
                        else if (addOrLike==3)
                        {
                            commentService.addComment(post,user);
                            postService.save(post);
                        }
                        else if (addOrLike==4)
                        {
                            if (post.getComments()!=null)
                            {
                                List<Comment> comments = new ArrayList<>(post.getComments());
                                for (int i=0;i<comments.size();i++)
                                {
                                    System.out.println(i+" "+comments.get(i).getTextComment());
                                    System.out.println(comments.get(i).getLastUpdateDateTime());
                                    System.out.println(comments.get(i).getUser().getUsername());
                                }
                                System.out.println("Please enter your comment number to comment: ");
                                int commentNumber=scanner.nextInt();
                                if (post.getComments().get(commentNumber-1)!=null)
                                {
                                    NestedComment nestedComment=new NestedComment();
                                    nestedComment.setTextComment(new Input("Enter your comment :").getInputString());
                                    nestedComment.setUser(user);
                                    nestedComment.setComment(post.getComments().get(commentNumber-1));
                                    nestedComment.setCreateDateTime(LocalDateTime.now());
                                    nestedComment.setLastUpdateDateTime(LocalDateTime.now());
                                    post.getComments().get(commentNumber-1).getNestedComments().add(nestedComment);
                                    commentService.save(post.getComments().get(commentNumber-1));
                                    System.out.println("You add comment to this comment");
                                }
                                else
                                {
                                    System.out.println("invalid comment number ...");
                                }
                            }
                            else
                            {
                                System.out.println("This post doesn't contains any comment ... ");
                            }
                        }
                        else
                        {
                            System.out.println("invalid number ....");
                        }
                    break;
                case 3:
                        for (int i=0;i<post.getLikes().size();i++)
                        {
                            System.out.println(post.getLikes().size()+" person like this post.");
                            System.out.println(post.getLikes().get(i).getUsername());
                        }
                    break;
                case 4:
                    return;
            }
        }
    }
}
