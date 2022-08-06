package repository;

import base.repository.BaseEntityRepository;
import entity.Comment;
import entity.Post;

public interface CommentRepository extends BaseEntityRepository<Comment, Long> {

    void showComments(Post post);

}
