package bot.handlers;

import bot.connectors.BackendConnector;
import bot.TextMessage;
import bot.entities.Tag;
import bot.exceptions.BackendConnectorException;
import bot.keyboards.InlineKeyboards;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
public class GiveTaskWithTagMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public GiveTaskWithTagMessageHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        String userId = update.getMessage().getFrom().getId() + "";
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        try {
            List<Tag> tags = backendConnector.getTags(userId);
            InlineKeyboards.setInlineGetOneTaskTagKeyboard(message, userId, tags);
            message.setText(TextMessage.chooseTaskTag);
        } catch (BackendConnectorException e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.giveTaskByTag);
        }
        return false;
    }

}
