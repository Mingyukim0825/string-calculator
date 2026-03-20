package calculator;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 진입점. 흐름 조율만 담당한다.
 * 파싱, 계산, 이력 관리를 직접 구현하지 말 것.
 * 각 책임은 별도 클래스에 위임할 것.
 */


public class StringCalculator {

    private final Deque<String> history = new ArrayDeque<>(); //이력저장

    public double calculate(String input) {

        //null 또는 빈값 0으로 처리
        if (input == null || input.isBlank()) {
            return 0;
        }

        String originalInput = input; //원본 저장

        //연산자 처리
        String operator = "+";
        if (input.startsWith("op=")) { //op=~ 형태 처리
            int idx = input.indexOf("|");
            operator = input.substring(3, idx);//연산자 추출
            input = input.substring(idx + 1);

            if (!List.of("+", "-", "*", "/").contains(operator)) {
                throw new IllegalArgumentException("지원하지 않는 연산자입니다");
            }//잘못된 연산자 예외
        }

        //구분자 처리
        String delimiter = ",|:"; //기본구분자

        if (input.startsWith("//")) { //커스텀구분자
            int idx = input.indexOf("\n");
            delimiter = Pattern.quote(input.substring(2, idx));
            input = input.substring(idx + 1); //숫자부분만 남김
        }

        //파싱
        String[] tokens = input.split(delimiter); //문자열 토큰 분리하기
        List<Double> numbers = new ArrayList<>();

        for (String token : tokens) {
            try {
                double num = Double.parseDouble(token); //숫자 변환

                if (num < 0) {//음수 예외처리
                    throw new IllegalArgumentException("음수는 입력할 수 없습니다");
                }

                numbers.add(num);

            } catch (NumberFormatException e) { //숫자 아닌 값 예외처리
                throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다");
            }
        }

        //계산
        double result = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            double num = numbers.get(i);

            switch (operator) {
                case "+" -> result += num;
                case "-" -> result -= num;
                case "*" -> result *= num;
                case "/" -> {
                    if (num == 0) {
                        throw new IllegalArgumentException("0으로 나눌 수 없습니다");
                    }
                    result /= num;
                }
            }
        }

        //소수점 처리 (소수점 1자리)
        result = Math.round(result * 10) / 10.0;

        //히스토리 저장 (최근순, 최대 10개)
        history.addFirst(originalInput + " = " + result);
        if (history.size() > 10) {
            history.removeLast();
        }

        return result;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }
}
