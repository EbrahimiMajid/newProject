package repository.impl;

import base.repository.impl.BaseEntityRepositoryImpl;
import entity.Comment;
import entity.Post;
import repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CommentRepositoryImpl extends BaseEntityRepositoryImpl<Comment, Long>
    implements CommentRepository {

    public CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public void showComments(Post post) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "from Comment c WHERE c.post.id =: id", Comment.class).setParameter("id", post.getId());
        query.getResultList().forEach(System.out::println);
    }

}
