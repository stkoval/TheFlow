package helpers;

/**
 *
 * @author Stas
 */
public class TimeParser {
    public static int parse(String sTime) {
        int hours = 0;
        int minutes = 0;
        if (sTime == null || sTime.equals("")) {
            return 0;
        }
        String timeArr[] = sTime.split(":");
        if (!timeArr[0].equals("00")) {
            hours = Integer.parseInt(timeArr[0]);
        }
        if (!timeArr[1].equals("00")) {
            minutes = Integer.parseInt(timeArr[1]);
        }
        return hours * 60 + minutes;
    }
}
