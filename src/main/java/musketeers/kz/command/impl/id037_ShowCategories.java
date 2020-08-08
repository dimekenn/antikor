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
import java.util.Date;
import java.util.List;

public class id037_ShowCategories extends Command {
    private List<Categories> categories;
    private Categories category;
    private ButtonsLeaf buttonsLeaf;
    private int deleteMessageId;
    private CategoriesDao categoriesDao;
    private Reports reports = new Reports();
    Float latitude;
    Float longitude;
    private ReportsDao reportsDao = factory.getReportsDao();
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
                    reports.setChatID(chatId);
                    reports.setFullName(userDao.getUserByChatId(chatId).getFullName());
                    reports.setDate(new Date());
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
                waitingType = WaitingType.REPORT_PHOTO;
                return COMEBACK;
            case REPORT_PHOTO:
                deleteMessage(updateMessageId);
                if (hasPhoto()) {
                    reports.setPhoto(updateMessagePhoto);
                    sendMessage(Const.SEND_PHOTO_SUCCESS);
                }
                if (hasVideo()){
                    reports.setVideo(updateMessage.getVideo().getFileId());
                    sendMessage(Const.SEND_VIDEO_SUCCESS);
                    sendMessageWithKeyboard(getText(Const.SEND_LOCATION), null);
                    waitingType = WaitingType.SEND_LOCATION;
//                    sendMessageWithKeyboard(getText(Const.SEND_LOCATION), Const.LOCATION_KEYBOARD);
//                    waitingType = WaitingType.SEND_LOCATION;
//                } else {
//                    sendMessage(Const.WRONG_TYPE);
                }
                if (isButton(1057)){
                    sendMessage(Const.SEND_LOCATION);
                    waitingType = WaitingType.SEND_LOCATION;
                }
                return COMEBACK;
            case SEND_LOCATION:
                deleteMessage(updateMessageId);
                if (isButton(1057)){
                    sendMessage(Const.ONLY_WITH_LOCATION);
                }
                System.out.println("PZDC PROSTO");
                latitude = update.getMessage().getLocation().getLatitude();
                longitude = update.getMessage().getLocation().getLongitude();
                String location = latitude + ";" + longitude;
                reports.setLocation(location);
                sendMessageWithKeyboard(getText(Const.SEND_LOCATION_SUCCESS), Const.LOCATION_KEYBOARD);
                waitingType = WaitingType.SEND_REPORT;
                return COMEBACK;
            case SEND_REPORT:
                if (isButton(1058)){
                    reportsDao.insert(reports);
                    sendMessage(Const.REPORT_FINISH);
                    return EXIT;
                }
        }

        return EXIT;
    }

}