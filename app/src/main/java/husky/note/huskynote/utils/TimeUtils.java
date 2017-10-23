package husky.note.huskynote.utils;

import android.support.annotation.IntDef;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TimeUtils
{
    public static long toTimeStamp(String serverTime)
    {
        return DateTime.parse(serverTime).getMillis();
    }

    public static String toServerTime(long timeInMillis)
    {
        return DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").print(timeInMillis);
    }

    public static String toTimeFormat(long timeInMillis)
    {
        return DateTimeFormat.forPattern("H:mm:ss").print(timeInMillis);
    }

    public static String toDateFormat(long timeInMillis)
    {
        return DateTimeFormat.forPattern("M-dd H:mm:ss").print(timeInMillis);
    }

    public static String toYearFormat(long timeInMillis)
    {
        return DateTimeFormat.forPattern("yyyy-M-dd H:mm:ss").print(timeInMillis);
    }

    public static Calendar getToday()
    {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    public static Calendar getYesterday()
    {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.HOUR, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        yesterday.set(Calendar.MILLISECOND, 0);
        yesterday.set(Calendar.DAY_OF_MONTH, -1);
        return yesterday;
    }

    public static Calendar getYearFormat()
    {
        Calendar year = Calendar.getInstance();
        year.set(Calendar.HOUR, 0);
        year.set(Calendar.MINUTE, 0);
        year.set(Calendar.SECOND, 0);
        year.set(Calendar.MILLISECOND, 0);
        year.set(Calendar.DAY_OF_MONTH, 0);
        return year;
    }
}
