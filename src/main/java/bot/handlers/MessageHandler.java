package bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    SendMessage getMessage(Update update);

    boolean canHandle(Update update);
}
