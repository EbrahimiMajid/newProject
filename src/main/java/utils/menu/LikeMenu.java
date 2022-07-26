package utils.menu;

import entity.Post;
import entity.User;
import service.PostService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LikeMenu extends Menu{
    private final User user;
    private final UserService userService;
    private final PostService postService;

    public LikeMenu(User user, UserService userService , PostService postService ){
        super(new String[]{"like","Back"});
        this.user = user;
        this.userService = userService;
        this.postService = postService;

    }

    public void runMenu() {
        while (true) {
            print();
            switch (chooseOperation()) {
                case 1:
                    Post post = addLike();
                    if (!Objects.isNull(post)) {
                        List<User> likes = post.getLikes();
                        boolean isLiked = isIn(user, likes);
                        if (isLiked){
                            post.getLikes().remove(user);
                            System.out.println("you disliked this tweet/before you disliked this tweet");

                        }
                        else {
                            post.getLikes().add(user);
                            System.out.println("you liked this tweet");
                        }
                        postService.save(post);
                    }
                    break;

                case 2:
                    return;

            }
        }
    }

    private boolean isIn(User user, List<User> likes) {
        for (User user1 : likes) {
            if (user1.equals(user))
                return true;
        }
        return false;
    }


    public Post addLike() {
        {
            List<List<Post>> posts = new ArrayList<>();
            for (User showTweetAllOfUser : userService.showPostAllOfUsers()) {
                posts.add(showTweetAllOfUser.getPosts());
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
            String[] textTweets = texts.toArray(new String[0]);
            System.out.println("Enter your tweet for add like/dislike :");
            return new ShowUsersInformation<Post>(textTweets, postList, true).runMenu();

        }
    }
}
