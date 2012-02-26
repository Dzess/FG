package pl.alife.fg.testing.utils;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import pl.alife.fg.utils.MapHelper;


public class MapHelperTest {

    private String input;
    private Map<String, String> result;

    @Test
    public void empty_map_parsing_return_empty_map() {
        input = "{}";

        performTest();

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void single_element_map_parsing() {
        input = "{Sample key=value 1}";

        performTest();

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("value 1", result.get("Sample key"));
    }

    @Test
    public void multiple_element_map_parsing() {
        input = "{Start Value=-10.0, Step=2.0, Stop Value=10.0}";

        performTest();

        Assert.assertEquals(3, result.size());
        Assert.assertEquals("-10.0", result.get("Start Value"));
        Assert.assertEquals("2.0", result.get("Step"));
        Assert.assertEquals("10.0", result.get("Stop Value"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void wrong_format_of_string_throws_exception_1() {
        input = "{Start Value=-10.0, Step=2.0, Stop Value=10.0";

        performTest();
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrong_format_of_string_throws_exception_2() {
        performTest();
    }

    private void performTest() {
        result = MapHelper.parse(input);
    }

}
