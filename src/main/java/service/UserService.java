package service;

import base.service.BaseEntityService;
import entity.User;
import entity.dto.SearchUserDto;

import java.util.List;

public interface UserService extends BaseEntityService<User, Long> {

    User login();

    void signUp();

    User editUsername(User user);

    List<User> showPostAllOfUsers();

    void blockUser(User user,User user1);

    User findByUsername(SearchUserDto searchUserDto);

    User existByUsername(String username);

    void addFollower(User user,User user1);

    List<User> getUserForShowPosts(User user);
}
