package repository.impl;

import base.repository.impl.BaseEntityRepositoryImpl;
import entity.Post;
import entity.User;
import entity.Visit;
import repository.PostRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public void showVisits(Post post, String time) {
        List<Visit> visits =  entityManager.createQuery(
                "from Visit t WHERE t.post =: p", Visit.class)
                .setParameter("p",post).getResultList();
        String time1 = time+"T00:00:00";
        String time2 = time+"T23:59:59";
        LocalDateTime localDateTime1 = LocalDateTime.parse(time1);
        LocalDateTime localDateTime2 = LocalDateTime.parse(time2);
        int number=0;
        for (Visit visit : visits) {
            if(localDateTime1.compareTo(visit.getLocalDateTime()) < 0 &&
                    localDateTime1.compareTo(visit.getLocalDateTime()) > 0){
                number++;
            }
        }

        System.out.println("Visits :"+number);

    }
}
