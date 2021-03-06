package bot.handlers;

import bot.connectors.BackendConnector;
import bot.keyboards.Keyboards;
import bot.TextMessage;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Order(value = 1)
@Slf4j
public class SelectionDayMessageHandler implements MessageHandler {
    enum Week {Воскресенье, Понедельник, Вторник, Среда, Четверг, Пятница, Суббота}
    private final BackendConnector backendConnector;
    String rightSchedule = "sh " + "\\d{2}" + ":" + "\\d{2}" + " ([1-7]{1})" + "(,[1-7]){0,6}";
    List<Integer> daysOfWeek = new ArrayList();
    String tagId = "";

    @Autowired
    public SelectionDayMessageHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }

    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        if (update.hasCallbackQuery()) {
            tagId = update.getCallbackQuery().getData().replace("/tagget", "");
            log.info(tagId);
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            message.setText(TextMessage.createShed);
        } else {
            String userId = update.getMessage().getFrom().getId() + "";
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            String schedule = update.getMessage().getText();
            String[] words = schedule.split(" ");

            if (!schedule.matches(rightSchedule) || timeIsWrong(words) || sameDay(words[2]))
                message.setText(TextMessage.wrongTerm);
            else {

                message.setText(TextMessage.goodTerm);
                String dateTime = words[1] + ":00";
                //message.setText("Спасибо, задача с тегом %s будет выведена в %s в %s", tagId, words[1], weeks[Integer.parseInt(words[2].split(",")[1])]  );


                Map<String, Object> result;
                try {

                    Map<String, Object> tagBody = Map.of(
                            "dateTime", dateTime,
                            "daysOfWeek", words[2],
                            "id", "hi"
                    );

                    result = backendConnector.postNewSchedule(new URI("http://localhost:8081/schedule/tag"), tagBody, tagId, userId);
                    log.info(String.valueOf(tagBody));
                    log.info("resultTask = " + result);
                } catch (IOException | InterruptedException | URISyntaxException e) {
                    log.info(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
                }
            }
        }


        Keyboards.setButtons6(message);
        return message;
    }

    public boolean sameDay(String words) {
        String str = words.replace(",", "");
        for (int i = 1; i < 8; i++) {
            str = str.replaceFirst(Integer.toString(i), "");
        }
        return str.length()!=0;
    }


    public boolean timeIsWrong(String[] words) {
        return (words[1].split(":")[0].charAt(0) != '0' && Integer.parseInt(words[1].split(":")[0]) > 23) ||
                (words[1].split(":")[1].charAt(0) != '0' && Integer.parseInt(words[1].split(":")[1]) > 59);

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
