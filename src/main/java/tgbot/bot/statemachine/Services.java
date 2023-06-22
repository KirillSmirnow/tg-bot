package tgbot.bot.statemachine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tgbot.core.UserService;

@Component
@RequiredArgsConstructor
@Getter
public class Services {
    private final UserService userService;
}
