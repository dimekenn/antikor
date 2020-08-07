package Musketeers.kz.entity.standart;

import lombok.Data;

@Data
public class Reports {
    private String reportText;
    private String photo;
    private String video;
    private String location;
    private int isDone;
    private Long chatID;
    private String fullName;
    private String categoryName;
}
