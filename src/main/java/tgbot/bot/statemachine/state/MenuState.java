package tgbot.bot.statemachine.state;

import lombok.RequiredArgsConstructor;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;
import tgbot.bot.statemachine.state.code.FileState;
import tgbot.bot.statemachine.state.timezone.TimezoneGroupListState;

import java.util.List;

@RequiredArgsConstructor
public class MenuState implements State {

    private final long userId;

    @Override
    public String getText() {
        return "Меню";
    }

    @Override
    public List<Button> getButtons() {
        return List.of(
                new Button("Профиль", Callback.of("profile")),
                new Button("Часовые пояса", Callback.of("timezones")),
                new Button("Код", Callback.of("code"))
        );
    }

    @Override
    public State getNextState(Callback callback) {
        return switch (callback.getOperation()) {
            case "profile" -> new ProfileState(userId);
            case "timezones" -> new TimezoneGroupListState(userId);
            case "code" -> new FileState(userId, "tgbot");
            default -> this;
        };
    }
}
