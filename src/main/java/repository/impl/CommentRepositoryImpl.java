package repository.impl;

import base.repository.impl.BaseEntityRepositoryImpl;
import entity.Comment;
import repository.CommentRepository;

import javax.persistence.EntityManager;

public class CommentRepositoryImpl extends BaseEntityRepositoryImpl<Comment, Long>
    implements CommentRepository {

    public CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }

}
