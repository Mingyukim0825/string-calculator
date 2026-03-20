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
    private static final String DEFAULT_DELIMITER = ",|:"; //기본 구분자
    private static final String CUSTOM_PREFIX = "//"; //커스텀 구분자 시작
    private static final String NEW_LINE = "\n";//커스텀 구분자에서 구분 기준
    private static final String OP_PREFIX = "op="; //연산자 지정

    public ParsedInput parse(String input) {

        if (input == null || input.isBlank()) { //공백일때 0을 반환한다
            return new ParsedInput(OperationType.ADD, List.of(0.0));
        }

        if (input.startsWith(OP_PREFIX)) { //연산자(op=) 먼저 처리
            return parseWithOperation(input);
        }

        if (input.startsWith(CUSTOM_PREFIX)) { //커스텀 구분자
            return parseWithCustomDelimiter(input);
        }

        return new ParsedInput(OperationType.ADD, parseNumbers(input, DEFAULT_DELIMITER)); //그냥 기본 덧셈
    }

    private ParsedInput parseWithOperation(String input) {
        String[] parts = input.split("\\|", 2); //2개로 나누기
        OperationType operation = OperationType.from(parts[0].substring(3)); //op= 앞 3글자 제거
        return new ParsedInput(operation, parseNumbers(parts[1], DEFAULT_DELIMITER)); //숫자파싱, 연산자 묶어서 반환
    }

    private ParsedInput parseWithCustomDelimiter(String input) {
        String delimiter = extractDelimiter(input); //;추출
        String numbers = extractNumbers(input); //숫자추출
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
                .map(String::trim) //공백제거
                .map(this::toNumber) //문자열을 숫자 변환
                .toList(); //리스트로 반환
    }

    private Double toNumber(String token) {
        validateToken(token); //빈 값 검사

        try {
            double number = Double.parseDouble(token); //숫자 변환
            validateNegative(number); //음수체크
            return number;
        } catch (NumberFormatException e) { //숫자 아닐때 예외처리
            throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다");
        }
    }

    private void validateToken(String token) {
        if (token.isBlank()) { //토큰이 비어있을 때 예외처리
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private void validateNegative(double number) {
        if (number < 0) { //음수일 떄 예외처리
            throw new IllegalArgumentException("음수는 입력할 수 없습니다");
        }
    }
}
