package calculator;

import java.util.List;

public class Calculator {
    public double calculate(ParsedInput parsedInput) { //파싱된 결과를 받아서 최종 계산 결과를 반환(double로 반환)
        List<Double> numbers = parsedInput.numbers(); //숫자 리스트 꺼내기

        if (numbers.size() == 1) { //숫자가 하나만 입력됐을때 그대로 반환
            return numbers.get(0);
        }

        return numbers.stream() //스트림 생성
                .skip(1) //reduce 초기값으로 첫번째 숫자를 써서 첫번째 값 제외
                .reduce(numbers.get(0), parsedInput.operation()::apply);
    }            //reduce(초기값, (누적값, 다음값)-> 계산)
}