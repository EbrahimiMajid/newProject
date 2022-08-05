package service.impl;

import base.service.impl.BaseEntityServiceImpl;
import entity.Chat;
import entity.Massage;
import entity.Post;
import entity.User;
import repository.ChatRepository;
import service.ChatService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ChatServiceImpl extends BaseEntityServiceImpl<Chat, Long, ChatRepository>
        implements ChatService {
    private EntityManager entityManager;
    public ChatServiceImpl(ChatRepository repository, EntityManager entityManager) {
        super(repository);
        this.entityManager = entityManager;
    }

    private final EntityTransaction transaction = repository.getEntityManger().getTransaction();
    @Override
    public void addChat(List<User> users) {
        List<Chat> chats = repository.Chats(users);
        Boolean check = true;
        for (Chat chat : chats) {
            if(chat.getUsers().contains(users.get(0))){
                if(chat.getUsers().contains(users.get(1))){
                    check = false;
                    break;
                }
            }
        }
        if(check){
            Chat chat = new Chat();
            chat.setUsers(users);
            for (User user : users) {
                user.getChats().add(chat);
            }
            transaction.begin();
            repository.save(chat);
            transaction.commit();
            System.out.println("Chat successfully created.");
        }
        else{
            System.out.println("This chat exists");
        }

    }

    @Override
    public void addGroup(List<User> users,String name) {
        Chat chat = new Chat();
        chat.setName(name);
        chat.setUsers(users);
        chat.setClosed(false);
        chat.setAdmins(new ArrayList<>());
        chat.getAdmins().add(users.get(0));
        for (User user : users) {
            user.getChats().add(chat);
        }
        transaction.begin();
        repository.save(chat);
        transaction.commit();
        System.out.println("Group successfully created.");
    }

    @Override
    public void deleteUserFromGroup(User user, Chat chat) {
        chat.getUsers().remove(user);
        user.getChats().remove(chat);
        transaction.begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        }
        entityManager.merge(user);
        transaction.commit();
        transaction.begin();
        if (chat.getId() == null) {
            entityManager.persist(chat);
        }
        entityManager.merge(chat);
        transaction.commit();

    }

    @Override
    public void addFollower(User user,User user1,User user2){
        user.getFollowers().add(user1);
        user.getFollowers().add(user2);
        transaction.begin();
        if (user.getId() == null) {
            entityManager.persist(user);
        }
        entityManager.merge(user);
        transaction.commit();
    }

    @Override
    public void editMassage(int id,Chat chat1,String text) {
        repository.editMassage(id,chat1,text);
        transaction.begin();
        if (chat1.getMassages().get(id).getId() == null) {
            entityManager.persist(chat1.getMassages().get(id));
        }
        entityManager.merge(chat1.getMassages().get(id));
        transaction.commit();
    }

    @Override
    public void showChat(Chat chat1) {
        Chat chat = chat1;
        if(chat==null){
            System.out.println("This chat does not exist.");
        }
        else{
            for (int i = 0; i < chat.getMassages().size(); i++) {
                if(chat.getMassages().get(i).getReplyText()!= null)
                    System.out.println("reply to : "+chat.getMassages().get(i).getReplyText());
                System.out.print( i+1 +".");
                System.out.println(chat.getMassages().get(i).getUser().getUserProfile().getFirstName()+"   "
                        +chat.getMassages().get(i).getUser().getUserProfile().getLastName()+":");

                System.out.println(chat.getMassages().get(i).getText());
                System.out.println(chat.getMassages().get(i).getLastUpdateDateTime().getHour()+":"+
                        chat.getMassages().get(i).getLastUpdateDateTime().getMinute());
            }
        }
    }

    @Override
    public void closeGroup(Chat chat) {
        if(chat.getClosed())
            chat.setClosed(false);
        else
            chat.setClosed(true);
        transaction.begin();
        if (chat.getId() == null) {
            entityManager.persist(chat);
        }
        entityManager.merge(chat);
        transaction.commit();
    }

    @Override
    public void blockUserInGroup(User user, Chat chat) {
        if(chat.getBlockList().contains(user)){
            chat.getBlockList().remove(user);
            System.out.println(user.getUserProfile().getFirstName()+"  "+
                    user.getUserProfile().getLastName()+"unblocked");
        }
        else{
            chat.getBlockList().add(user);
            System.out.println(user.getUserProfile().getFirstName()+"  "+
                    user.getUserProfile().getLastName()+"blocked");
        }

        transaction.begin();
        if (chat.getId() == null) {
            entityManager.persist(chat);
        }
        entityManager.merge(chat);
        transaction.commit();
    }

    @Override
    public void addMassage(Chat chat1,User user, String text, String replyText) {
        Chat chat = chat1;
        Massage massage = new Massage();
        massage.setText(text);
        massage.setForward(false);
        massage.setCreateDateTime(LocalDateTime.now());
        massage.setLastUpdateDateTime(LocalDateTime.now());
        if(replyText!=null)
            massage.setReplyText(replyText);
        massage.setUser(user);
        massage.setChat(chat);
        chat.getMassages().add(massage);
        transaction.begin();
        if (massage.getId() == null) {
            entityManager.persist(massage);
        }
        entityManager.merge(massage);
        transaction.commit();
    }

    @Override
    public List<Massage> searchMassageInChat(Chat chat1, String text) {
        return repository.searchMassageInChat(chat1,text);
    }

    @Override
    public void forwardMassage(Chat chat2,User user ,int id, Chat chat) {
        Chat chat1 = chat2;
        System.out.println(chat1.getMassages().size());
        if(chat1.getMassages().size() <= id){
            System.out.println("invalid!");
            return;
        }
        chat1.getMassages().get(id).setForward(true);
        String text = chat1.getMassages().get(id).getText();
        Massage massage = new Massage();
        massage.setText("forwarded from "+
                chat1.getMassages().get(id).getUser().getUserProfile().getFirstName()+"\n"+text);
        massage.setForward(false);
        massage.setCreateDateTime(LocalDateTime.now());
        massage.setLastUpdateDateTime(LocalDateTime.now());
        massage.setUser(user);
        massage.setChat(chat);
        chat.getMassages().add(massage);
        transaction.begin();
        if (massage.getId() == null) {
            entityManager.persist(massage);
        }
        entityManager.merge(massage);
        transaction.commit();
    }

    @Override
    public List<Chat> showChats(User user) {
        return repository.showChats(user);
    }

    @Override
    public void deleteChat(Chat chat1,User user) {
        Chat chat = chat1;
        List<User> users = chat.getUsers();
        if(chat==null){
            System.out.println("This chat does not exist.");
        }
        else{
            for (User user1 : users) {
                user1.getChats().remove(chat);
                transaction.begin();
                if (user1.getId() == null) {
                    entityManager.persist(user1);
                }
                entityManager.merge(user1);
                transaction.commit();
            }
            System.out.println("The chat was deleted.");
        }
    }

    @Override
    public void deleteMassage(int id, Chat chat1) {

        Long Id = chat1.getMassages().get(id-1).getId();
        chat1.getMassages().remove(chat1.getMassages().get(id-1));
        transaction.begin();
            entityManager.createQuery(
                    "DELETE FROM Massage m WHERE m.id=: g "
            ).setParameter("g",Id).executeUpdate();
        transaction.commit();
    }
}