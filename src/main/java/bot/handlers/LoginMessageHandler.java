package bot.handlers;

import bot.Keyboards;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class LoginMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(String.valueOf(update.getMessage().getChatId())));
        message.setText("Для того чтобы залогиниться тебе необходимо отправить мне свой токен точно как в Todoist\n" +
                "Введи его и просто отправь мне⬇️\n" +
                "\n" +
                "Откуда взять токен?\n" +
                "1️⃣ Необходимо нажать на иконку своего профиля в Todoist\n" +
                "2️⃣ Нажать на вкладку  Интеграции \n" +
                "3️⃣ Скопировать Токен API (можно нажать на Скопировать в буфер обмена)\n" +
                "4️⃣ Вернутся в наш чатик и отправить токен мне (вставить)"
        );
        Keyboards.setButtons2(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase("Залогиниться✅");
        }
        return false;
    }

}
