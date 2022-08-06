package utils.menu;

import entity.Post;
import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends Menu{
    private final User user;
    private List<Post> posts;
    MainMenu(User user)
    {
        super(new String[]{"Main","Back"});
        this.user=user;
    }
    public void runMenu()
    {
        while (true)
        {
            print();
            switch (chooseOperation())
            {
                case 1:
                    Scanner scanner=new Scanner(System.in);
                    List<User> users=new ArrayList<>();
                    users.addAll(user.getFollowers());
                    users.addAll(user.getFollowings());
                    users.add(user);
                    posts=new ArrayList<>(getPostOfUser(users));
                    posts.sort(new PostSortingComparator().reversed());
                    for (int i=1;i<=posts.size();i++)
                    {
                        System.out.println(i+" --> "+posts.get(i-1).getText());
                        System.out.println(posts.get(i-1).getLastUpdateDateTime().getHour()+" : "+posts.get(i-1).getLastUpdateDateTime().getMinute());
                    }
                    System.out.println("Please enter your post number that you want to like/dislike or comment or show likes:");
                    int postNumber=scanner.nextInt();
                    if (posts.get(postNumber-1)!=null)
                    {
                        new LikeCommentShow(user,posts.get(postNumber-1)).runMenu();
                    }
                    else
                    {
                        System.out.println("invalid post number ...");
                    }
                    break;
                case 2:
                    return;
            }
        }
    }
    List<Post> getPostOfUser(List<User> users)
    {
        List<Post> posts=new ArrayList<>();
        for (User value : users) {
            posts.addAll(value.getPosts());
        }
        return posts;
    }
}
