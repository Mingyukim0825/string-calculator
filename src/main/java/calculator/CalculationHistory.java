package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * 힌트: 연산 이력을 관리하는 클래스.
 * 일급 컬렉션으로 구현해볼 것.
 * 반드시 이 클래스를 사용할 필요는 없다. 자유롭게 설계할 것.
 */

public class CalculationHistory {
    private static final int MAX_SIZE = 10; //계산 이력 최대 10개
    private final Deque<String> histories = new ArrayDeque<>(); //Deque 자료구조를 이용해서 양쪽에서 빠르게 넣고 뺌
                                                                //일급 컬렉션, 캡슐화
    public void record(String input, double result) { //새로운 연산 결과 저장
        removeOldestIfFull(); //10개 넘으면 오래된 것부터 제거
        histories.addFirst(input + " = " + result); //최신순으로 앞에서부터 추가
    }

    public List<String> getRecentHistories() {
        return histories.stream().toList();
    } //이력 반환해줌

    private void removeOldestIfFull() {
        if (histories.size() < MAX_SIZE) {
            return;
        }
        histories.removeLast();
    }
}