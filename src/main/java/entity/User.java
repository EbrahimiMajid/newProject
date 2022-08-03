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
@Table(name = User.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long> {
   public final static String TABLE_NAME = "user_table";
   public static final String USER_ID = "user_id";

   private String username;

   private String password;

   @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
   private UserProfile userProfile = new UserProfile();

   @OneToMany(mappedBy = "user"  ,cascade = CascadeType.ALL)
   private List<Post> posts = new ArrayList<>();

   @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
   private List<Comment> comments = new ArrayList<>();

   @ManyToMany(mappedBy = "users" ,cascade = CascadeType.ALL)
   private List<Chat> chats = new ArrayList<>();

   @OneToMany
   @JoinTable(name = "block_user_table",
           joinColumns = {@JoinColumn(name = "chat_id",referencedColumnName = "id")},
           inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}
   )
   private List<User> blockList = new ArrayList<>();
   //----------------------------------
   @OneToMany
   @JoinTable(name = "follower_user_table",
           joinColumns = {@JoinColumn(name = "user1_id",referencedColumnName = "id")},
           inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}
   )
   private List<User> followers = new ArrayList<>();

   @OneToMany
   @JoinTable(name = "following_user_table",
           joinColumns = {@JoinColumn(name = "user1_id",referencedColumnName = "id")},
           inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")}
   )
   private List<User> followings = new ArrayList<>();

   @OneToMany(mappedBy = "user")
   private List<Visit> Visits;



   @Override
   public String toString() {
      return userProfile.getFirstName() + " " + userProfile.getLastName() + "\n"
              + username + "\n" + userProfile.getBio();
   }

}
