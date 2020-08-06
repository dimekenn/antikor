package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.dao.impl.CategoriesDao;
import Musketeers.kz.dao.impl.ReportsDao;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.entity.enums.WaitingType;
import Musketeers.kz.entity.standart.Categories;
import Musketeers.kz.entity.standart.Reports;
import Musketeers.kz.entity.standart.User;
import Musketeers.kz.utils.ButtonsLeaf;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.stream.events.StartDocument;
import java.util.ArrayList;
import java.util.List;

public class id037_ShowCategories extends Command {
    private List<Categories> categories;
    private Categories category;
    private ButtonsLeaf buttonsLeaf;
    private int deleteMessageId;
    private CategoriesDao categoriesDao;
    private Reports reports = new Reports();
    private ReportsDao reportsDao;
//    private final User currentUser = userDao.getUserByChatId(chatId);
    private int secondDeleteMessageId;
    private Language currentLanguage;
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
                    //int categoryId = categories.get(Integer.parseInt(updateMessageText)).getId(); // Сохранение ID
                    String categorys = categories.get(Integer.parseInt(updateMessageText)).getName(); //Сохранение обьекта
                    reports.setCategoryName(categorys);
//                    reports.setChatID(currentUser.getChatId());
//                    reports.setFullName(currentUser.getFullName());
                    sendMessage(getText(Const.WRITE_REPORT));
                    waitingType = WaitingType.REPORT_TEXT;
                }
                return COMEBACK;
            case REPORT_TEXT:
                deleteMessage(updateMessageId);
                if (update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().length() <= 50) {
                    reports.setReportText(update.getMessage().getText());
                }
                sendMessageWithKeyboard(getText(Const.SEND_PHOTO_VIDEO), Const.PHOTO_VIDEO_KEYBOARD);
                waitingType = WaitingType.SEND_FILE;
                return COMEBACK;
            case SEND_FILE:
                deleteMessage(updateMessageId);
                if (isButton(1056)) {
                    sendMessage(Const.PUT_PHOTO_VIDEO);
                } else if (isButton(1057)){
                    waitingType = WaitingType.SEND_LOCATION;
                }
                waitingType = WaitingType.REPORT_PHOTO;
                return COMEBACK;
            case REPORT_PHOTO:
                deleteMessage(updateMessageId);
                if (hasPhoto()) {
                    reports.setPhoto(updateMessagePhoto);
                } else if (hasVideo()){
                    reports.setVideo(updateMessage.getVideo().getFileId());
                } else {
                    sendMessage(Const.WRONG_TYPE);
                }
                sendMessage(Const.SEND_LOCATION);
                waitingType = WaitingType.SEND_LOCATION;
                return COMEBACK;
            case SEND_LOCATION:
                deleteMessage(updateMessageId);
                Float latitude = update.getMessage().getLocation().getLatitude();
                Float longitude = update.getMessage().getLocation().getLongitude();
                String location = latitude + ";" + longitude;
                reports.setLocation(location);
                return COMEBACK;
        }
        reportsDao.insert(reports);
        return EXIT;
    }

//    private int getCategories() throws TelegramApiException{
//        if ()
//    }
}