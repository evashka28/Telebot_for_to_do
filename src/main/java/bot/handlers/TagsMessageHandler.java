package bot.handlers;

import bot.Keyboards;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TagsMessageHandler implements MessageHandler {
    public static boolean createTag = false;
    @Override
    public SendMessage getMessage(Update update) {
        createTag = true;
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("Для того чтобы создать новый тег, введи:" +
                        "#название_тега\n " +
                        "Например: #читать\n" );
        Keyboards.setButtonsTag(message);
        return message;
    }


    @Override
    public boolean canHandle(Update update) {
        if(update.getMessage() != null && update.getMessage().getText() != null) {
            return update.getMessage().getText().equals("Создать тег");
        }
        return false;
    }
}
