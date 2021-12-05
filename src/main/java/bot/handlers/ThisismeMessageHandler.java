package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ThisismeMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Если ты пользователь Todoist, то смело нажимай залогиниться✅\n" +
                        "\n" +
                        "А если у тебя еще нет профиля, то ссылка внизу\uD83D\uDE09\n" +
                        "Скорее регистрируйся и возвращайся ко мне\uD83E\uDD17 \n" +
                        "https://todoist.com/users/showregister");
        Keyboards.setButtons2(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Точно я!");
        }
        return false;
    }
}
