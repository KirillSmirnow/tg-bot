package tgbot.bot.statemachine.state.timezone;

import lombok.RequiredArgsConstructor;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;
import tgbot.bot.statemachine.state.MenuState;

import java.time.ZoneId;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class TimezoneGroupListState implements State {

    private final long userId;

    @Override
    public String getText() {
        return "Выберите группу часовых поясов";
    }

    @Override
    public List<Button> getButtons() {
        var groupButtons = ZoneId.getAvailableZoneIds().stream()
                .filter(id -> id.contains("/"))
                .map(id -> id.split("/")[0])
                .distinct()
                .sorted()
                .map(group -> new Button(group, Callback.of(group)))
                .collect(toList());
        groupButtons.add(new Button("Меню", Callback.of("menu")));
        return groupButtons;
    }

    @Override
    public State getNextState(Callback callback) {
        if (callback.getOperation().equals("menu")) {
            return new MenuState(userId);
        }
        return new TimezoneListState(userId, callback.getOperation());
    }
}
