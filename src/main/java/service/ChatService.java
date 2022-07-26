package service;

import base.service.BaseEntityService;
import entity.Chat;
import entity.Massage;
import entity.Post;
import entity.User;

import java.util.List;

public interface ChatService extends BaseEntityService<Chat, Long> {

    void addChat(List<User> users);

    void addGroup(List<User> users,String name);

    void deleteUserFromGroup(User user,Chat chat);

    void editMassage(int id,Chat chat1,String text);

    void showChat(Chat chat1);

    void closeGroup(Chat chat);

    void blockUserInGroup(User user,Chat chat);

    void addFollower(User user,User user1,User user2);

    void deleteMassage(int id,Chat chat1);

    void addMassage(Chat chat1,User user ,String text,String replyText);

    List<Massage> searchMassageInChat(Chat chat1, String text);

    void forwardMassage(Chat chat1,User user,int id,Chat chat);

    void showChats(User user);

    void deleteChat(Chat chat1,User user);
}