package entity;
import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "massage_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Massage extends BaseEntity<Long> {

    private String text;

    private String replyText;

    private Boolean forward;

    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "last_update_date_time", nullable = false)
    private LocalDateTime lastUpdateDateTime;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
