package bot.handlers;

import bot.Keyboards;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

@Component
@Order(value = 2)
@Slf4j
public class TokenMessageHandler implements MessageHandler {

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String userName = update.getMessage().getFrom().getUserName() + "";
        String synkToken = "0";
        String token = update.getMessage().getText() + "";
        String zone = "Europe/Moscow";


        Map<String, String> body = Map.of(
                "id", userId,
                "name", userName,
                "token", token,
                "sync_token", synkToken,
                "zone", zone
        );


        log.info("create user");
        try {
            Map<String, String> result = postJSON(new URI("http://localhost:8081/user"), body);
            log.info("result = " + result);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        // get user projects
        log.info("get user's projects");
        List<Project> resultProjects = null;
        try {
            resultProjects = getProjects(userId);
            log.info("result = " + resultProjects);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        // create new project ToDoBot
        // надо проверить есть ли проект fromToDoBot
        // если нет, то создать, а если есть, то складывать туда
        try {
            String favorite = "false";
            String name = "fromToDoBot";
            String todoId = 0 + "";
            Map<String, String> projectBody = Map.of(
                    "favorite", favorite,
                    "id", userId,
                    "name", name,
                    "todoistId", todoId
            );

            Map<String, String> result = postNewProject(new URI("http://localhost:8081/project"), projectBody, userId);
            log.info("resultproject = " + result);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        // if tasks are not empty -> show OK message
        // if tasks are empty or got error -> show token invalid message

        if (resultProjects == null) {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(TextMessage.invalid_token);
            Keyboards.setButtons2(message);
            return message;
        } else {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(TextMessage.good_authorization);
            Keyboards.setButtons(message);
            return message;
        }
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().length() == 40;
        }
        return false;
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

    public Map<String, String> postJSON(URI uri, Map<String, String> map)
            throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        String resultBody = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();

        return (Map<String, String>) objectMapper.readValue(resultBody, Map.class);
    }

//    public void deleteUser(String userId) throws IOException, InterruptedException, URISyntaxException {
//        URI uri = new URI("http://localhost:8081/user/" + userId);
//        HttpRequest request = HttpRequest.newBuilder(uri)
//                .DELETE()
//                .build();
//
//        HttpClient.newHttpClient()
//                .send(request, HttpResponse.BodyHandlers.ofString());
//    }

    public Map<String, String> postNewProject(URI uri, Map<String, String> map, String userId)
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

    class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {
        /**
         * Parses the given JSON string into a Map.
         */
        Map<String, String> readValue(String content) {
            try {
                return this.readValue(content, new TypeReference<>() {
                });
            } catch (IOException ioe) {
                throw new CompletionException(ioe);
            }
        }
    }
}
