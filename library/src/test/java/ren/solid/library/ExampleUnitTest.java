package ren.solid.library;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        String baseUrl = "http://gank.io/api/";
        String url = "http://gank.io/api/day/history/2016/07/26";


        assertEquals("day/history", url.substring(baseUrl.length()));
    }
}