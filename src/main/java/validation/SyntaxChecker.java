package validation;

import treerepresentation.operator.InternalOperatorNames;
import exception.FormulaNotCorrectShapeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SyntaxChecker {

    private final String formula;

    public SyntaxChecker(String formula) {
        this.formula = formula;
    }

    /**
     * Asserts that the supplied formula has well-formed brackets.
     * Well-formed brackets are asserted using a counter.
     * An opening bracket results the counter to be incremented, while a closing bracket results in a decrement.
     * The formula is invalid if:
     * At the end of the formula the counter is not 0.
     * The counter was negative at any point.
     *
     * @throws FormulaNotCorrectShapeException if the brackets are not well-formed
     */
    public void assertWellFormedBrackets() throws FormulaNotCorrectShapeException {
        var brackets = formula.replaceAll("[^()]", "");
        var bracketOpeningCounter = 0;
        for (int i = 0; i < brackets.length(); i++) {
            if (brackets.charAt(i) == '(')
                bracketOpeningCounter++;
            else
                bracketOpeningCounter--;

            if (bracketOpeningCounter < 0)
                throw new FormulaNotCorrectShapeException("In the supplied formula there are closing brackets without corresponding opening brackets");
        }

        if (bracketOpeningCounter > 0)
            throw new FormulaNotCorrectShapeException("The supplied formula contains more opening brackets than closing");
    }

    /**
     * Asserts that between each variable an operator appear and that no operators appear sequentially (i.e. without any variable in between).
     *
     * @throws FormulaNotCorrectShapeException if the operators are not well-formed, e.g. if two sequential "&" appear
     */
    public void assertWellFormedOperators() throws FormulaNotCorrectShapeException {
        var formulaWithoutWhiteSpaces = formula.replaceAll("\\s+", "");
        var notAllowedPatterns = new ArrayList<String>();

        // two binary operator mustn't follow each other:
        for (var binaryOperator1 : InternalOperatorNames.BINARY_OPERATORS)
            for (var binaryOperator2 : InternalOperatorNames.BINARY_OPERATORS)
                notAllowedPatterns.add("\\" + binaryOperator1 + "\\" + binaryOperator2);

        // double negation is not allowed:
        notAllowedPatterns.add(InternalOperatorNames.INTERNAL_NOT_OPERATOR + InternalOperatorNames.INTERNAL_NOT_OPERATOR);

        // negation mustn't follow a binary operator
        for (var binaryOperator : InternalOperatorNames.BINARY_OPERATORS)
            notAllowedPatterns.add(InternalOperatorNames.INTERNAL_NOT_OPERATOR + "\\" + binaryOperator);

        if (notAllowedPatterns.stream().map(Pattern::compile).anyMatch(p -> p.matcher(formulaWithoutWhiteSpaces).find()))
            throw new FormulaNotCorrectShapeException("The supplied formula is not in correct shape: " +
                    "Either (a) two binary operators follow each other, (b) double negation exists, or (c) a binary operator follows negation");

        // a formula isn't allowed to start or end with a binary operator; also holds for sub-formulas (i.e. formulas inside brackets)
        if (Arrays.stream(InternalOperatorNames.BINARY_OPERATORS).anyMatch(s ->
                formulaWithoutWhiteSpaces.startsWith(s) || formulaWithoutWhiteSpaces.endsWith(s) || formulaWithoutWhiteSpaces.contains("(" + s) || formulaWithoutWhiteSpaces.contains(s + ")")))
            throw new FormulaNotCorrectShapeException("The supplied formula is not in correct shape: a formula mustn't start/end with a binary operator; also holds for sub-formulas (i.e. formulas inside brackets)");
    }
}
