package bot.handlers;


import bot.BackendConnector;
import bot.domen.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 1)
@Slf4j
public class TasksByTagHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    @Autowired
    public TasksByTagHandler(BackendConnector backendConnector) {
        this.backendConnector = backendConnector;
    }


    @Override
    public SendMessage getMessage(Update update) {
        String userId = update.getMessage().getFrom().getId() + "";
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));

        try {
            message.setText("Выберите тег: ");
            setInlineTagKeyboard(message, userId);
        } catch (Exception e) {
            message.setText("Ошибка!");
            log.error(e.getMessage() + " " + ExceptionUtils.getStackTrace(e));
        }


        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase("Задачи по тегу");
        }
        return false;
    }

    public void setInlineTagKeyboard(SendMessage message, String userId) throws Exception {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<Tag> tags = backendConnector.getTags(userId);
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();

        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/getTaskByTag%d", tag.getId()));
            keyboardRow.add(button);
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
