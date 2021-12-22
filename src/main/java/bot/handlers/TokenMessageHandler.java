package bot.handlers;

import bot.connectors.BackendConnector;
import bot.keyboards.Keyboards;
import bot.TextMessage;
import bot.entities.Project;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;

@Component
@Order(value = 2)
@Slf4j
public class TokenMessageHandler implements MessageHandler {
    public final BackendConnector backendConnector;

    @Autowired
    public TokenMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        String userId = update.getMessage().getFrom().getId() + "";
        String userName = update.getMessage().getFrom().getUserName() + "";
        String synkToken = "0";
        String token = update.getMessage().getText() + "";
        String zone = "Europe/Moscow";

        log.info("create user");
        Map<String, String> result;
        try {
            result = backendConnector.createUser(userId, userName, synkToken, token, zone, this);
            log.info("result = " + result);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        // get user projects
        log.info("get user's projects");
        List<Project> resultProjects = null;
        try {
            resultProjects = backendConnector.getProjects(userId);
            log.info("result = " + resultProjects);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        // create new project ToDoBot
        // надо проверить есть ли проект fromToDoBot
        // если нет, то создать, а если есть, то складывать туда
        try {
            Map<String, String> resultProject = backendConnector.createProject(userId, this);
            log.info("resultproject = " + resultProject);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }

        // if tasks are not empty -> show OK message
        // if tasks are empty or got error -> show token invalid message

        if (resultProjects == null) {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(TextMessage.invalidToken);
            Keyboards.setButtons2(message);
            return message;
        } else {
            message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText(TextMessage.goodAuthorization);
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

    //    public void deleteUser(String userId) throws IOException, InterruptedException, URISyntaxException {
//        URI uri = new URI("http://localhost:8081/user/" + userId);
//        HttpRequest request = HttpRequest.newBuilder(uri)
//                .DELETE()
//                .build();
//
//        HttpClient.newHttpClient()
//                .send(request, HttpResponse.BodyHandlers.ofString());
//    }

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
