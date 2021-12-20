package bot.handlers;

import bot.Keyboards;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Order(value = 3)
public class CannotUnderstandHandler implements MessageHandler {

    @Override
    public SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText(TextMessage.cannotUnderstand);
        Keyboards.setButtons(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage();
    }
}
