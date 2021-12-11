package bot.handlers;

import bot.Keyboards;
import bot.domen.Tag;
import bot.domen.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ReturnTagMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        String userId = update.getMessage().getFrom().getId() + "";
        SendMessage message;


        List<Tag> resultTags = null;
        try {
            resultTags = getTags(userId);
            System.out.println("resultgetTask = " + resultTags);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String returnTags = resultTags.stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));

        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Список твоих тегов: " + returnTags);
        Keyboards.setButtonsTag(message);

        return message;
    }


    public List<Tag> getTags(String userId) throws IOException, InterruptedException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        URI uri = new URI("http://localhost:8081/tags");
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
            return update.getMessage().getText().equals("Мои теги");
        }
        return false;
    }
}
