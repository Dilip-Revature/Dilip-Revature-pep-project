package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message createMessage(Message message){
        //check if message text is null or >= 255 characters
        if(
            message.getMessage_text() == null ||
            message.getMessage_text().trim().isEmpty() ||
            message.getMessage_text().length() >= 255    
        ){
            return null;
        }
        //check if posted_by is a real user
        Account account = accountDAO.getAccountById(message.getPosted_by());
        if(account != null){
            return messageDAO.createMessage(message);
        }


        return  null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesByAccountId(int id){
        return messageDAO.getMessagesByAccountId(id);
    }

    public Message getMessageById(int id){
        Message message = messageDAO.getMessageById(id);
        if(message != null){
            return message;
        }
            return null;
    }

    public Message deleteMessageById(int id){
        Message message = messageDAO.deleteMessageById(id);
        if(message != null){
            return message;
        } 
        return null;
    }

    public Message updateMessageById(int id, String message_text){
        if(message_text == null || message_text.trim().isEmpty() || message_text.length() >= 255){
            return null;
        }
        return messageDAO.updateMessageById(id, message_text);
    }

    
}
