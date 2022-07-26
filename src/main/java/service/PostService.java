package service;

import base.service.BaseEntityService;
import entity.Post;
import entity.User;

public interface PostService extends BaseEntityService<Post, Long> {

    void addPost(User user);

    void editPost(Post post);

    void showPosts(User user);

    void deleteById(Long Id);

}
