package Musketeers.kz.entity.standart;

import lombok.Data;

@Data
public class Reports {
    private int ID;
    private String reportText;
    private String photo;
    private String video;
    private String location;
    private int isDone;
    private int chatID;
    private String fullName;
    private String categoryName;
}