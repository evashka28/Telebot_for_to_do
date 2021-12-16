package bot.handlers;

import bot.Keyboards;
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
        message.setText("Если ты пользователь Todoist, то смело нажимай залогиниться✅\n" +
                        "\n" +
                        "А если у тебя еще нет профиля, то ссылка внизу\uD83D\uDE09\n" +
                        "Скорее регистрируйся и возвращайся ко мне\uD83E\uDD17 \n" +
                        "https://todoist.com/users/showregister");
        Keyboards.setButtons2(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase("Точно я!");
        }
        return false;
    }
}
