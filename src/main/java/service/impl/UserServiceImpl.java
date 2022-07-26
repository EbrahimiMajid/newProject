package service.impl;

import base.service.impl.BaseEntityServiceImpl;

import entity.dto.SearchUserDto;
import repository.ChatRepository;
import repository.UserRepository;
import service.UserService;
//import utils.InputInformation;
//import utils.input.Input;
import entity.User;
import utils.InputInformation;
import utils.input.Input;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserServiceImpl extends BaseEntityServiceImpl<User, Long , UserRepository>
    implements UserService {

    private EntityManager entityManager ;

    public UserServiceImpl(UserRepository repository, EntityManager entityManager) {
        super(repository);
        this.entityManager = entityManager;
    }

    private final EntityTransaction transaction = repository.getEntityManger().getTransaction();

    @Override
    public User login() {
        String username = new Input("Enter your username :").getInputString();
        String password = new Input("Enter your password :").getInputString();
        User user = repository.existByUsername(username);
        try{
            if (user.getPassword().equals(password)){
                return user;
            }
            if (password.equals("0"))
            {
                String securityQuestion=new Input("What was your favorite school teacher’s name?(securityQuestion)").getInputString();
                if (user.getSecurityQuestion().equals(securityQuestion))
                {
                    String newPassword = new Input("Please enter your new password :").getInputString();
                    System.out.println("(Notice)If forget your password enter 0 in password Box !");
                    String replacePassword=new Input("Please repeat your new password :").getInputString();
                    while (!Objects.equals(replacePassword, password))
                    {
                        System.out.println("Please enter repeated password correctly !");
                        replacePassword=new Input("Please repeat your password :").getInputString();
                    }
                    user.setPassword(newPassword);
                    return user;
                }
            }
        } catch (NullPointerException ignored){

        }
        return null;
    }

    @Override
    public void signUp() {
        User user = new User();

        user.getUserProfile().setFirstName(InputInformation.getFirstName());

        user.getUserProfile().setLastName(InputInformation.getLastName());

        String username = new Input("Enter your username").getInputString();

        while (repository.existByUsername(username) != null) {
            System.out.println("this username is token before");
            username = new Input("Enter your username :").getInputString();
        }

        user.setUsername(username);

        user.setPassword(new Input("Enter your password").getInputString());

        while (user.getPassword().length()<7)
        {
            System.out.println("Please enter at least 7 character !");
            user.setPassword(new Input("Try again to your password :").getInputString());
        }

        String replacePassword=new Input("Please repeat your password :").getInputString();

        while (!Objects.equals(replacePassword, user.getPassword()))
        {
            System.out.println("Please enter repeated password correctly !");
            replacePassword=new Input("Please repeat your password :").getInputString();
        }

        user.getUserProfile().setPhoneNumber(InputInformation.getPhoneNumber());

        user.getUserProfile().setEmail(new Input("Enter your email :").getInputString());

        user.getUserProfile().setAge(new Input("Enter your Age :").getInputInt());

        user.setSecurityQuestion(new Input("(Security Question)What was your favorite school teacher’s name?").getInputString());

        user.getUserProfile().setTypeUser(new Input("Enter your type of user(Business account,Common account) : ").getInputString());

        user.setBusiness(user.getUserProfile().getTypeUser().equals("Business account"));

        user.getUserProfile().setBio(new Input("Enter your bio : ").getInputString());

        user.getUserProfile().setUser(user);

        repository.getEntityManger().getTransaction().begin();
        repository.save(user);
        repository.getEntityManger().getTransaction().commit();

        System.out.println("You are signup successfully...");

    }

    @Override
    public User editUsername(User user) {
        String username = new Input("Enter your username").getInputString();

        while (repository.existByUsername(username) != null) {
            System.out.println("this username is token before");
            username = new Input("Enter your username :").getInputString();
        }

        user.setUsername(username);
        return user;
    }

    @Override
    public List<User> showPostAllOfUsers() {
        return repository.showPostAllOfUsers();
    }

    @Override
    public User findByUsername(SearchUserDto searchUserDto) {
        return repository.findByUsername(searchUserDto);
    }

    @Override
    public User existByUsername(String username){
        return repository.existByUsername(username);
    }

    @Override
    public void addFollower(User user,User user1){
        if(user.getFollowers().contains(user1)){
            user.getFollowers().remove(user1);
            user1.getFollowings().remove(user);
        }
        else{
            user.getFollowers().add(user1);
            user1.getFollowings().add(user);
        }
        transaction.begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        }
        entityManager.merge(user);
        transaction.commit();

        transaction.begin();
        if (user.getId() == null) {
            entityManager.persist(user1);
        }
        entityManager.merge(user1);
        transaction.commit();
    }

    @Override
    public void blockUser(User user, User user1) {
        user.getBlockList().add(user1);
        transaction.begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        }
        entityManager.merge(user);
        transaction.commit();
    }

    @Override
    public List<User> getUserForShowPosts(User user) {
        List<User> users = new ArrayList<>();
        if(!user.getFollowers().contains(user))
            users.add(user);
        users.addAll(user.getFollowers());
        users.addAll(user.getFollowings());
        return users;
    }
}
