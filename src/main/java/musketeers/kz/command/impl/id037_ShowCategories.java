package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.entity.enums.WaitingType;
import Musketeers.kz.entity.standart.Categories;
import Musketeers.kz.utils.ButtonsLeaf;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class id037_ShowCategories extends Command {
    private List<Categories> categories;
    private Categories category;
    private ButtonsLeaf buttonsLeaf;
    private int deleteMessageId;
    private int secondDeleteMessageId;
//    private RegistrationEvent   registrationEvent;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                deleteMessage(updateMessageId);
                categories = factory.getCategoriesDao().getAll();
                if (categories == null || categories.size() == 0) {
                    deleteMessageId = sendMessage(Const.CATEGORY_LIST_EMPTY);
                    return EXIT;
                }
                List<String> list = new ArrayList<>();
                categories.forEach((e) -> list.add(e.getName()));
                buttonsLeaf = new ButtonsLeaf(list);
                toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.CATEGORY_CHOOSE), buttonsLeaf.getListButton()));
                waitingType = WaitingType.CATEGORY_SELECTION;
                return COMEBACK;
            case CATEGORY_SELECTION:
                deleteMessage(updateMessageId);
                if (hasCallbackQuery()) {

                }
                return COMEBACK;
        }
        return EXIT;
    }

//    private int getCategories() throws TelegramApiException{
//        if ()
//    }
}