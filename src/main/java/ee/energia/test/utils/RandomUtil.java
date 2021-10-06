package ee.energia.test.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RandomUtil {
    public static String generateSession() {
        String upperCaseLetters = RandomStringUtils.random(20, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(20, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(10);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers);
        List<Character> tokenChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(tokenChars);
        String password = tokenChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }
}
