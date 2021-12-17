package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Tag;
import bot.domen.Task;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
public class GiveOneTaskMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public GiveOneTaskMessageHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        String userId = update.getCallbackQuery().getFrom().getId() +"";
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        String tagId = update.getCallbackQuery().getData().replace("/getOneTaskByTag", "");
        Task task = null;
        try {
            task = getTaskByTag(userId,tagId);
            String taskContent = task.getContent() + "";
            message.setText("Отличное время, чтобы изучить что-то новое:\n" + taskContent);
        } catch (Exception e) {
            message.setText("По такому тегу нет задач!");
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
            if(update.hasCallbackQuery()) {
                return update.getCallbackQuery().getData().contains("/getOneTaskByTag");
            }
            return false;
    }

    private Task getTaskByTag(String userId, String tagId) throws Exception {
        return backendConnector.getTaskByTag(tagId, userId);
    }
}
