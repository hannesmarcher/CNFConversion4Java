package parser;

import treerepresentation.Atom;
import treerepresentation.IFormula;
import treerepresentation.operator.InternalOperatorNames;
import treerepresentation.operator.impl.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The purpose of this class is to transform a formula with well-shaped brackets into its tree format.
 * For example, the formula (-A & B) | (A ~ D) is transformed to: Or(And(Neg(A), B), Impl(A, D))
 */
public class FormulaToTreeParser {

    private String formula;
    private final Set<String> allVariableNames;
    private final HashMap<String, IFormula> idToFormulaMap = new HashMap<>();
    private IFormula result;

    public FormulaToTreeParser(String formula) {
        this.formula = formula.replaceAll("\\s+", "");
        allVariableNames = Arrays.stream(formula.replace("(", " ")
                        .replace(")", " ")
                        .replace(InternalOperatorNames.INTERNAL_AND_OPERATOR, " ")
                        .replace(InternalOperatorNames.INTERNAL_OR_OPERATOR, " ")
                        .replace(InternalOperatorNames.INTERNAL_NOT_OPERATOR, " ")
                        .replace(InternalOperatorNames.INTERNAL_IMPLIES_OPERATOR, " ")
                        .replace(InternalOperatorNames.INTERNAL_EQUIVALENCE_OPERATOR, " ")
                        .replace(InternalOperatorNames.INTERNAL_XOR_OPERATOR, " ")
                        .split("\\s+"))
                .collect(Collectors.toSet());
    }

    /**
     * Parses the formula and returns an object hierarchy. Useful for further processing of the formula.
     * @return The formula as IFormula
     */
    public IFormula parse() {
        //In case parse() has already been executed
        if (result != null)
            return result;

        if (!formula.contains("(")) {
            result = parseBracketLessFormula(formula);
            return result;
        }

        String regex = "\\(([^()]*?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formula);
        while (matcher.find()) {
            var subFormula = matcher.group(1);
            var parsedFormula = parseBracketLessFormula(subFormula);
            var randomIntGenerator = new Random();
            String formulaId;
            do {
                formulaId = String.valueOf(randomIntGenerator.nextInt(Integer.MAX_VALUE));
            } while (allVariableNames.contains(formulaId) || idToFormulaMap.containsKey(formulaId));
            formula = formula.replace("(" + subFormula + ")", formulaId);
            idToFormulaMap.put(formulaId, parsedFormula);
        }

        return parse();
    }

    private IFormula parseBracketLessFormula(String formula) {
        //If the following condition is true, then it is very likely that an implementation bug exists
        if (formula.contains("(") && formula.contains(")"))
            throw new RuntimeException("A sub-formula contained brackets - this probably indicates an implementation bug");


        String binaryOperatorsRegex = Arrays.stream(InternalOperatorNames.BINARY_OPERATORS).map(s -> "\\" + s).collect(Collectors.joining("|"));
        String regex = ".*(" + binaryOperatorsRegex + ").*"; // matches the last occurrence of binary operators
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formula);
        if (matcher.matches()) {
            var operator = matcher.group(1);
            var indexToSplit = formula.lastIndexOf(operator);
            var currentLiteral = formula.substring(indexToSplit + operator.length());
            var remainingFormula = formula.substring(0, indexToSplit);
            var result = parseBracketLessFormula(remainingFormula);


            return selectOperatorBasedOnOperatorName(operator, result, parseCurrentLiteral(currentLiteral));
        }

        return parseCurrentLiteral(formula);
    }

    private IFormula parseCurrentLiteral(String currentLiteral) {
        var atomName = currentLiteral.replace(InternalOperatorNames.INTERNAL_NOT_OPERATOR, "");
        if (idToFormulaMap.containsKey(atomName))
            if (currentLiteral.startsWith(InternalOperatorNames.INTERNAL_NOT_OPERATOR))
                return new Negation(idToFormulaMap.get(atomName));
            else
                return idToFormulaMap.get(atomName);

        var atom = new Atom(atomName);
        if (currentLiteral.startsWith(InternalOperatorNames.INTERNAL_NOT_OPERATOR))
            return new Negation(atom);
        return atom;
    }

    private static IFormula selectOperatorBasedOnOperatorName(String operator, IFormula a, IFormula b) {
        if (operator.equals(InternalOperatorNames.INTERNAL_AND_OPERATOR))
            return new AndOperator(a, b);
        if (operator.equals(InternalOperatorNames.INTERNAL_OR_OPERATOR))
            return new OrOperator(a, b);
        if (operator.equals(InternalOperatorNames.INTERNAL_IMPLIES_OPERATOR))
            return new ImpliesOperator(a, b);
        if (operator.equals(InternalOperatorNames.INTERNAL_EQUIVALENCE_OPERATOR))
            return new EquivalenceOperator(a, b);
        if (operator.equals(InternalOperatorNames.INTERNAL_XOR_OPERATOR))
            return new XorOperator(a, b);

        throw new RuntimeException("Operator: " + operator + " is not supported! - this probably indicates an implementation bug"); //This should never happen!
    }
}
