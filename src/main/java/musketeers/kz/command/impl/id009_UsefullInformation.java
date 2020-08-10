package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id009_UsefullInformation extends Command {
    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                deleteMessage(updateMessageId);
                sendMessageWithKeyboard(getText(Const.USEFULLINFORMATION_MESSAGE), Const.USEFULLINFORMATION_KEYBOARD);
        }
        return EXIT;
    }
}

