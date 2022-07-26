package repository.impl;

import base.repository.impl.BaseEntityRepositoryImpl;
import entity.User;
import entity.dto.SearchUserDto;
import repository.UserRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserRepositoryImpl extends BaseEntityRepositoryImpl<User , Long>
    implements UserRepository {
    public static final String FETCH_GRAPH = "javax.persistence.fetchgraph";

    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }


    @Override
    public User existByUsername(String username) {
        TypedQuery<User> user_name = entityManager.createQuery(
                " select u from User u where u.username = : user_name ", User.class
        ).setParameter("user_name", username);
        try {
            return user_name.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public List<User> showPostAllOfUsers() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);

        CriteriaQuery<User> isDeleted = criteriaQuery.select(from).where(criteriaBuilder.isFalse(from.get("isDeleted")));

        TypedQuery<User> typedQuery = entityManager.createQuery(isDeleted);
        EntityGraph<User> entityGraph = entityManager.createEntityGraph(User.class);
        typedQuery.setHint(
                FETCH_GRAPH , entityGraph
        );
        return typedQuery.getResultList();
    }
    //----------------------------
    @Override
    public User showChats() {
        return null;
    }
    //----------------------------
    @Override
    public User findByUsername(SearchUserDto searchUserDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);

        Predicate username = criteriaBuilder.like(from.get("username"),
                "%" + searchUserDto.getUsername() + "%");
        Predicate isDelete = criteriaBuilder.isFalse(from.get("isDeleted"));


        criteriaQuery.where(criteriaBuilder.and(username,isDelete));

        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
