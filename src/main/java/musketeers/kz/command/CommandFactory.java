package Musketeers.kz.command;

import Musketeers.kz.exceptions.NotRealizedMethodException;
import Musketeers.kz.command.impl.*;

public class CommandFactory {

    public  static Command getCommand(long id) {
        Command result = getCommandWithoutReflection((int) id);
        if (result == null) throw new NotRealizedMethodException("Not realized for type: " + id);
        return result;
    }

    private static Command getCommandWithoutReflection(int id) {
        switch (id) {
            case 1:
                return new id001_ShowInfo();
            case 2:
                return new id002_SelectionLanguage();
            case 3:
                return new id003_Survey();
            case 4:
                return new id004_ShowAdminInfo();
            case 5:
                return new id005_Registration();
            case 6:
                return new id006_ShowCategories();
            case 7:
                return new id007_ProfileReportHistory();
            case 8:
                return new id008_ShowNews();
            case 9:
                return new id009_UsefullInformation();
            case 10:
                return new id010_StructureShowInfo();
            case 11:
                return new id011_Reception();
            case 12:
                return new id012_Registration();



       }
       return null;
   }
}
