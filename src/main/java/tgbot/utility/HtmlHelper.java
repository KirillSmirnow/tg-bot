package tgbot.utility;

public class HtmlHelper {

    public static String escapeText(String text) {
        return text
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("&", "&amp;")
                ;
    }

    public static String limitTextLength(String text) {
        return text.substring(0, Math.min(text.length(), 4000));
    }
}
