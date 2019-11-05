import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class CalendarUtilsTest {

    SimpleDateFormat sdf;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private CalendarUtils calendarUtils;



    @Before
    public void setUp() throws Exception {
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        System.setOut(new PrintStream(outContent));


        // Mocking date to 03.11.2019 00:00:00
        final Date date = Mockito.mock(Date.class);
        Mockito.when(date.getTime()).thenReturn(1572735600000L);

        final CalendarUtils.DateTime dt = Mockito.mock(CalendarUtils.DateTime.class);
        Mockito.when(dt.getDate()).thenReturn(date);

        calendarUtils = new CalendarUtils(dt);



    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
    }

    @Test
    public void getDaysForYear() {
        CalendarUtils.getDaysForYear(2019);
        Assert.assertEquals("Jan has 31 days\n" +
                "Feb has 28 days\n" +
                "Mar has 31 days\n" +
                "Apr has 30 days\n" +
                "May has 31 days\n" +
                "Jun has 30 days\n" +
                "Jul has 31 days\n" +
                "Aug has 31 days\n" +
                "Sep has 30 days\n" +
                "Oct has 31 days\n" +
                "Nov has 30 days\n" +
                "Dec has 31 days\n",outContent.toString());
    }

    @Test
    public void getMondaysDates(){
        CalendarUtils.getMondaysDates(11,2019);
        Assert.assertEquals("04.11.2019\n" +
                "11.11.2019\n" +
                "18.11.2019\n" +
                "25.11.2019\n",outContent.toString());
    }

    @Test
    public void getLocalDate(){
        calendarUtils.getLocalDate();
        Assert.assertEquals("Sun, 2019 November 03 00:00:00\n" +
                "So, 2019 November 03 00:00:00\n" +
                "Sun, 2019 November 03 00:00:00\n" +
                "星期日, 2019 十一月 03 00:00:00\n",outContent.toString());
    }
    @Test
    public void isFriday13() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Assert.assertTrue(CalendarUtils.isFriday13(sdf.parse("13.09.2019")));
            Assert.assertFalse(CalendarUtils.isFriday13(sdf.parse("13.10.2019")));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDateFromDate() {
        try {

            Assert.assertEquals(calendarUtils.getDateFromDate(sdf.parse("01.01.2019")), "0 years 10 months 2 days");
            Assert.assertEquals(calendarUtils.getDateFromDate(sdf.parse("02.12.2017")), "1 years 11 months 1 days");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDateFromDate_future_date_param_should_return_exception() {
        try {
            calendarUtils.getDateFromDate(sdf.parse("02.02.2020"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void gregorian_calendar_minimum_exception() {
        CalendarUtils.gregorianCalendarYearCheck(GregorianCalendar.getInstance().getActualMinimum(Calendar.YEAR)-1);

    }
    @Test(expected = IllegalArgumentException.class)
    public void gregorian_calendar_maximum_exception() {
        CalendarUtils.gregorianCalendarYearCheck(GregorianCalendar.getInstance().getActualMaximum(Calendar.YEAR)+1);

    }


}