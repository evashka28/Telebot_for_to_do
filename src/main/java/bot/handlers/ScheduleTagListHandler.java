package bot.handlers;

import bot.BackendConnector;
import bot.domen.Tag;
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
public class ScheduleTagListHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public ScheduleTagListHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message = new SendMessage();
        String userId = update.getMessage().getFrom().getId() + "";
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));

        try {
            message.setText("Выбери тег для которого хочешь посмотрееть расписание \n ⬇️ ️");
            setInlineTagKeyboard(message, userId);
        } catch (Exception e) {
            message.setText("Ошибка!");
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            return update.getMessage().getText().equals("Моё расписание");
//        }
        return false;
    }

    public void setInlineTagKeyboard(SendMessage message, String userId) throws Exception {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<Tag> tags = backendConnector.getTags(userId);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/taggetsh%d", tag.getId()));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
