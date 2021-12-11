package bot.handlers;

import bot.Keyboards;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AboutBotMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText("Хей! Механика моей работы простая\uD83D\uDE09\n " +
                        "Ты отправляешь мне ссылки и выбираешь режим чтения\n " +
                        "Я - завожу задачи в Todoist, контролирую их выполнение\n " +
                        "\n"+
                        "В ходе разработки появятся новые фичи и ты обязательно об этом узнаешь ☺️");
        Keyboards.setButtons(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Как работает бот?");
        }
        return false;
    }
}
