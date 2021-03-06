package bot.handlers;

import bot.TextMessage;
import bot.connectors.BackendConnector;
import bot.state.StateManager;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
@Order(value = 1)
@Slf4j
public class CreateTagsMessageHandler implements MessageHandler {
    private final StateManager stateManager;

    private final BackendConnector backendConnector;

    @Autowired
    public CreateTagsMessageHandler(StateManager stateManager, BackendConnector backendConnector) {
        this.stateManager = stateManager;
        this.backendConnector = backendConnector;
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String content = update.getMessage().getText() + "";
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText(TextMessage.createdTag);
        //Keyboards.setButtonsTag(message);
        //stateManager.setState(UserState.NORMAL, update.getMessage().getFrom().getId());


        // create new tag in ToDoist
        Map<String, Object> result;
        try {
            result = backendConnector.createTag(userId, content, this);
            log.info("resultTag = " + result);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            message.setText(TextMessage.error);
        }
        return message;
    }


    public Map<String, Object> postNewTag(URI uri, Map<String, Object> map, String userId)
            throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .header("userId", userId)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String resultBody = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();

        return (Map<String, Object>) objectMapper.readValue(resultBody, Map.class);
    }

    @Override
    public boolean canHandle(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            UserState userState = stateManager.getState(update.getMessage().getFrom().getId());
//            if (userState == UserState.CREATING_TAG) {
//                return update.getMessage().getText().startsWith("#");
//            }
//        } else {
//            if (update.hasCallbackQuery()) {
//                UserState userState = stateManager.getState(update.getCallbackQuery().getFrom().getId());
//                if (update.hasMessage() && update.getMessage().hasText() &&
//                        userState == UserState.CREATING_TAG) {
//                    return update.getMessage().getText().startsWith("#");
//                }
//
//            }
//        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().startsWith("#");
        }
        return false;
    }
}
