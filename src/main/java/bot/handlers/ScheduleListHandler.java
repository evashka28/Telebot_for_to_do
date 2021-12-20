package bot.handlers;

import bot.BackendConnector;
import bot.domen.TagRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
@Slf4j
public class ScheduleListHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public ScheduleListHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        String userId = update.getMessage().getFrom().getId() + "";
        message.setChatId(String.valueOf(update.getMessage().getChatId()));


        try {
            message.setText(TextMessage.shed_list);
            setInlineTaskKeyboard(message, getSchedules(userId));
        } catch (Exception e) {
            message.setText(TextMessage.error);
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }


        return message;
    }

    @Override
    public boolean canHandle(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.my_shedule);
        }
        return false;
    }

    public void setInlineTaskKeyboard(SendMessage message, List<TagRequest> schedules) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (TagRequest schedule : schedules) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.format("%s Время: %s, Дни: %s", schedule.getTag().getName(), schedule.getDateTime(), schedule.getDaysOfWeek()));
            button.setCallbackData(String.format("/schdel%s", schedule.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }

    private List<TagRequest> getSchedules(String tagId) throws Exception {
        return backendConnector.getSchedules(tagId);
    }


}
