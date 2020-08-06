package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id38_ReportText extends Command {
    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                deleteMessage(updateMessageId);

        }
        return false;
    }
}
