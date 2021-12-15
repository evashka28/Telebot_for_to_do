package bot.handlers;

import bot.BackendConnector;
import bot.Keyboards;
import bot.domen.Tag;
import bot.domen.TagRequest;
import bot.domen.Task;
import bot.domen.Project;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(value = 1)
public class ScheduleMessageHandler implements MessageHandler {
    private final BackendConnector backendConnector;

    public ScheduleMessageHandler() {
        this.backendConnector = new BackendConnector();
    }


    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setText("Список твоих расписаний (для удаления нажмите на него):");

        if(update.hasCallbackQuery()) {
            message.setChatId(String.valueOf(update.getCallbackQuery().getMessage().getChatId()));
            String tagId = update.getCallbackQuery().getData().replace("/taggetsh","") ;
            setInlineTaskKeyboard(message, tagId, getSchs(tagId));
        }


        return message;
    }

    @Override
    public boolean canHandle(Update update) {

        if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData().contains("/taggetsh");
        }
        return false;
    }

    public void setInlineTaskKeyboard(SendMessage message, String tagId, List<TagRequest> sches){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for(TagRequest sch: sches){
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(sch.getDateTime() + sch.getDaysOfWeek());
            button.setCallbackData(String.format("/shgdel%s/shtagid%s", sch.getId(), tagId));
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
    }

    private List<TagRequest> getSchs(String tagId){
        return backendConnector.getSchs(tagId);
    }


}
