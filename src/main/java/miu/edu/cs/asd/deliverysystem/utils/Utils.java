package miu.edu.cs.asd.deliverysystem.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class Utils {
    public static boolean isDelivered(){
        Date now = new Date();
        Date newTime = DateUtils.addMinutes(now, 5);
        Date currentTime = new Date();
        return currentTime.after(newTime);
    }
}
