package tgbot.bot.statemachine.state.code;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import tgbot.bot.statemachine.Button;
import tgbot.bot.statemachine.Callback;
import tgbot.bot.statemachine.State;
import tgbot.bot.statemachine.state.MenuState;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;
import static tgbot.utility.HtmlHelper.escapeText;
import static tgbot.utility.HtmlHelper.limitTextLength;

@RequiredArgsConstructor
public class FileState implements State {

    private static final String PATH_PREFIX = "./src/main/java";

    private final long userId;
    private final String path;

    private Path getFullPath() {
        return Path.of(PATH_PREFIX, path);
    }

    @Override
    @SneakyThrows
    public String getText() {
        var fullPath = getFullPath();
        if (Files.isRegularFile(fullPath)) {
            var content = Files.readString(fullPath);
            return "<code>" + limitTextLength(escapeText(content)) + "</code>";
        }
        return path;
    }

    @Override
    public List<Button> getButtons() {
        var fileButtons = new ArrayList<>(getFileButtons());
        fileButtons.add(new Button("Назад", Callback.of("back")));
        fileButtons.add(new Button("Меню", Callback.of("menu")));
        return fileButtons;
    }

    @SneakyThrows
    private List<Button> getFileButtons() {
        var fullPath = getFullPath();
        if (Files.isRegularFile(fullPath)) {
            return emptyList();
        }
        return Files.list(getFullPath())
                .sorted(Comparator.<Path, Boolean>comparing(Files::isDirectory).reversed())
                .map(path -> path.getFileName().toString())
                .map(filename -> new Button(filename, Callback.of(filename)))
                .toList();
    }

    @Override
    public State getNextState(Callback callback) {
        return switch (callback.getOperation()) {
            case "menu" -> new MenuState(userId);
            case "back" -> new FileState(userId, Path.of(path).getParent().toString());
            default -> new FileState(userId, Path.of(path, callback.getOperation()).toString());
        };
    }
}
