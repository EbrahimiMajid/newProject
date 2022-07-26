package repository;

import base.repository.BaseEntityRepository;
import entity.Post;
import entity.User;

public interface PostRepository extends BaseEntityRepository<Post, Long> {
    void showPosts(User user);
    void deleteById(Long id);
}
