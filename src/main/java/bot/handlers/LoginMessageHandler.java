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
                        "Откуда взять токен?\n" +
                        "1️⃣ Необходимо нажать на иконку своего профиля в Todoist\n" +
                        "2️⃣ Нажать на вкладку  Интеграции \n" +
                        "3️⃣ Скопировать Токен API (можно нажать на Скопировать в буфер обмена)\n" +
                        "4️⃣ Вернутся в наш чатик и отправить токен мне (вставить)"
                );
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("Залогиниться✅");
    }

}
