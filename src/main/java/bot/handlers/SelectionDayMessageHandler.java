package bot.handlers;

import bot.Keyboards;
import bot.domen.Schedule;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Order(value = 1)
public class SelectionDayMessageHandler implements MessageHandler {
    enum Week {Воскресенье, Понедельник, Вторник, Среда, Четверг, Пятница, Суббота}

    String rightSchedule = "sh " + "\\d{2}" + ":" + "\\d{2}" + " ([1-7]{1})" + "(,[1-7]){0,6}";
    List<Integer> daysOfWeek = new ArrayList();
    String tagId = "";

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        if (update.hasCallbackQuery()) {
            tagId = update.getCallbackQuery().getData().replace("/tagget", "");
            System.out.println(tagId);
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            message.setText("Выбери расписания для тега в формате:\n" +
                    "sh 15:45 1,2,3 \n" +
                    "где 1 -вс, 2-пн и так далее");
        } else {
            String userId = update.getMessage().getFrom().getId() + "";
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String schedule = update.getMessage().getText();
            String[] words = schedule.split(" ");

            if (!schedule.matches(rightSchedule) || timeiswrong(words) || sameday(words))
                message.setText("Неправильное выражение, попробуй еще раз.");
            else {

                message.setText("Спасибо, расписание сохранено");
                String dateTime = words[1] + ":00";
                //message.setText("Спасибо, задача с тегом %s будет выведена в %s в %s", tagId, words[1], weeks[Integer.parseInt(words[2].split(",")[1])]  );


                Map<String, Object> result;
                try {

                    Map<String, Object> tagBody = Map.of(
                            "dateTime", dateTime,
                            "daysOfWeek", words[2],
                            "id", "hi"
                    );

                    result = postNewSch(new URI("http://localhost:8081/schedule/tag"), tagBody, tagId, userId);
                    System.out.println(tagBody);
                    System.out.println("resultTask = " + result);
                } catch (IOException | InterruptedException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }


        Keyboards.setButtons6(message);
        return message;
    }

    public boolean sameday(String[] words) {
        String str = words[2];
        for (int i = 1; i < 8; i++) str = str.replace(Integer.toString(i), "");
        return words[2].length() != str.length();
    }


    public boolean timeiswrong(String[] words) {
        return (words[1].split(":")[0].charAt(0) != '0' && Integer.parseInt(words[1].split(":")[0]) > 23) ||
                (words[1].split(":")[1].charAt(0) != '0' && Integer.parseInt(words[1].split(":")[1]) > 59);

    }


    public Map<String, Object> postNewSch(URI uri, Map<String, Object> map, String tagId, String userId)
            throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .header("tagId", tagId)
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
        //if(update.hasCallbackQuery())
        //     return update.getCallbackQuery().getData().contains("/tagget");
        if ((update.hasMessage() && update.getMessage().hasText())) {
            return update.getMessage().getText().contains("sh");
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/tagget");
        }
        return false;

    }
}
