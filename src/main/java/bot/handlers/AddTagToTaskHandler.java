package bot.handlers;

import bot.connectors.BackendConnector;
import bot.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
@Slf4j
public class AddTagToTaskHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public AddTagToTaskHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));

        String query = update.getCallbackQuery().getData().replace("/addTtT", "");
        log.info(query);
        String[] tags = query.split("\\."); //первый id тега, второй - задачи
        try {
            String result = backendConnector.addTagToTask(update.getCallbackQuery().getFrom().getId().toString(),
                    Long.parseLong(tags[1]), Long.parseLong(tags[0]));
            message.setText(TextMessage.addTag);
        } catch (Exception e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/addTtT");
        }
        return false;
    }
}
