package repository.impl;

import base.repository.impl.BaseEntityRepositoryImpl;
import entity.Post;
import entity.User;
import repository.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public  class PostRepositoryImpl extends BaseEntityRepositoryImpl<Post, Long>
    implements PostRepository {

    public PostRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Post> getEntityClass() {
        return Post.class;
    }

    @Override
    public void showPosts(User user) {
        TypedQuery<Post> query = entityManager.createQuery(
                "from Post t WHERE t.user.id    =: id", Post.class).setParameter("id", user.getId());

        query.getResultList().forEach(System.out::println);


    }
    @Override
    public void deleteById(Long id) {
        entityManager.createQuery(
                "delete from Post as t where t.id =: id",
                Post.class
        ).setParameter("id",id);
    }
}
