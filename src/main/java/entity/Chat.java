package entity;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat extends BaseEntity<Long> {

    @OneToMany(mappedBy = "chat",cascade = CascadeType.ALL)
    private List<Massage> massages = new ArrayList<>();

    private String name;

    private Boolean closed;

    @ManyToMany
    @JoinTable(name = "chat_user_table",
        joinColumns = {@JoinColumn(name = "chat_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}
    )
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "chat_admin_table",
            joinColumns = {@JoinColumn(name = "chat_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "admin_id",referencedColumnName = "id")}
    )
    private List<User> admins;

    @ManyToMany
    @JoinTable(name = "chat_block_table",
            joinColumns = {@JoinColumn(name = "chat_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "block_id",referencedColumnName = "id")}
    )
    private List<User> blockList;
}
