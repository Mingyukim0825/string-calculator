package calculator;

import java.util.List;

/**
 * 진입점. 흐름 조율만 담당한다.
 * 파싱, 계산, 이력 관리를 직접 구현하지 말 것.
 * 각 책임은 별도 클래스에 위임할 것.
 */
public class StringCalculator {
    private final InputParser parser;
    private final Calculator calculator;
    private final CalculationHistory history;

    public StringCalculator() {
        this.parser = new InputParser();
        this.calculator = new Calculator();
        this.history = new CalculationHistory();
    }

    public double calculate(String input) {
        ParsedInput parsedInput = parser.parse(input);
        double result = calculator.calculate(parsedInput);
        history.record(input, result);
        return result;
    }

    public List<String> getHistory() {
        return history.getRecentHistories();
    }
}