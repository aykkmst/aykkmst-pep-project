package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */    
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){        
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegister);
        app.post("/login", this::postLogin);
        app.post("/messages", this::postMessage);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagebyIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessagebyIdHandler);
        app.patch("/messages/{message_id}", this::updateMessagebyIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesbyUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */


    private void postRegister(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){            
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void postLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account confAccount = accountService.accountLogin(account);
        if(confAccount!=null){            
            ctx.json(mapper.writeValueAsString(confAccount));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }
    
    private void postMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){            
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    public void getMessagebyIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt((ctx.pathParam("message_id")));
        Message message = messageService.getMessagebyId(message_id);
        if(message!=null){            
            ctx.json(mapper.writeValueAsString(message));
            ctx.status(200);
        }
    }

    public void deleteMessagebyIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt((ctx.pathParam("message_id")));
        Message message = messageService.deleteMessagebyId(message_id);
        if(message!=null){            
            ctx.json(mapper.writeValueAsString(message));
            ctx.status(200);
        }
    }
 
    public void updateMessagebyIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt((ctx.pathParam("message_id")));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updateMessage = messageService.updateMessagebyId(message, message_id);
        if(updateMessage!=null){            
            ctx.json(mapper.writeValueAsString(updateMessage));
            ctx.status(200);
        }
        else
            ctx.status(400);
    }

    public void getAllMessagesbyUserHandler(Context ctx){
        int account_id = Integer.parseInt((ctx.pathParam("account_id")));
        List<Message> messages = messageService.getAllMessagesbyUser(account_id);
        ctx.json(messages);
    }
}