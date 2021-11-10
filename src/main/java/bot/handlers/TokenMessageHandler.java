package bot.handlers;

import bot.Keyboards;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TokenMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Ура\uD83C\uDF89\n" +
                        "Теперь ты можешь пользоваться ботом.\n" +
                        "Просто отправляй мне ссылки, настраивай свой режим чтения, а я буду подкидывать тебе все это в Todoist \uD83E\uDD13\n" +
                        "Все очень просто, ты мне ссылку, а я тебе сэкономленное время \uD83D\uDE0A\n" +
                        "Прямо сейчас можешь отправлять мне ссылки, я уже жду)\n" +
                        "\n" +
                        "Внизу расположены кнопки для навигации⬇️");
        Keyboards.setButtons(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.getMessage().getText().equals("токен");
    }
}
