package service.impl;

import base.service.impl.BaseEntityServiceImpl;
import entity.Comment;
import entity.NestedComment;
import entity.Post;
import entity.User;
import repository.CommentRepository;
import service.CommentService;
import utils.input.Input;

import java.time.LocalDateTime;

public class CommentServiceImpl extends BaseEntityServiceImpl<Comment, Long , CommentRepository>
        implements CommentService {

    public CommentServiceImpl(CommentRepository repository) {
        super(repository);
    }


/*
    @Override
    public List<String> commentsOfUser(Long id) {
        return repository.commentsOfUser(id);
    }*/

    @Override
    public void addComment(Post post , User user) {
        Comment comment = new Comment();
        comment.setTextComment(new Input("Enter your comment :").getInputString());
        comment.setCreateDateTime(LocalDateTime.now());
        comment.setLastUpdateDateTime(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        user.getComments().add(comment);
        post.getComments().add(comment);
        repository.getEntityManger().getTransaction().begin();
        repository.save(comment);
        repository.getEntityManger().getTransaction().commit();
    }
    @Override
    public void addComment(User user,Comment comment)
    {
        NestedComment comment1= new NestedComment();
        comment1.setTextComment(new Input("Enter your comment :").getInputString());
        comment1.setCreateDateTime(LocalDateTime.now());
        comment1.setLastUpdateDateTime(LocalDateTime.now());
        comment1.setUser(user);
        comment1.setComment(comment);
        user.getNestedComments().add(comment1);
        repository.getEntityManger().getTransaction().begin();
        repository.save(comment);
        repository.getEntityManger().getTransaction().commit();
    }

    @Override
    public void editComment(Comment comment) {
        comment.setTextComment(
                new Input("Enter your comment text :").getInputString()
        );
        comment.setLastUpdateDateTime(LocalDateTime.now());
        repository.getEntityManger().getTransaction().begin();
        repository.save(comment);
        repository.getEntityManger().getTransaction().commit();

    }

    @Override
    public void showComment(Post post) {
        repository.showComments(post);
    }
}
