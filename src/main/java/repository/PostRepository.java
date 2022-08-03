package repository;

import base.repository.BaseEntityRepository;
import entity.Post;
import entity.User;

import java.time.LocalDateTime;

public interface PostRepository extends BaseEntityRepository<Post, Long> {
    void showPosts(User user);
    void deleteById(Long id);
    void showVisits(Post post, String time);
}
