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

    public static void setInlineTagKeyboard(SendMessage message, List<Tag> tags, long taskId){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();


        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();

        for(Tag tag : tags){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(tag.getName());
            button.setCallbackData(String.format("/addTtT%d.%s", tag.getId(), taskId));
            keyboardRow.add(button);
        }
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);

        keyboardMarkup.setKeyboard(keyboard);

        message.setReplyMarkup(keyboardMarkup);
    }
}
