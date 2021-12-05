package bot.handlers;

import bot.Keyboards;
import bot.domen.Task;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TasksMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        String userId = update.getMessage().getFrom().getId() + "";
        SendMessage message;


        List<Task> resultTask = null;
        try {
            resultTask = getTasks(userId);
            System.out.println("resultgetTask = " + resultTask);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String returnTasks = resultTask.stream()
                .map(Task::getContent)
                .collect(Collectors.joining(", "));

        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Список твоих задач на данный момент: " + returnTasks);
        Keyboards.setButtons(message);

        return message;
    }


    public List<Task> getTasks(String userId) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = new URI("http://localhost:8081/tasks");
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            return objectMapper.readValue(body, new TypeReference<>() {});
        }

    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Задачи");
        }
        return false;
    }
}
