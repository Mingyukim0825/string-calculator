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
    private static final int MAX_SIZE = 10;
    private final Deque<String> histories = new ArrayDeque<>();

    public void record(String input, double result) {
        removeOldestIfFull();
        histories.addFirst(input + " = " + result);
    }

    public List<String> getRecentHistories() {
        return histories.stream().toList();
    }

    private void removeOldestIfFull() {
        if (histories.size() < MAX_SIZE) {
            return;
        }
        histories.removeLast();
    }
}