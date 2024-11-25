import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private final Function<String, Integer> parseNumber = str -> {
        try {
            String trimmed = str.trim();
            if (trimmed.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + str);
        }
    };

    private final Consumer<List<Integer>> checkNegatives = nums -> {
        List<Integer> negatives = nums.stream()
                .filter(n -> n < 0)
                .toList();
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }
    };

    private final Predicate<Integer> isValidNumber = num -> num <= 1000;

    public int add(String numbers) {
        if (StringUtils.isEmpty(numbers)) {
            return 0;
        }

        String numbersToCalculate = numbers;
        List<String> delimiters = new ArrayList<>();
        delimiters.add(","); // default delimiter

        if (numbers.startsWith("//")) {
            Matcher m = Pattern.compile("//(?:\\[(.*?)])+//(.*)").matcher(numbers);
            if (m.find()) {
                numbersToCalculate = m.group(2);

                // Estrai tutti i delimitatori
                Matcher delimiterMatcher = Pattern.compile("\\[(.*?)]").matcher(numbers);
                while (delimiterMatcher.find()) {
                    delimiters.add(Pattern.quote(delimiterMatcher.group(1)));
                }
            }
        }

        // Costruisci il pattern regex combinando tutti i delimitatori
        String finalDelimiter = String.join("|", delimiters);

        // Split usando tutti i delimitatori
        List<Integer> nums = Arrays.stream(numbersToCalculate.split(finalDelimiter))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .map(parseNumber)
                .collect(Collectors.toList());

        checkNegatives.accept(nums);

        return nums.stream()
                .filter(isValidNumber)
                .reduce(0, Integer::sum);
    }

}
