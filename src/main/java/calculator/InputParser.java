package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
/**
 * 힌트: 입력 문자열 파싱을 담당하는 클래스.
 * 구분자 추출, 숫자 토큰 분리, 연산자 추출 등을 여기서 처리할 수 있다.
 * 반드시 이 클래스를 사용할 필요는 없다. 자유롭게 설계할 것.
 */
public class InputParser {
    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String CUSTOM_PREFIX = "//";
    private static final String NEW_LINE = "\n";
    private static final String OP_PREFIX = "op=";

    public ParsedInput parse(String input) {
        validateInput(input);

        if (input.isBlank()) {
            return new ParsedInput(OperationType.ADD, List.of(0.0));
        }

        if (input.startsWith(OP_PREFIX)) {
            return parseWithOperation(input);
        }

        if (input.startsWith(CUSTOM_PREFIX)) {
            return parseWithCustomDelimiter(input);
        }

        return new ParsedInput(OperationType.ADD, parseNumbers(input, DEFAULT_DELIMITER));
    }

    private ParsedInput parseWithOperation(String input) {
        String[] parts = input.split("\\|", 2);
        OperationType operation = OperationType.from(parts[0].substring(3));
        return new ParsedInput(operation, parseNumbers(parts[1], DEFAULT_DELIMITER));
    }

    private ParsedInput parseWithCustomDelimiter(String input) {
        String delimiter = extractDelimiter(input);
        String numbers = extractNumbers(input);
        return new ParsedInput(OperationType.ADD, parseNumbers(numbers, Pattern.quote(delimiter)));
    }

    private String extractDelimiter(String input) {
        return input.substring(2, input.indexOf(NEW_LINE));
    }

    private String extractNumbers(String input) {
        return input.substring(input.indexOf(NEW_LINE) + 1);
    }

    private List<Double> parseNumbers(String input, String delimiterRegex) {
        return Arrays.stream(input.split(delimiterRegex))
                .map(String::trim)
                .map(this::toNumber)
                .toList();
    }

    private Double toNumber(String token) {
        validateToken(token);

        try {
            double number = Double.parseDouble(token);
            validateNegative(number);
            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닌 값입니다.");
        }
    }

    private void validateInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("입력이 null입니다.");
        }
    }

    private void validateToken(String token) {
        if (token.isBlank()) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private void validateNegative(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다.");
        }
    }
}
