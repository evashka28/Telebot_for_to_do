package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Привет! Я Todo Bot\uD83E\uDD16\n" +
                        "Если ты часто добавляешь видосики, статьи, а может подкасты в закладки, но у тебя нет времени все это посмотреть, почитать, послушать прямо сейчас, то нам с тобой по пути.");
        Keyboards.setButtons1(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("/start");
    }
}
