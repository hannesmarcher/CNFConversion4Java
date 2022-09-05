package validation;

import exception.FormulaNotCNFException;
import treerepresentation.Atom;
import treerepresentation.IFormula;
import treerepresentation.operator.impl.AndOperator;
import treerepresentation.operator.impl.Negation;
import treerepresentation.operator.impl.OrOperator;

import java.util.LinkedList;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Checks, iteratively, if the submitted formula is in CNF.
 */
public class CNFChecker {

    private final IFormula formula;

    public CNFChecker(IFormula formula) {
        this.formula = formula;
    }

    /**
     * Checks if the following axioms hold:
     *    1. An atom is CNF;
     *    2. A negated atom is CNF;
     *    3. A disjunction of literals is CNF;
     *    4. A conjunction of formulas obtained by (3) is CNF;
     * @throws FormulaNotCNFException if formula not in CNF*/
    public void assertCNF() throws FormulaNotCNFException {
        assertCNF(formula);
    }

    private void assertCNF(IFormula formula) throws FormulaNotCNFException {
        try {
            if (formula instanceof Atom) {
                assertCNF((Atom) formula);
            } else if (formula instanceof Negation) {
                assertCNF((Negation) formula);
            } else if (formula instanceof OrOperator) {
                assertCNF((OrOperator) formula);
            } else if (formula instanceof AndOperator) {
                assertCNF((AndOperator) formula);
            } else {
                throw new FormulaNotCNFException(formula.getClass() + " is not allowed");
            }
        } catch (FormulaNotCNFException e) {
            throw new FormulaNotCNFException("The formula: " + formula + " is not in CNF: \n\t" + e.getMessage());
        }
    }

    private void assertCNF(AndOperator formula) throws FormulaNotCNFException {
        Predicate<Class<?>> assertAtomNegationOrAnd = aClass -> !Set.of(Atom.class, Negation.class, OrOperator.class, AndOperator.class).contains(aClass);

        var subFormulas = new LinkedList<AndOperator>();
        subFormulas.add(formula);
        while (!subFormulas.isEmpty()) {
            var currentFormula = subFormulas.pop();
            if (assertAtomNegationOrAnd.test(currentFormula.getA().getClass()) | assertAtomNegationOrAnd.test(currentFormula.getB().getClass()))
                throw new FormulaNotCNFException("Found not allowed operator inside 'and' class");
            //post-condition: currentFormula.a and currentFormula.b are either and, or, negation, atom

            if (currentFormula.getA() instanceof Atom | currentFormula.getA() instanceof Negation | currentFormula.getA() instanceof OrOperator)
                assertCNF(currentFormula.getA());
            else
                subFormulas.add((AndOperator) currentFormula.getA());
            if (currentFormula.getB() instanceof Atom | currentFormula.getB() instanceof Negation | currentFormula.getB() instanceof OrOperator)
                assertCNF(currentFormula.getB());
            else
                subFormulas.add((AndOperator) currentFormula.getB());
        }
    }

    private void assertCNF(OrOperator formula) throws FormulaNotCNFException {
        Predicate<Class<?>> assertAtomNegationOr = aClass -> !Set.of(Atom.class, Negation.class, OrOperator.class).contains(aClass);

        var subFormulas = new LinkedList<OrOperator>();
        subFormulas.add(formula);
        while (!subFormulas.isEmpty()) {
            var currentFormula = subFormulas.pop();
            if (assertAtomNegationOr.test(currentFormula.getA().getClass()) | assertAtomNegationOr.test(currentFormula.getB().getClass()))
                throw new FormulaNotCNFException("Found not allowed operator inside 'or' class");
            //post-condition: currentFormula.a and currentFormula.b are either or, negation, atom

            if (currentFormula.getA() instanceof Atom | currentFormula.getA() instanceof  Negation)
                assertCNF(currentFormula.getA());
            else
                subFormulas.add((OrOperator) currentFormula.getA());
            if (currentFormula.getB() instanceof Atom | currentFormula.getB() instanceof  Negation)
                assertCNF(currentFormula.getB());
            else
                subFormulas.add((OrOperator) currentFormula.getB());
        }
    }

    private void assertCNF(Negation formula) throws FormulaNotCNFException {
        if (!formula.getA().getClass().equals(Atom.class))
            throw new FormulaNotCNFException("Negation only allowed before atom");
    }

    private void assertCNF(Atom ignoredFormula) throws FormulaNotCNFException {}
}
