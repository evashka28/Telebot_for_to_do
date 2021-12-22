package bot.handlers;

import bot.connectors.BackendConnector;
import bot.TextMessage;
import bot.entities.TagRequest;
import bot.exceptions.BackendConnectorException;
import bot.keyboards.InlineKeyboards;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@Order(value = 1)
@Slf4j
public class ScheduleListHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public ScheduleListHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        String userId = update.getMessage().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        try {
            message.setText(TextMessage.shedList);
            InlineKeyboards.setInlineTaskKeyboard(message, getSchedules(userId));
        } catch (BackendConnectorException e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.myShedule);
        }
        return false;
    }

    private List<TagRequest> getSchedules(String tagId) throws BackendConnectorException {
        return backendConnector.getSchedules(tagId);
    }


}
