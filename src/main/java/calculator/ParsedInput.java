package calculator;

import java.util.List;

public record ParsedInput(OperationType operation, List<Double> numbers) {
} //파싱된 결과를 담는 데이터 전용 객체