package bot.handlers;

import bot.BackendConnector;
import bot.InlineKeyboards;
import bot.domen.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Order(value = 1)
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
        message.setText("Сохраняю ссылку...\n" +
                "\n" +
                "Выберите подходящий тег для этой задачи");


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

            result = postNewTask(new URI("http://localhost:8081/task"), taskBody, userId);
            System.out.println("resultTask = " + result);

            if (result != null) {
                long taskId = Long.parseLong(result.get("id").toString());
                InlineKeyboards.setInlineTagKeyboard(message, getTags(userId), taskId);
            }
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }

        return message;
    }

    public Map<String, Object> postNewTask(URI uri, Map<String, Object> map, String userId)
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
            String text = update.getMessage().getText();
            String isSafeLink = "https://";
            String isLink = "http://";
            return (text.toLowerCase().contains(isLink.toLowerCase()) ||
                    text.toLowerCase().contains(isSafeLink.toLowerCase()));
        }
        return false;

    }

    private List<Tag> getTags(String userId) throws Exception {
        return backendConnector.getTags(userId);
    }
}
