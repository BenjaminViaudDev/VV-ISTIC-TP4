package fr.istic.vv;
import net.jqwik.api.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortingTest {
    static final boolean VERBOSE = false;
    static Comparator<Object> comparator;

    static {
        comparator = new Comparator<Object>() {
            @Override
            public int compare(Object integer, Object t1) {
                if (integer instanceof Integer && t1 instanceof  Integer) {
                    if ((((Integer) integer).intValue() < ((Integer) t1).intValue())) return -1;
                    if (((Integer) integer).intValue() > ((Integer) t1).intValue()) return 1;
                }
                return 0;
            }
        };

    }
    @Property
    boolean testBubbleSort(@ForAll("getRandomList") List<Integer> list) {
        Object[] array = list.toArray();
        Sorting.bubblesort(array,comparator);
        list.sort(comparator);
        return compareArrays(array, list.toArray());
    }

    @Property
    boolean testQuickSort(@ForAll("getRandomList") List<Integer> list) {
        Object[] array = list.toArray();
        Sorting.quicksort(array,comparator);
        list.sort(comparator);
        return compareArrays(array, list.toArray());
    }

    @Property
    boolean testMergeSort(@ForAll("getRandomList") List<Integer> list) {
        Object[] array = list.toArray();
        Object[] target = Arrays.stream(list.toArray()).sorted(comparator).toArray();
        Sorting.mergesort(array,comparator);
        return compareArrays(array, target);
    }

    @Provide
    Arbitrary<List<Integer>> getRandomList() {
        return Arbitraries.integers().list().ofMinSize(1).ofMaxSize(1000);
    }

    public boolean compareArrays(Object[] array1, Object[] array2) {
        if (array1.length != array2.length) return false;

        for (int l = 0; l < array1.length; l++) {
            if (comparator.compare(array1[l], array2[l]) != 0) return false;
        }
        return true;
    }

    @Property
    boolean testBubbleSortWithRepeatedElements(@ForAll("getRandomListWithRepeats") List<Integer> list) {
        Object[] array = list.toArray();
        Sorting.bubblesort(array, comparator);
        list.sort(comparator);
        return compareArrays(array, list.toArray());
    }

    @Property
    boolean testQuickSortWithRepeatedElements(@ForAll("getRandomListWithRepeats") List<Integer> list) {
        Object[] array = list.toArray();
        Sorting.quicksort(array, comparator);
        list.sort(comparator);
        return compareArrays(array, list.toArray());
    }

    @Property
    boolean testMergeSortWithRepeatedElements(@ForAll("getRandomListWithRepeats") List<Integer> list) {
        Object[] array = list.toArray();
        Object[] target = Arrays.stream(list.toArray()).sorted(comparator).toArray();
        Sorting.mergesort(array, comparator);
        return compareArrays(array, target);
    }

    @Provide
    Arbitrary<List<Integer>> getRandomListWithRepeats() {
        return Arbitraries.integers().list().ofMinSize(1).ofMaxSize(1000);
    }
}