package bot.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateManagerTest {
    StateManager manager = new StateManager();

    @Test
    void shouldGetNormalStateByDefault() {
        Long givenUserId = 1L;
        UserState actualState = manager.getState(givenUserId);
        UserState expectedState = UserState.NORMAL;

        assertEquals(expectedState, actualState);
    }

    @Test
    void shouldUpdateUserState() {
        Long givenUserId = 2L;
        manager.setState(UserState.CREATING_TAG, givenUserId);

        UserState actualState = manager.getState(givenUserId);
        assertEquals(UserState.CREATING_TAG, actualState);
    }

    @Test
    void shouldNotThrowNPE() {
        assertDoesNotThrow(() -> manager.getState(null));
    }
}