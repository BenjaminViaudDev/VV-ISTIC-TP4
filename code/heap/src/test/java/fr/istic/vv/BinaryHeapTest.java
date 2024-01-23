package fr.istic.vv;
import net.jqwik.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class BinaryHeapTest {
    @Property
    boolean popReturnsMinimumElement(@ForAll List<Integer> integers) {
        if (integers.isEmpty()) {
            return true;
        }

        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(Comparator.naturalOrder());

        for (Integer value : integers) {
            binaryHeap.push(value);
        }

        List<Integer> sortedList = new ArrayList<>(integers);
        sortedList.sort(Comparator.naturalOrder());

        while (!binaryHeap.isEmpty()) {
            Integer minElement = binaryHeap.pop();
            Integer expectedMinElement = sortedList.remove(0);
            if (!minElement.equals(expectedMinElement)) {
                return false;
            }
        }

        return true;
    }

    @Property
    boolean peekReturnsMinimumElement(@ForAll List<Integer> integers) {
        if (integers.isEmpty()) {
            return true;
        }

        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(Comparator.naturalOrder());

        for (Integer value : integers) {
            binaryHeap.push(value);
        }

        List<Integer> sortedList = new ArrayList<>(integers);
        sortedList.sort(Comparator.naturalOrder());

        for (Integer expectedMinElement : sortedList) {
            Integer minElement = binaryHeap.peek();
            if (!minElement.equals(expectedMinElement)) {
                return false;
            }
            binaryHeap.pop();
        }

        return true;
    }

    @Property
    boolean pushIncreasesHeapSize(@ForAll List<Integer> integers, @ForAll("newElement") int newElement) {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(Comparator.naturalOrder());

        for (Integer value : integers) {
            binaryHeap.push(value);
        }

        int initialSize = binaryHeap.count();

        binaryHeap.push(newElement);

        return binaryHeap.count() == initialSize + 1;
    }

    @Property
    boolean testBinaryHeapFunctionality(@ForAll List<Integer> integers) {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(Comparator.naturalOrder());

        for (Integer value : integers) {
            binaryHeap.push(value);
        }

        List<Integer> sortedList = new ArrayList<>(integers);
        sortedList.sort(Comparator.naturalOrder());

        while (!binaryHeap.isEmpty()) {
            Integer minElement = binaryHeap.pop();
            Integer expectedMinElement = sortedList.remove(0);
            if (!minElement.equals(expectedMinElement)) {
                return false;
            }
        }

        return true;
    }

    @Provide
    Arbitrary<Integer> newElement() {
        return Arbitraries.integers();
    }
    @Property
    boolean absoluteValueOfAllNumbersIsPositive(@ForAll int anInteger) {
        return anInteger == Integer.MIN_VALUE || Math.abs(anInteger) >= 0;
    }
}
