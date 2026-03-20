package calculator;

import java.util.Arrays;
import java.util.function.BinaryOperator;
/**
 * 힌트: 연산자 종류를 나타내는 enum.
 * +, -, *, / 를 표현하고, 각 연산자에 맞는 계산을 수행할 수 있다.
 * 반드시 이 클래스를 사용할 필요는 없다. 자유롭게 설계할 것.
 */

public enum OperationType {
    ADD("+", (a, b) -> a + b),
    SUBTRACT("-", (a, b) -> a - b),
    MULTIPLY("*", (a, b) -> a * b),
    DIVIDE("/", (a, b) -> divide(a, b));

    private final String symbol; //연산자
    private final BinaryOperator<Double> operation; //계산 결과

    OperationType(String symbol, BinaryOperator<Double> operation) {
        this.symbol = symbol;
        this.operation = operation;
    }

    public static OperationType from(String symbol) {
        return Arrays.stream(values())
                .filter(type -> type.symbol.equals(symbol)) //연산자 찾기
                .findFirst() //첫번째 결과
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 연산자입니다."));
    }               //없으면 예외처리

    public double apply(double a, double b) {
        return operation.apply(a, b);
    }

    private static double divide(double a, double b) {
        if (b == 0) { //0으로 나누기 예외처리
            throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
        }
        return Math.round((a / b) * 10) / 10.0; //소수점 계산
    }
}