package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;

import Model.Message;

import java.util.List;

public class MessageService{
    public AccountDAO accountDAO;
    public MessageDAO messageDAO;    

    public MessageService(){
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        if(("").equals(message.getMessage_text()) || message.getMessage_text().length() > 255)
            return null;
        else if(accountDAO.getAccountbyId(message.getPosted_by()) == null)
            return null;
        else{
            Message newMessage = messageDAO.insertMessage(message);
            return newMessage;
        }
    }

    public List<Message> getAllMessages() {
        List<Message> messageList = messageDAO.getAllMessages();
        return messageList;
    }

    public Message getMessagebyId(int message_id) {
        Message message = messageDAO.getMessagebyId(message_id);
        return message;
    }

    public Message deleteMessagebyId(int message_id){
        Message message = messageDAO.deleteMessagebyId(message_id);
        return message;
    }

    public Message updateMessagebyId(Message updateMessage, int message_id){
        if(messageDAO.getMessagebyId(message_id) == null)
            return null;
        else if(("").equals(updateMessage.getMessage_text()) || updateMessage.getMessage_text().length() > 255)
            return null;         
        else{
            Message message = messageDAO.getMessagebyId(message_id);
            if(updateMessage.getPosted_by() == 0) {
                updateMessage.setPosted_by(message.getPosted_by());
            }
            
            if(updateMessage.getTime_posted_epoch() == 0){
                updateMessage.setTime_posted_epoch(message.getTime_posted_epoch());
            }
            Message newMessage = messageDAO.updateMessagebyId(updateMessage, message_id);            
            return newMessage;
        }
    }

    public List<Message> getAllMessagesbyUser(int account_id){
        List<Message> messageList = messageDAO.getAllMessagesbyUser(account_id);
        return messageList;
    }
}