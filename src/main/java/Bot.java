import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

public class Bot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "1249536099:AAGaJt81YbQsHODXXetMkWNn6aO3C1aJxP0";
    public static final String BOT_NAME = "antikorkzbot";


    @Override
    public void onUpdateReceived(Update update) {
        Message message = new Message();
        if (message != null && message.hasText()){
            switch (message.getText()){
                case "/start":
                    sendMsg(message, "Чем могу помочь?");
                    break;
            }
        }
    }

    private void sendMsg(Message message, String s) {
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
