package bot.state;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StateManager {

    Map<Long, UserState> storage = new HashMap<>();

    public UserState getState(Long id){
        return storage.getOrDefault(id, UserState.NORMAL);
    }

    public void setState(UserState state, Long id) {
        storage.put(id, state);
    }
}
