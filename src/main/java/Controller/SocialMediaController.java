package Controller;

import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAccountMessagesHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::createMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context){
        Account account = context.bodyAsClass(Account.class);
        Account createAccount = accountService.createAccount(account);
        if(createAccount != null){
            context.status(200).json(createAccount);
        }
        else {
            context.status(400);
        }
    }

    public void loginHandler(Context context){
        Account body = context.bodyAsClass(Account.class);
        Account account = accountService.login(body.username, body.password);
        System.out.println(account);
        if(account != null){
            context.status(200).json(account);
        } else {
            context.status(401);
        }

    }

    public void createMessageHandler(Context context){
        Message message = context.bodyAsClass(Message.class);
        Message createMessage = messageService.createMessage(message);
        if(createMessage != null){
            context.status(200).json(createMessage);
        } else {
            context.status(400);
        }
    }

    public void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.status(200).json(messages);
    }

    public void getAccountMessagesHandler(Context context){
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        context.status(200).json(messages);
    }

    public void getMessageByIdHandler(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if(message != null){
            context.status(200).json(message);
        } else {
            context.status(200).json("");
        }

    }

    public void deleteMessageHandler(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(message_id);
        if(message != null){
            context.status(200).json(message);
        } else {
            context.status(200).json("");
        }

    }

    public void updateMessageHandler(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        String newMessageText = context.bodyAsClass(Message.class).getMessage_text();
        Message updatedMessage = messageService.updateMessageById(message_id, newMessageText);
        if(updatedMessage != null) {
            context.status(200).json(updatedMessage);
        } else {
            context.status(400).json("");
        }
    }



  

}