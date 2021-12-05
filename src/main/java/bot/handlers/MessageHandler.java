package bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.URISyntaxException;

public interface MessageHandler {
    SendMessage getMessage(Update update) throws URISyntaxException, IOException, InterruptedException;

    boolean canHandle(Update update);
}
