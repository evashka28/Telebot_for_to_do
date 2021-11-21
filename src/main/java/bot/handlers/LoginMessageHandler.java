package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LoginMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        TokenMessageHandler.canHandle = true;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Для того чтобы залогиниться тебе необходимо отправить мне свой токен точно как в Todoist\n" +
                        "Введи его и просто отправь мне⬇️\n" +
                        "\n" +
                        "Скоро я смогу рассказать как это сделать, но не сейчас\uD83D\uDE43");
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("Залогиниться✅");
    }

}
