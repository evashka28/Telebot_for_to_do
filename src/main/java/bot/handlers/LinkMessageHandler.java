package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.Keyboards;
import bot.domen.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LinkMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public LinkMessageHandler() {
        this.backendConnector = new BackendConnector();
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String content = update.getMessage().getText() + "";
        message = new SendMessage();
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText("Сохраняю ссылку...\n" +
                        "\n" +
                        "Выберите подходящий тег для этой задачи");


        // create new task in ToDoist
        Map<String, Object> result = null;
        try {
            String favorite = "false";
            String todoistId = 0 + "";
            String description = "";
            String id = 0 +"";
            Map<String, Object> taskBody = Map.of(
                    "favorite", favorite,
                    "id", id,
                    "todoist_id", todoistId,
                    "description", description,
                    "content", content
            );

            result = postNewTask(new URI("http://localhost:8081/task"), taskBody, userId);
            System.out.println("resultTask = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(result != null) {
            long id = Long.parseLong(result.get("id").toString());
            InlineKeyboards.setInlineTagKeyboard(message, getTags(userId), id);
        }

        return message;
    }

    public Map<String,Object> postNewTask(URI uri, Map<String,Object> map, String userId)
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
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            String text = update.getMessage().getText();
            String isLink = "https://";
            return text.toLowerCase().contains(isLink.toLowerCase());
        }
        return false;

    }

    private List<Tag> getTags(String userId) {
        return backendConnector.getTags(userId);
    }
}
