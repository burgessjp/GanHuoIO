package ren.solid.ganhuoio;

import org.junit.Test;

import java.util.Calendar;

import ren.solid.library.utils.DateUtils;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {


    @Test
    public void textDateIsToDay() throws Exception {
        System.out.println(DateUtils.isToday(DateUtils.parseDate("2017-3-18")));
    }


}