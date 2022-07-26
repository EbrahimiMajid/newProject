package entity;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = Comment.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity<Long> {
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
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Override
    public String toString() {
        return "@" + user.getUsername() + ":\n" +
                textComment + "\n";
    }
}
