package service.impl;

import base.service.impl.BaseEntityServiceImpl;
import entity.Post;
import entity.User;
import repository.PostRepository;
import service.PostService;
import utils.input.Input;
//import utils.input.Input;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;

public class PostServiceImpl extends BaseEntityServiceImpl<Post, Long, PostRepository>
        implements PostService {

    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }

    private final EntityTransaction transaction = repository.getEntityManger().getTransaction();

    @Override
    public void addPost(User user) {
        Post post = new Post();
        post.setText(new Input(
                "Enter your text :",
                "Your text must be a maximum of 280 characters",
                "", null).getInputTextString());
        post.setCreateDateTime(LocalDateTime.now());
        post.setLastUpdateDateTime(LocalDateTime.now());
        post.setUser(user);
        user.getPosts().add(post);
        transaction.begin();
        repository.save(post);
        transaction.commit();

    }

    @Override
    public void editPost(Post post) {
        post.setText(new Input(
                "Enter your text :",
                "Your text must be a maximum of 280 characters",
                "", null).getInputTextString());
        post.setLastUpdateDateTime(LocalDateTime.now());
        transaction.begin();
        repository.save(post);
        transaction.commit();
    }

    @Override
    public void showPosts(User user) {
        repository.showPosts(user);
    }

    @Override
    public void deleteById(Long Id) {
        repository.deleteById(Id);
    }


}
