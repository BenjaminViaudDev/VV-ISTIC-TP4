package fr.istic.vv;
import net.jqwik.api.*;

import java.util.List;
import java.util.stream.Collectors;

public class RomanNumeralTest {

    @Property
    boolean testValidityToInteger(@ForAll("generateRomanString") String roman) {
        int value = RomanNumeralUtils.parseRomanNumeral(roman);
        return value >= 0 && value < 4000;
    }

    @Property
    boolean testNotValidToInteger(@ForAll("generateInvalidInt") int roman) {
        try {
            RomanNumeralUtils.toRomanNumeral(roman);
            // Si aucune exception n'est levée, le test échoue
            return false;
        } catch (Exception expected) {
            return true;
        }
    }

    @Property
    boolean testNotValidToRomanNumeral(@ForAll("generateInvalidRomanString") String roman) {
        try {
            RomanNumeralUtils.parseRomanNumeral(roman);
            // Si aucune exception n'est levée, le test échoue
            return false;
        } catch (Exception expected) {
            return true;
        }
    }

    @Property
    boolean testValidityToRomanNumeral(@ForAll("generateValidInt") int number) {
        String res = RomanNumeralUtils.toRomanNumeral(number);
        return RomanNumeralUtils.isValidRomanNumeral(res);
    }

    @Property
    boolean testValidityOneNumeral(@ForAll("generateRomanString") String roman) {
        return roman.matches("(M|D|C|L|X|V|I)*");
    }

    @Property
    boolean testRepetitionNotValid(@ForAll("generateRomanString") String roman) {
        List<String> bads = List.of(
                "MMMM",
                "CCCC",
                "XXXX",
                "IIII",
                "DD",
                "LL",
                "VV"
        );

        for (String bad : bads) {
            if (roman.contains(bad)) return false;
        }
        return true;
    }

    @Provide
    Arbitrary<String> generateRomanString() {
        return Arbitraries.strings().ascii().ofMinLength(1).ofMaxLength(15)
                .map(this::filterNonRomanCharacters)
                .filter(RomanNumeralUtils::isValidRomanNumeral);
    }

    @Provide
    Arbitrary<String> generateInvalidRomanString() {
        return Arbitraries.strings().withCharRange('A', 'Z') // Générer des lettres majuscules
                .ofMinLength(1).ofMaxLength(15)
                .filter(s -> !RomanNumeralUtils.isValidRomanNumeral(s)); // Exclure les chiffres romains valides
    }

    private String filterNonRomanCharacters(String input) {
        return input.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .filter(s -> "IVXLCDM".contains(s))
                .collect(Collectors.joining());
    }

    @Provide
    Arbitrary<Integer> generateValidInt(){
        return Arbitraries.integers().between(1,3999);
    }
    @Provide
    Arbitrary<Integer> generateInvalidInt() {
        return Arbitraries.oneOf(
                Arbitraries.integers().lessOrEqual(0),
                Arbitraries.integers().greaterOrEqual(4000)
        );
    }
}
