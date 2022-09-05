import exception.FormulaNotCorrectShapeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import validation.SyntaxChecker;

public class SyntaxCheckerTest {
    private SyntaxChecker sut;

    @Test
    public void assertWellFormedBrackets_formulaWellFormed() {
        //Given
        var formula = "A & B | ((C ~ D) = E)";
        sut = new SyntaxChecker(formula);

        //Act & Assert
        Assertions.assertDoesNotThrow(() -> sut.assertWellFormedBrackets());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A & B | ((C ~ D) = E",
            "A & B | (C ~ D) = E)",
    })
    public void assertWellFormedBrackets_formulasNotCorrectShape(String formula) {
        //Given
        sut = new SyntaxChecker(formula);

        //Act & Assert
        Assertions.assertThrows(FormulaNotCorrectShapeException.class, () -> sut.assertWellFormedBrackets());
    }


    @Test
    public void assertWellFormedOperators_formulaWellFormed() {
        //Given
        var formula = "A & B | ((C ~ D) = E)";
        sut = new SyntaxChecker(formula);

        //Act & Assert
        Assertions.assertDoesNotThrow(() -> sut.assertWellFormedOperators());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A & B | ((C ~~ D) = E)",
            "A & --B | ((C ~ D) = E)",
            "A -& B | ((C ~ D) = E)",
            "& A & B | ((C ~ D) = E)",
            "A & B | ((C ~ D) = E) |",
            "A & B | (|(C ~ D) = E)",
            "A & B | ((C ~ D) = E ^)",
    })
    public void assertWellFormedOperators_formulasNotCorrectShape(String formula) {
        //Given
        sut = new SyntaxChecker(formula);

        //Act & Assert
        Assertions.assertThrows(FormulaNotCorrectShapeException.class, () -> sut.assertWellFormedOperators());
    }
}
