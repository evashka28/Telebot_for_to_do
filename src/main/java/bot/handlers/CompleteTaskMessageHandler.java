package bot.handlers;

import bot.BackendConnector;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

public class CompleteTaskMessageHandler implements MessageHandler{
    private final BackendConnector backendConnector;

    public CompleteTaskMessageHandler() {
        backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/taskcom", "");
        System.out.println("complete " + backendConnector.deleteTask(userId, Long.parseLong(query)));
        message.setText("Задание выполнено!");
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/taskcom");
        return false;
    }
}
