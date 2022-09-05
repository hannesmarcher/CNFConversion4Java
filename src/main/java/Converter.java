import exception.FormulaNotCNFException;
import exception.FormulaNotCorrectShapeException;
import parser.FormulaRepresentationParser;
import parser.FormulaToTreeParser;
import validation.CNFChecker;
import validation.SyntaxChecker;

public class Converter {

    public static String convert(String formula, FormulaRepresentationParser formulaRepresentationParser) throws FormulaNotCorrectShapeException, FormulaNotCNFException {
        formula = formulaRepresentationParser.getInternalRepresentation(formula);
        var syntaxChecker = new SyntaxChecker(formula);
        syntaxChecker.assertWellFormedBrackets();
        syntaxChecker.assertWellFormedOperators();
        var cnf = new FormulaToTreeParser(formula).parse().getCNF();
        new CNFChecker(cnf).assertCNF();
        return formulaRepresentationParser.getExternalRepresentation(cnf.getCNFAsString());
    }
}
