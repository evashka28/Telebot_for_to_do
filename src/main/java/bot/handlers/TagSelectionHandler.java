package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
@Slf4j
public class TagSelectionHandler implements MessageHandler {
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
            message.setText(TextMessage.choose_task_tag);
            InlineKeyboards.setInlineTagKeyboard(message, getTags(userId), taskId);
        } catch (Exception e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/tasktagsel");
        }
        return false;
    }

    private List<Tag> getTags(String userId) throws Exception {
        return backendConnector.getTags(userId);
    }


}
