package service;

import base.service.BaseEntityService;
import entity.Post;
import entity.User;

import java.util.List;

public interface PostService extends BaseEntityService<Post, Long> {

    void addPost(User user);

    void editPost(Post post);

    void showPosts(User user);

    void deleteById(Long Id);

    void showVisits(Post post,String time);

    List<Post> businessPosts();

    void saveLikeAndDislikeInBusinessPost(Post post,User user,Boolean check);

}
