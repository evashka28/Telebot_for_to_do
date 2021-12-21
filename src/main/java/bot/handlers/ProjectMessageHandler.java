package bot.handlers;

import bot.keyboards.Keyboards;
import bot.TextMessage;
import org.springframework.core.annotation.Order;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

//@Component
@Order(value = 1)
public class ProjectMessageHandler implements MessageHandler {
    @Override
    public SendMessage getMessage(Update update) {
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText(TextMessage.projectsInfo);
        Keyboards.setButtonsProject(message);
        return message;
    }

    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            return text.equalsIgnoreCase(TextMessage.projects);
        }
        return false;
    }
}

