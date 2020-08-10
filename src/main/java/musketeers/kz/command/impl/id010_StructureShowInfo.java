package Musketeers.kz.command.impl;

import lombok.extern.slf4j.Slf4j;
import Musketeers.kz.command.Command;
import Musketeers.kz.dao.impl.AboutUsDao;
import Musketeers.kz.dao.impl.ButtonDao;
import Musketeers.kz.entity.enums.Language;
import Musketeers.kz.entity.enums.WaitingType;
import Musketeers.kz.entity.standart.AboutUs;
import Musketeers.kz.entity.standart.Categories;
import Musketeers.kz.entity.standart.Reports;
import Musketeers.kz.utils.ButtonsLeaf;
import Musketeers.kz.utils.Const;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class id010_StructureShowInfo extends Command {

    private List<AboutUs> aboutUses;
    private List<AboutUs> aboutUses2;
     private ButtonDao buttonDao = new ButtonDao();
    // private ButtonsLeaf buttonsLeaf;
    // private int deleteMessageId;
    private AboutUsDao aboutUsDao = new AboutUsDao();
    private ButtonsLeaf buttonsLeaf;
    // private Reports reports = new Reports();
    // private ReportsDao reportsDao = factory.getReportsDao();
    // private int secondDeleteMessageId;
    // private Language currentLanguage;
//    private RegistrationEvent   registrationEvent;

    @Override
    public boolean execute() throws TelegramApiException {
        switch (waitingType) {
            case START:
                List<String> list = new ArrayList<>();
                if (isButton(50)){
                    aboutUses = factory.getAboutUsDao().getAll(buttonDao.getButtonText(50));
                    AboutUs aboutUs = aboutUses.get(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Const.FULL_NAME+" "+aboutUs.getName());
                    stringBuilder.append(Const.PHONE+" "+aboutUs.getPhone() + " ");
                    stringBuilder.append(Const.INFORM+" "+aboutUs.getInform() + " ");
                    stringBuilder.append(aboutUs.getHost() + " ");

                 sendMessage(stringBuilder.toString());

                }
                else if(isButton(64)){
                    aboutUses = factory.getAboutUsDao().getAll(buttonDao.getButtonText(64));
                    aboutUses.forEach((e)->list.add(e.getName()));
                    buttonsLeaf = new ButtonsLeaf(list);
                    toDeleteKeyboard(sendMessageWithKeyboard("sad", buttonsLeaf.getListButton()));
                    waitingType = WaitingType.STRUCTURE_RECEPTION;
                }

                return COMEBACK;

           case STRUCTURE_RECEPTION:
               if (hasCallbackQuery()) {
                   AboutUs aboutUs2 = aboutUses.get(Integer.parseInt(updateMessageText));
                   StringBuilder stringBuilder = new StringBuilder();
                   stringBuilder.append(Const.FULL_NAME+" "+aboutUs2.getName());
                   stringBuilder.append(Const.PHONE+" "+aboutUs2.getPhone() );
                   stringBuilder.append(Const.INFORM+" "+aboutUs2.getInform() );
                   stringBuilder.append(aboutUs2.getHost() );

                   sendMessage(stringBuilder.toString());
               }

               return COMEBACK;
        }
//
        return EXIT;
    }
}



