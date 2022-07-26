package service;

import base.service.BaseEntityService;
import entity.Comment;
import entity.Post;
import entity.User;

public interface CommentService extends BaseEntityService<Comment, Long> {
//    List<String> commentsOfUser(Long id);

    void addComment(Post tweet , User user);

    void editComment(Comment comment);
}