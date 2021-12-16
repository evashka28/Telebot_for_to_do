package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
@Order(value = 1)
public class TagSelectionHandler implements MessageHandler{
    private final BackendConnector backendConnector;

    @Autowired
    public TagSelectionHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
        String userId = update.getCallbackQuery().getFrom().getId().toString();


        String query = update.getCallbackQuery().getData().replace("/tasktagsel", "");
        long taskId = Long.parseLong(query);
        try {
            message.setText("Выберите тег:");
            InlineKeyboards.setInlineTagKeyboard(message, getTags(userId), taskId);
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasCallbackQuery())
            return update.getCallbackQuery().getData().contains("/tasktagsel");
        return false;
    }

    private List<Tag> getTags(String userId) throws Exception {
        return backendConnector.getTags(userId);
    }


}
