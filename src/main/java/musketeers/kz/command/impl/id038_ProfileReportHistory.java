package Musketeers.kz.command.impl;

import Musketeers.kz.command.Command;
import Musketeers.kz.dao.impl.FileDao;
import Musketeers.kz.entity.custom.File;
import Musketeers.kz.entity.enums.WaitingType;
import Musketeers.kz.entity.standart.Button;
import Musketeers.kz.entity.standart.Reports;
import Musketeers.kz.utils.ButtonsLeaf;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class id038_ProfileReportHistory extends Command {

    private List<Reports> listReport;
    private ButtonsLeaf buttonLeaf;
    private FileDao fileDao;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType){
            case START:
                listReport = factory.getReportsDao().getAll(chatId);
                if (listReport == null || listReport.size() == 0) {
                    sendMessage(Const.CATEGORY_LIST_EMPTY);
                    return EXIT;
                }
                List<String> list = new ArrayList<>();
                listReport.forEach(reports -> list.add(getText(Const.REPORT_NUMBER)  + " " + reports.getID()+"\n"));
                buttonLeaf = new ButtonsLeaf(list);
                toDeleteKeyboard(sendMessageWithKeyboard(getText(Const.CHOOSE_REPORT_MESSAGE), buttonLeaf.getListButton()));
                waitingType = WaitingType.SHOW_REPORT_BUTTONS;
                return COMEBACK;
            case SHOW_REPORT_BUTTONS:
                if (hasCallbackQuery()){
                    Reports reports = listReport.get(Integer.parseInt(updateMessageText));
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getText(Const.REPORT_NUMBER)+" "+reports.getID());
                    stringBuilder.append("\n" + getText(Const.REPORT_TEXT) +" "+ reports.getReportText());
                    stringBuilder.append("\n" + getText(Const.REPORT_CATEGORY) +" "+ reports.getCategoryName());
                    stringBuilder.append("\n" + getText(Const.REPORT_DATE) +" "+ reports.getDate());
                    if (reports.getPhoto() != null){
                        bot.execute(new SendPhoto().setChatId(chatId).setPhoto(reports.getPhoto()));
                    }
                    if (reports.getVideo() != null){
                        bot.execute(new SendVideo().setChatId(chatId).setVideo(reports.getVideo()));
                    }
                    if (reports.getLocation() != null){
                        String[] split = reports.getLocation().split(";");
                        bot.execute(new SendLocation().setChatId(chatId).setLatitude(Float.parseFloat(split[0])).setLongitude(Float.parseFloat(split[1])));
                    }
                    sendMessage(stringBuilder.toString());
                }
                return COMEBACK;

        }
        return EXIT;
    }
}
