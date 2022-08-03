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
import java.util.List;

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

    @Override
    public void showVisits(Post post,String time) {

    }

    @Override
    public List<Post> businessPosts() {
        return null;
    }

    @Override
    public void saveLikeAndDislikeInBusinessPost(Post post,User user, Boolean check) {
        if(check==null){
            if(!post.getNoReaction().contains(user)){
                post.getNoReaction().add(user);
                if(!post.getDisLikes().contains(user))
                    post.getDisLikes().remove(user);
            }
        }
        else if(check){
            post.getLikes().add(user);
            System.out.println("You liked this ad!");
        }
        else{
            if(!post.getDisLikes().contains(user)){
                post.getDisLikes().add(user);
                System.out.println("You disliked this ad!");
                if(!post.getNoReaction().contains(user))
                    post.getNoReaction().remove(user);
            }
        }
        transaction.begin();
        repository.save(post);
        transaction.commit();
    }


}
