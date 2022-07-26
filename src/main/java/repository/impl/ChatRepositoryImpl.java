package repository.impl;

import base.repository.impl.BaseEntityRepositoryImpl;
import entity.Chat;
import entity.Massage;
import entity.User;
import repository.ChatRepository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ChatRepositoryImpl extends BaseEntityRepositoryImpl<Chat, Long>
        implements ChatRepository {
    public ChatRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Chat> getEntityClass() {
        return Chat.class;
    }

    @Override
    public List<Chat> Chats(List<User> users) {
        TypedQuery<Chat> query = entityManager.createQuery(
                "from Chat t WHERE 5 =: k", Chat.class).setParameter("k", 5);
        List<Chat> chats1 = query.getResultList();
        return chats1;
        //List<Chat> chat = entityManager.createQuery("").getResultList();


    }

    @Override
    public void showChats(User user) {

        TypedQuery<Chat> query = entityManager.createQuery(
                "from Chat t WHERE 5 =: k", Chat.class).setParameter("k", 5);
        List<Chat> chats1 = query.getResultList();
        List<Chat> chats2 = new ArrayList<>();
        for (Chat chat : chats1) {
            if(chat.getUsers().contains(user)){
                chats2.add(chat);
            }
        }
        //-----------------------
        //ترتیب نشان دادن چت ها
        //----------------------
        for (int i = 0; i < chats2.size(); i++) {
            System.out.print(i+1 +".");
            if(chats2.get(i).getName()==null){
                System.out.println(chats2.get(i).getUsers().get(1).getUserProfile().getFirstName()+"   "+
                        chats2.get(i).getUsers().get(1).getUserProfile().getLastName());
            }
            else{
                System.out.println(chats2.get(i).getName());
            }


        }
    }

    @Override
    public List<Massage> searchMassageInChat(Chat chat1,String text) {
        Chat chat = chat1;
        List<Massage> massages = new ArrayList<>();
        for (Massage massage : chat.getMassages()) {
            if(massage.getText().indexOf(text)!=-1){
                massages.add(massage);
            }
        }
        if(massages.size()==0){
            System.out.println("No result!");
            return null;
        }
        for (Massage massage : massages) {
            int i = chat.getMassages().indexOf(massage);
            if(chat.getMassages().get(i).getReplyText()!= null)
                System.out.println("reply to : "+chat.getMassages().get(i).getReplyText());
            System.out.println(chat.getMassages().indexOf(massage)+1 +".");
            System.out.println(chat.getMassages().get(i).getUser().getUserProfile().getFirstName()+"   "
                    +chat.getMassages().get(i).getUser().getUserProfile().getLastName());
            if(chat.getMassages().get(i).getText().length() > 5)
                System.out.println(chat.getMassages().get(i).getText().substring(0,4));
            else
                System.out.println(chat.getMassages().get(i).getText());
            System.out.println(chat.getMassages().get(i).getLastUpdateDateTime().getHour()+":"+
                    chat.getMassages().get(i).getLastUpdateDateTime().getMinute());
        }
        return massages;
    }

    @Override
    public void editMassage(int id, Chat chat1 , String text) {
        Chat chat = chat1;
        if(chat.getMassages().get(id).getForward() == false){
            chat.getMassages().get(id).setText(text);
            chat.getMassages().get(id).setLastUpdateDateTime(LocalDateTime.now());
        }
        else{
            System.out.println("This massage forwarded!");
        }

    }

    @Override
    public void deleteMassage(int id, Chat chat1) {
        Chat chat = chat1;
        if(id <= chat.getMassages().size()) {
            Long massageId = chat.getMassages().get(id - 1).getId();


        }
        else{
            System.out.println("invalid!");
        }
    }

    /*
    Chat chat = chat1;
        if(id <= chat.getMassages().size()){
            Long massageId = chat.getMassages().get(id-1).getId();
            entityManager.createQuery(
                    "DELETE FROM Massage m WHERE m.id= 3 "
            );



            //DELETE FROM Massage WHERE id=;
            //delete from Massage as t where t.id =: massageId
        }
        else{
            System.out.println("invalid!");
        }
     */

    @Override
    public Chat chat(List<User> users) {
        if(users.get(0).getBlockList().contains(users.get(1)))
            return null;
        List<Chat> temp = Chats(users);
        for (Chat chat : temp) {
            if(chat.getUsers().contains(users.get(0))){
                if(chat.getUsers().contains(users.get(1))){
                    return chat;
                }
            }
        }
        return null;
    }

    @Override
    public void addMassage(List<User> users,String text,String replyText) {
        Chat chat = chat(users);
        Massage massage = new Massage();
        massage.setText(text);
        massage.setCreateDateTime(LocalDateTime.now());
        massage.setLastUpdateDateTime(LocalDateTime.now());
        if(replyText!=null)
            massage.setReplyText(replyText);
        massage.setUser(users.get(0));
        massage.setChat(chat);
        chat.getMassages().add(massage);
    }

    @Override
    public void deleteChatById(Long id) {
        entityManager.createQuery(
                "delete from Chat as t where t.id =: id",
                Chat.class
        ).setParameter("id",id);
    }
}
