package bot;

import bot.domen.Tag;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class InlineKeyboards {

    public static void setInlineTagKeyboard(SendMessage message, List<Tag> tags, long taskId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();


        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        int counter = 0;

        for (Tag tag : tags) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/addTtT%d.%s", tag.getId(), taskId));
            keyboardRow.add(button);
            counter++;
            if (counter == 3) {
                keyboard.add(keyboardRow);
                keyboardRow = new ArrayList<>();
                counter = 0;
            }
        }

        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }

    public static void setInlineTaskKeyboard(SendMessage message, String userId, long taskId) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton deleteButton = new InlineKeyboardButton();
        deleteButton.setCallbackData(String.format("/taskdel%d", taskId));
        deleteButton.setText("Удалить❌");
        row.add(deleteButton);

        InlineKeyboardButton completeButton = new InlineKeyboardButton();
        completeButton.setCallbackData(String.format("/taskcom%d", taskId));
        completeButton.setText("Выполнить✅");
        row.add(completeButton);

        InlineKeyboardButton tagSelectionButton = new InlineKeyboardButton();
        tagSelectionButton.setCallbackData(String.format("/tasktagsel%d", taskId));
        tagSelectionButton.setText("Выбрать тег");
        row.add(tagSelectionButton);

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
