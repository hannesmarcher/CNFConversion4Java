import exception.FormulaNotCNFException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parser.FormulaToTreeParser;
import validation.CNFChecker;

public class CNFCheckerTest {

    @Test
    public void assertCNF_validCNFFormula() {
        //GIVEN
        var formula = "(A | B | -C) & (A | E | F) & (-R | -F)";
        var formulaAsObject = new FormulaToTreeParser(formula).parse();

        //Assert
        Assertions.assertDoesNotThrow(() -> new CNFChecker(formulaAsObject).assertCNF());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "(A | B | -C) & (A | E | F) & -(-R | -F)",
            "((A & B) | -C) & (A | E | F) & (-R | F)",
            "(A | B ~ -C) & (A | E | F) & (-R | -F)",
            "A = B",
    })
    public void assertCNF_invalidCNFFormula(String formula) {
        //GIVEN
        var formulaAsObject = new FormulaToTreeParser(formula).parse();

        //Assert
        Assertions.assertThrows(FormulaNotCNFException.class, () -> new CNFChecker(formulaAsObject).assertCNF());
    }
}
