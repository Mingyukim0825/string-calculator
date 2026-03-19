package calculator;

import java.util.List;

public class Calculator {
    public double calculate(ParsedInput parsedInput) {
        List<Double> numbers = parsedInput.numbers();

        if (numbers.size() == 1) {
            return numbers.get(0);
        }

        return numbers.stream()
                .skip(1)
                .reduce(numbers.get(0), parsedInput.operation()::apply);
    }
}