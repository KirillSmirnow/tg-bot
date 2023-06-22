package tgbot.bot.statemachine.state.timezone;

import lombok.RequiredArgsConstructor;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TimezoneState implements State {

    private final long userId;
    private final String group;
    private final String timezone;

    @Override
    public String getText() {
        var zoneId = ZoneId.of(timezone);
        var offset = zoneId.getRules().getOffset(Instant.now());
        var dateTime = ZonedDateTime.now().withZoneSameInstant(zoneId);
        return """
                Часовой пояс: %s
                Текущее смещение: %s
                Текущее время: %s
                """.formatted(zoneId, offset, dateTime);
    }

    @Override
    public List<Button> getButtons() {
        return List.of(
                new Button("Назад", Callback.of("back"))
        );
    }

    @Override
    public State getNextState(Callback callback) {
        return new TimezoneListState(userId, group);
    }
}
