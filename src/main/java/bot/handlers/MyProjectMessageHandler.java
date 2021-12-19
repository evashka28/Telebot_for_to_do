package bot.handlers;

import bot.Keyboards;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
@Order(value = 1)
public class MyProjectMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";

        // get user projects
        List<Project> resultProjects = null;
        try {
            resultProjects = getProjects(userId);
            System.out.println("result = " + resultProjects);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
        String returnProjectsName = resultProjects.stream()
                .map(Project::getName)
                .collect(Collectors.joining(", "));

        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Список твоих проектов: " + returnProjectsName);
        Keyboards.setButtonsProject(message);
        return message;

    }

    public List<Project> getProjects(String userId) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = new URI("http://localhost:8081/projects");
        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("userId", userId)
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 410) {
            return null;
        } else {
            String body = response.body();
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        }
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            return text.equalsIgnoreCase("Мои проекты");
        }
        return false;
    }
}

