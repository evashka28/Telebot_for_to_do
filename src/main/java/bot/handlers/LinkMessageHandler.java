package bot.handlers;

import bot.connectors.BackendConnector;
import bot.exceptions.BackendConnectorException;
import bot.keyboards.InlineKeyboards;
import bot.TextMessage;
import bot.entities.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Order(value = 1)
@Slf4j
public class LinkMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public LinkMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String content = URLEncoder.encode(update.getMessage().getText() + "", StandardCharsets.UTF_8);
        message = new SendMessage();
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText(TextMessage.saveLink);


        // create new task in ToDoist
        Map<String, Object> result;
        try {
            String favorite = "false";
            String todoistId = 0 + "";
            String description = "";
            String id = 0 + "";
            Map<String, Object> taskBody = Map.of(
                    "favorite", favorite,
                    "id", id,
                    "todoist_id", todoistId,
                    "description", description,
                    "content", content
            );

            result = backendConnector.postNewTask(new URI("http://localhost:8081/task"), taskBody, userId);
            log.info("resultTask = " + result);

            if (result != null) {
                long taskId = Long.parseLong(result.get("id").toString());
                InlineKeyboards.setInlineAddTagToTaskKeyboard(message, getTags(userId), taskId);
            }
        } catch (BackendConnectorException | URISyntaxException | IOException | InterruptedException e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String isSafeLink = "https://";
            String isLink = "http://";
            return (text.toLowerCase().contains(isLink.toLowerCase()) ||
                    text.toLowerCase().contains(isSafeLink.toLowerCase()));
        }
        return false;

    }

    private List<Tag> getTags(String userId) throws BackendConnectorException {
        return backendConnector.getTags(userId);
    }
}
