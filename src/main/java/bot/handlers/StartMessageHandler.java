package bot.handlers;

import bot.Keyboards;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class StartMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Привет! Я Todo Bot\uD83E\uDD16\n" +
                        "Если ты часто добавляешь видосики, статьи, а может подкасты в закладки, но у тебя нет времени все это посмотреть, почитать, послушать прямо сейчас, то нам с тобой по пути.");
        Keyboards.setButtons1(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasText())
            return update.getMessage().getText().equalsIgnoreCase("/start");
        return false;
    }
}
