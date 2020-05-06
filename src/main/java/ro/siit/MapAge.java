package ro.siit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MapAge {
    public static long calculateAgeInDays(Date date){
        Date todayDate = new Date(); //data zilei curente
        long totalDaysLived = todayDate.getTime() - date.getTime();

        return TimeUnit.DAYS.convert(totalDaysLived,TimeUnit.MILLISECONDS);
    }
}
