package tgbot.bot.statemachine.state.timezone;

import lombok.RequiredArgsConstructor;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;

import java.time.ZoneId;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class TimezoneListState implements State {

    private final long userId;
    private final String group;

    @Override
    public String getText() {
        return "Выберите часовой пояс";
    }

    @Override
    public List<Button> getButtons() {
        var groupButtons = ZoneId.getAvailableZoneIds().stream()
                .filter(id -> id.startsWith(group + "/") && id.length() < 20)
                .sorted()
                .limit(99)
                .map(id -> new Button(id, Callback.of(id)))
                .collect(toList());
        groupButtons.add(new Button("Назад", Callback.of("back")));
        return groupButtons;
    }

    @Override
    public State getNextState(Callback callback) {
        if (callback.getOperation().equals("back")) {
            return new TimezoneGroupListState(userId);
        }
        return new TimezoneState(userId, group, callback.getOperation());
    }
}
