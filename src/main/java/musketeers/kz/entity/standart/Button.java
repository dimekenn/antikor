package Musketeers.kz.entity.standart;

import Musketeers.kz.entity.enums.Language;
import lombok.Data;

@Data
public class Button {

    private int      id;
    private String   name;
    private int      commandId;
    private String   url;
    private Language language;
    private boolean  requestContact;
    private int      messageId;

}
