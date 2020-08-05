package Musketeers.kz.command.impl;

import Musketeers.kz.services.LanguageService;
import Musketeers.kz.command.Command;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class id002_SelectionLanguage extends Command {

    @Override
    public boolean execute() throws TelegramApiException {
        deleteMessage(updateMessageId);
        chosenLanguage();
        toDeleteMessage(sendMessage(Const.WELCOME_TEXT_WHEN_START));
        return EXIT;
    }

    private void chosenLanguage() {
        if (isButton(Const.RU_LANGUAGE)) LanguageService.setLanguage(chatId, Language.ru);
        if (isButton(Const.KZ_LANGUAGE)) LanguageService.setLanguage(chatId, Language.kz);
        if (isButton(Const.EN_LANGUAGE)) LanguageService.setLanguage(chatId, Language.en);
    }
}
