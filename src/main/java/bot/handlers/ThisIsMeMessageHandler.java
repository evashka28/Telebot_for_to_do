package bot.handlers;

import bot.keyboards.Keyboards;
import bot.TextMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class ThisIsMeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText(TextMessage.thisIsMe);
        Keyboards.setButtons2(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.isMe);
        }
        return false;
    }
}
