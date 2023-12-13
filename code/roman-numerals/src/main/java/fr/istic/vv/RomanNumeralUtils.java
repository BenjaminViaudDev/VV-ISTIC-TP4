package fr.istic.vv;

import java.util.*;

public class RomanNumeralUtils {

        private static final Map<Character, Integer> romanMap = new HashMap<>();

        static {
                romanMap.put('I', 1);
                romanMap.put('V', 5);
                romanMap.put('X', 10);
                romanMap.put('L', 50);
                romanMap.put('C', 100);
                romanMap.put('D', 500);
                romanMap.put('M', 1000);
        }
        public static boolean isValidRomanNumeral(String value) {
                if (value == null || value.isEmpty()) {
                        return false;
                }

                int count = 1;
                char prev = value.charAt(0);

                for (int i = 1; i < value.length(); i++) {
                        char current = value.charAt(i);

                        if (!romanMap.containsKey(prev) || !romanMap.containsKey(current)) {
                                return false;
                        }

                        if (romanMap.get(prev) < romanMap.get(current)) {
                                if (count > 1 || !isValidSubtraction(prev, current)) {
                                        return false;
                                }
                                count = 0;
                        } else {
                                count = (prev == current) ? count + 1 : 1;
                                if (count > getMaxRepetitions(prev)) {
                                        return false;
                                }
                        }

                        // Handling D, L, V not repeated
                        if (doesNotAllowRepetition(prev, current)) {
                                return false;
                        }

                        prev = current;
                }
                return true;
        }

        private static boolean doesNotAllowRepetition(char prev, char current) {
                return "DLV".indexOf(prev) >= 0 && prev == current;
        }

        private static int getMaxRepetitions(char symbol) {
                return (symbol == 'M' || symbol == 'C' || symbol == 'X' || symbol == 'I') ? 3 : 1;
        }

        private static boolean isValidSubtraction(char smaller, char larger) {
                List<Character> validSubtractions = Arrays.asList('I', 'X', 'C');
                return validSubtractions.contains(smaller) && (romanMap.get(larger) / romanMap.get(smaller) <= 10);
        }
    
        public static int parseRomanNumeral(String numeral) {
                if (!isValidRomanNumeral(numeral)) {
                        throw new IllegalArgumentException("Invalid Roman Numeral");
                }

                int result = 0;
                int prevValue = 0;

                for (int i = numeral.length() - 1; i >= 0; i--) {
                        int currentValue = romanMap.get(numeral.charAt(i));

                        if (currentValue < prevValue) {
                                result -= currentValue;
                        } else {
                                result += currentValue;
                        }

                        prevValue = currentValue;
                }

                return result;
        }
    
        public static String toRomanNumeral(int number) {
                if (number <= 0 || number >= 4000) {
                        throw new IllegalArgumentException("Number out of range for Roman numeral conversion");
                }

                Map<Integer, String> map = new LinkedHashMap<>();
                map.put(1000, "M");
                map.put(900, "CM");
                map.put(500, "D");
                map.put(400, "CD");
                map.put(100, "C");
                map.put(90, "XC");
                map.put(50, "L");
                map.put(40, "XL");
                map.put(10, "X");
                map.put(9, "IX");
                map.put(5, "V");
                map.put(4, "IV");
                map.put(1, "I");

                StringBuilder roman = new StringBuilder();
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        int value = entry.getKey();
                        String symbol = entry.getValue();

                        while (number >= value) {
                                roman.append(symbol);
                                number -= value;
                        }
                }
                return roman.toString();
        }
    
}
