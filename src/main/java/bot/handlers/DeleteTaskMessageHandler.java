package bot.handlers;

import bot.BackendConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 1)
public class DeleteTaskMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public DeleteTaskMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/taskdel", "");
        try {
            System.out.println("delete " + backendConnector.deleteTask(userId, Long.parseLong(query)));
            message.setText("Задание удалено!");
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/taskdel");
        return false;
    }
}
