package entity;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.pool.TypePool;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = NestedComment.TABLE_NAME)
@NamedEntityGraph(name = "nested_comment_likes",
        attributeNodes = {
                @NamedAttributeNode("likes")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NestedComment extends BaseEntity<Long> {
    public static final String TABLE_NAME = "comment_table";
    public static final String TEXT_COMMENT = "text_comment";
    public static final String CREATE_DATE_TIME = "create_date_time";
    public static final String LAST_UPDATE_DATE_TIME = "last_update_date_time";
    @Column(name = TEXT_COMMENT)
    private String textComment;
    @Column(name = CREATE_DATE_TIME, nullable = false)
    private LocalDateTime createDateTime;
    @Column(name = LAST_UPDATE_DATE_TIME, nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "nested_comment_like")
    private List<User> likes = new ArrayList<>();
    @Override
    public String toString() {
        return "@" + user.getUsername() + ":\n" +
                textComment + "\n";
    }
}
