import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//
public class CalendarUtils {

    private final DateTime dateTime;

    interface DateTime{
        Date getDate();
    }

    class DateTimeImpl implements DateTime {
        @Override
        public Date getDate() {
            return new Date();
        }
    }

    public CalendarUtils(final DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static void main(String[] args) {

    }



    /**
     * Prints days for every month of given year.
     *
     * @param year
     */
    public static void getDaysForYear(int year) {

        gregorianCalendarYearCheck(year);

        Calendar calendar = new GregorianCalendar(year, Calendar.JANUARY, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.US);

        for (int i = calendar.get(Calendar.MONTH); i <= calendar.getActualMaximum(Calendar.MONTH); i++) {
            calendar.set(year, i, 1);
            System.out.println(sdf.format(calendar.getTime()) + " has " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + " days");
        }


    }


    /**
     * Prints mondays for given month and year
     *
     * @param year
     * @param month months in following format: 1 equals January, 2 equals February etc.
     */
    public static void getMondaysDates(int month, int year) {

        gregorianCalendarYearCheck(year);

        if (month > 12 || month < 1) {
            throw new IllegalArgumentException("Param \"month\" should be in [1;12]");
        }

        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        System.out.println(sdf.format(calendar.getTime()));

        int i = calendar.get(Calendar.DATE);

        while (i + 7 <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            i += 7;
            calendar.set(year, month - 1, i);
            System.out.println(sdf.format(calendar.getTime()));
        }

    }

    /**
     * Checks if date is Friday 13th.
     *
     * @param date
     * @return true if date is Friday13th, false if not.
     */

    public static boolean isFriday13(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE) == 13 && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }

    /**
     * Returns String which contains information how many years, months and days passed from given date.
     *
     * @param date
     * @return
     */

    public String getDateFromDate(Date date) {

        if (date.getTime() > new Date().getTime()) {
            throw new IllegalArgumentException("Param \"date\" should contain date in the past");
        }

        Calendar todaysDate = Calendar.getInstance();
        Calendar usersDate = Calendar.getInstance();
        usersDate.setTime(date);

        todaysDate.setTime(dateTime.getDate());


        int day;
        int month;
        int year;

        int addition = 0;

        if (usersDate.get(Calendar.DAY_OF_MONTH) > todaysDate.get(Calendar.DAY_OF_MONTH)) {
            addition = usersDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (addition != 0) {
            day = (todaysDate.get(Calendar.DAY_OF_MONTH) + addition) - usersDate.get(Calendar.DAY_OF_MONTH);
            addition = 1;
        } else {
            day = todaysDate.get(Calendar.DAY_OF_MONTH) - usersDate.get(Calendar.DAY_OF_MONTH);
        }

        if ((usersDate.get(Calendar.MONTH) + addition) > todaysDate.get(Calendar.MONTH)) {
            month = (todaysDate.get(Calendar.MONTH) + 12) - (usersDate.get(Calendar.MONTH) + addition);
            addition = 1;
        } else {
            month = (todaysDate.get(Calendar.MONTH)) - (usersDate.get(Calendar.MONTH) + addition);
            addition = 0;
        }

        year = todaysDate.get(Calendar.YEAR) - (usersDate.get(Calendar.YEAR) + addition);

        return year + " years " + month + " months " + day + " days";

    }

    /**
     * Prints current date in format "EEE, yyyy MMMMM dd HH:mm:ss" for four locales: {Canada, Germany, Pakistan, Taiwan}
     */

    public  void getLocalDate() {

        SimpleDateFormat sdfCanada = new SimpleDateFormat("EEE, yyyy MMMMM dd HH:mm:ss", Locale.CANADA);
        SimpleDateFormat sdfGermany = new SimpleDateFormat("EEE, yyyy MMMMM dd HH:mm:ss", Locale.GERMANY);
        SimpleDateFormat sdfPakistan = new SimpleDateFormat("EEE, yyyy MMMMM dd HH:mm:ss", Locale.forLanguageTag("ur_PK"));
        SimpleDateFormat sdfTaiwan = new SimpleDateFormat("EEE, yyyy MMMMM dd HH:mm:ss", Locale.TAIWAN);
        Date date = new Date();
        date.setTime(dateTime.getDate().getTime());
        System.out.println(sdfCanada.format(date));
        System.out.println(sdfGermany.format(date));
        System.out.println(sdfPakistan.format(date));
        System.out.println(sdfTaiwan.format(date));

    }

    public static int getWeekday(int day, int month, int year) {
        if (month < 3) {
            --year;
            month += 10;
        } else
            month -= 2;
        return ((day + 31 * month / 12 + year + year / 4 - year / 100 + year / 400) % 7);
    }

    public static void gregorianCalendarYearCheck(int year) {
        int maxYear = GregorianCalendar.getInstance().getActualMaximum(Calendar.YEAR);
        int minYear = GregorianCalendar.getInstance().getActualMinimum(Calendar.YEAR);

        if (year > maxYear || year < minYear) {
            throw new IllegalArgumentException("Param \"year\" should be in [" + minYear + ";" + maxYear + "]");
        }
    }





    public enum Month {

        JANUARY(0),
        FEBRUARY(1),
        MARCH(2),
        APRIL(3),
        MAY(4),
        JUNE(5),
        JULY(6),
        AUGUST(7),
        SEPTEMBER(8),
        OCTOBER(9),
        NOVEMBER(10),
        DECEMBER(11);

        private int monthNumber;


        Month(int monthNumber) {
            this.monthNumber = monthNumber;
        }


    }

}
