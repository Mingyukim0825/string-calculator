package calculator;

import java.util.List;

public record ParsedInput(OperationType operation, List<Double> numbers) {
}