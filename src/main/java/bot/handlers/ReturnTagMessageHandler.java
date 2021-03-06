package bot.handlers;

import bot.connectors.BackendConnector;
import bot.exceptions.BackendConnectorException;
import bot.keyboards.Keyboards;
import bot.TextMessage;
import bot.entities.Tag;
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
import java.util.stream.Collectors;

@Component
@Order(value = 1)
@Slf4j
public class ReturnTagMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public ReturnTagMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }

    @Override
    public SendMessage getMessage(Update update) {
        String userId = update.getMessage().getFrom().getId() + "";
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        List<Tag> resultTags = null;
        try {
            resultTags = backendConnector.getTags(userId);
            log.info("resultgetTask = " + resultTags);
            String returnTags = resultTags.stream()
                    .map(Tag::getName)
                    .collect(Collectors.joining(", "));
            message.setText(TextMessage.tagsList + returnTags);
            Keyboards.setButtonsTag(message);
        } catch (BackendConnectorException e) {
            log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
            message.setText(TextMessage.error);
        }


        return message;
    }


//    public List<Tag> getTags(String userId) throws IOException, InterruptedException, URISyntaxException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        URI uri = new URI("http://localhost:8081/tags");
//        HttpRequest request = HttpRequest.newBuilder(uri)
//                .header("userId", userId)
//                .build();
//
//        HttpResponse<String> response = HttpClient.newHttpClient()
//                .send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 410) {
//            return null;
//        } else {
//            String body = response.body();
//            return objectMapper.readValue(body, new TypeReference<>() {
//            });
//        }
//
//    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.myTags);
        }
        return false;
    }
}
