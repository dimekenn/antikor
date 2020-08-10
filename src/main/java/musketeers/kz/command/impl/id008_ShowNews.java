package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id008_ShowNews extends Command {
    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                deleteMessage(updateMessageId);
                sendMessageWithKeyboard(getText(Const.NEWS_MESSAGE), Const.NEWS_KEYBOARD);
        }
        return EXIT;
    }


}
