//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private static final String BOT_TOKEN = "1249536099:AAGaJt81YbQsHODXXetMkWNn6aO3C1aJxP0";
    public static final String BOT_NAME = "antikorkz";

    public Bot() {
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String var3 = message.getText();
            byte var4 = -1;
            switch(var3.hashCode()) {
                case 1455327635:
                    if (var3.equals("/start")) {
                        var4 = 0;
                    }
                default:
                    switch(var4) {
                        case 0:
                            this.sendMsg(message, "Чем могу помочь?");
                    }
            }
        }

    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);

        try {
            this.setButtons(sendMessage);
            this.execute(sendMessage);
        } catch (TelegramApiException var5) {
            var5.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "antikorkz";
    }

    public String getBotToken() {
        return "1249536099:AAGaJt81YbQsHODXXetMkWNn6aO3C1aJxP0";
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRows = new ArrayList();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/start"));
        keyboardSecondRow.add(new KeyboardButton("/settings"));
        keyboardRows.add(keyboardFirstRow);
        keyboardRows.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}