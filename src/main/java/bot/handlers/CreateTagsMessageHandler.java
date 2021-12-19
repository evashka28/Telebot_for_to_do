package bot.handlers;

import bot.Keyboards;
import bot.state.StateManager;
import bot.state.UserState;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CreateTagsMessageHandler implements MessageHandler {
    private final StateManager stateManager;

    @Autowired
    public CreateTagsMessageHandler(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String content = update.getMessage().getText() + "";
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Тег создан!");
        Keyboards.setButtonsTag(message);
        stateManager.setState(UserState.NORMAL, update.getMessage().getFrom().getId());


        // create new tag in ToDoist
        Map<String, Object> result;
        try {
            String id = 0 + "";
            Map<String, Object> tagBody = Map.of(
                    "id", id,
                    "name", content
            );

            result = postNewTag(new URI("http://localhost:8081/tag"), tagBody, userId);
            System.out.println("resultTag = " + result);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            message.setText("Ошибка");
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            UserState userState = stateManager.getState(update.getMessage().getFrom().getId());
            if (userState == UserState.CREATING_TAG) {
                return update.getMessage().getText().startsWith("#");
            }
        } else {
            if (update.hasCallbackQuery()) {
                UserState userState = stateManager.getState(update.getCallbackQuery().getFrom().getId());
                if (update.hasMessage() && update.getMessage().hasText() &&
                        userState == UserState.CREATING_TAG) {
                    return update.getMessage().getText().startsWith("#");
                }

            }
        }
        return false;
    }
}
