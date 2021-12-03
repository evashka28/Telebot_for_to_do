package bot.handlers;

import bot.Keyboards;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class LinkMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String content = update.getMessage().getText() + "";
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Сохраняю ссылку...\n" +
                        "\n" +
                        "Выбери подходящий тег для этой задачи");
        Keyboards.setButtons(message);

        // create new task in ToDoist
        try {
            String favorite = "false";
            String todoistId = 0 + "";
            String description = "";
            String id = 0 +"";
            Map<String, String> taskBody = Map.of(
                    "favorite", favorite,
                    "id", id,
                    "todoist_id", todoistId,
                    "description", description,
                    "content", content
            );

            Map<String, String> result = postNewTask(new URI("http://localhost:8081/task"), taskBody, userId);
            System.out.println("resultTask = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return message;
    }
    public Map<String,String> postNewTask(URI uri, Map<String,String> map, String userId)
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

        return (Map<String, String>) objectMapper.readValue(resultBody, Map.class);
    }

    @Override
    public boolean canHandle(Update update) {
        String text = update.getMessage().getText();
        String isLink = "https://";
        return text.toLowerCase().contains(isLink.toLowerCase());

    }
}
