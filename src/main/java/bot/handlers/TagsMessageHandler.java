package bot.handlers;

import bot.Keyboards;
import bot.state.StateManager;
import bot.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Order(value = 1)
public class TagsMessageHandler implements MessageHandler {
    private final StateManager stateManager;

    @Autowired
    public TagsMessageHandler(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public SendMessage getMessage(Update update) {
        stateManager.setState(UserState.CREATING_TAG, update.getMessage().getFrom().getId());
        SendMessage message;
        message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText(TextMessage.createNewTag);
        Keyboards.setButtonsTag(message);
        return message;
    }


    @Override
    public boolean canHandle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText().equalsIgnoreCase(TextMessage.createTag);
        }
        return false;
    }
}
