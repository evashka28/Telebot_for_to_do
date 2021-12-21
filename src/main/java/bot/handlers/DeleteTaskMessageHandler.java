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

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 1)
@Slf4j
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
            log.info("delete " + backendConnector.deleteTask(userId, Long.parseLong(query)));
            message.setText(TextMessage.deletedTask);
        } catch (Exception e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
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
