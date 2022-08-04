package repository;

import base.repository.BaseEntityRepository;
import entity.Chat;
import entity.Massage;
import entity.User;

import java.util.List;

public interface ChatRepository extends BaseEntityRepository<Chat, Long> {

    List<Chat> Chats(List<User> users);

    List<Chat> showChats(User user);

    List<Massage> searchMassageInChat(Chat chat1,String text);

    void editMassage(int id,Chat chat1,String text);

    void deleteMassage(int id,Chat chat1);

    Chat chat(List<User> users);

    void addMassage(List<User> users,String text,String replyText);

    void deleteChatById(Long id);

}
