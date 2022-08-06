package utils.menu;

import entity.Post;

import java.util.Comparator;

public class PostSortingComparator implements Comparator<Post> {
    @Override
    public int compare(Post post1, Post post2) {
        return post1.getLastUpdateDateTime().compareTo(post2.getLastUpdateDateTime());
    }
}
