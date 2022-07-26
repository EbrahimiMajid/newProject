package repository;

import base.repository.BaseEntityRepository;
import entity.User;
import entity.dto.SearchUserDto;

import java.util.List;

public interface UserRepository extends BaseEntityRepository<User, Long> {

    User existByUsername(String username);

    List<User> showPostAllOfUsers();

    User showChats();


    User findByUsername(SearchUserDto searchUserDto);
}
