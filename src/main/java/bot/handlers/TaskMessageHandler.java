package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 1)
public class TaskMessageHandler implements MessageHandler{
    private final BackendConnector backendConnector;

    @Autowired
    public TaskMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/taskget", "");

        Task task = null;
        try {
            task = backendConnector.getTask(userId, Long.parseLong(query));
            message.setText(task.getContent());
            InlineKeyboards.setInlineTaskKeyboard(message, userId, task.getId());
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/taskget");
        return false;
    }


}
