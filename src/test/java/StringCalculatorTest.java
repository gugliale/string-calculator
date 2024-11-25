import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    private final StringCalculator calculator = new StringCalculator();

    @Test
    @DisplayName("Test when sum is zero")
    void test_ForEmptyString() {

        assertEquals(0, calculator.add(""));
    }

    @Test
    @DisplayName("Test when sum only a number")
    void test_ForSingleValue() {

        assertEquals(5, calculator.add("5"));
    }

    @Test
    @DisplayName("Test when sum two numbers")
    void test_SumTwoNumbers() {

        assertEquals(7, calculator.add("2,5"));
    }

    @Test
    @DisplayName("Test when numbers are at lest three")
    void test_ForHandleMultipleNumbers() {

        assertEquals(10, calculator.add("1,2,3,4"));
    }

    @Test
    @DisplayName("Test when numbers are greater then 1000")
    void test_IgnoreNumbersGreaterThan1000() {

        assertEquals(5, calculator.add("5,1001"));
    }

    @Test
    @DisplayName("Test for different delimiters")
    void test_HandleCustomDelimiter() {

        assertEquals(6, calculator.add("//[***]//1***2***3"));
    }

    @Test
    @DisplayName("Test when there are multiple delimiters")
    void test_HandleMultipleDelimiter() {
        assertEquals(6, calculator.add("//[*][%]//1*2%3"));
    }

    @Test
    @DisplayName("Test when negative are present")
    void test_ThrowForNegativeNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.add("1,-2,3,-4"));
        assertTrue(exception.getMessage().contains("[-2, -4]"));
    }

    @Test
    @DisplayName("Test when give invalid input")
    void test_ThrowForInvalidNumber() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.add("1,abc,3"));

    }

} 