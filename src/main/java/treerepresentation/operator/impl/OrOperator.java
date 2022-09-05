package treerepresentation.operator.impl;

import treerepresentation.Atom;
import treerepresentation.IFormula;
import treerepresentation.operator.BinaryOperator;
import treerepresentation.operator.InternalOperatorNames;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OrOperator extends BinaryOperator {
    public OrOperator(IFormula a, IFormula b) {
        super(a, b);
    }

    @Override
    public IFormula getCNF() {
        // Here we have disjunction
        // We first require that a and b are in CNF
        // Once a and b are in CNF, we can apply the distributive law, i.e.:
        //  - if a and b are in CNF, they have the form: a1 & a2 & a3 & ..., and b1 & b2 & ..., respectively
        //  - note that ai/bi consist of a disjunction of literals
        //  - distributive law: (a1 & a2) | (b1 & b2) = (a1 | b1) & (a1 | b2) & (a2 | b1) & (a2 | b2)
        //  - thus: distributive law yields CNF

        var cnfA = getA().getCNF();
        var cnfB = getB().getCNF();

        var clausesA = getAllClausesFromCNFFormula(cnfA);
        var clausesB = getAllClausesFromCNFFormula(cnfB);

        var allOrClauses = new ArrayList<OrOperator>();
        for (var clauseA : clausesA) {
            for (var clauseB : clausesB) {
                allOrClauses.add(new OrOperator(clauseA, clauseB));
            }
        }

        return allOrClauses.size() >= 2 ? constructAndOperator(allOrClauses) : allOrClauses.get(0);
    }

    @Override
    public boolean isCNF() {
        Predicate<Object> checkValidClasses = aClass -> Set.of(Atom.class, Negation.class, OrOperator.class).contains(aClass.getClass());
        if (!checkValidClasses.test(getA()) || !checkValidClasses.test(getB()))
            return false;

        return getA().isCNF() && getB().isCNF();
    }

    private AndOperator constructAndOperator(List<OrOperator> allClauses) {
        var result = new AndOperator(allClauses.get(0), allClauses.get(1));
        for (var i = 2; i < allClauses.size(); i++) {
            result = new AndOperator(allClauses.get(i), result);
        }
        return result;
    }

    private List<IFormula> getAllClausesFromCNFFormula(IFormula formula) {
        var clauses = new ArrayList<IFormula>();

        var formulaStack = new LinkedList<IFormula>();
        formulaStack.add(formula);

        while (!formulaStack.isEmpty()) {
            var currentFormula = formulaStack.pop();
            if (currentFormula instanceof Atom || currentFormula instanceof Negation || currentFormula instanceof OrOperator)
                clauses.add(currentFormula);

            if (currentFormula instanceof AndOperator) {
                formulaStack.add(((AndOperator) currentFormula).getA());
                formulaStack.add(((AndOperator) currentFormula).getB());
            }
        }

        return clauses;
    }

    @Override
    public String getFormulaAsString() {
        return "(" + getA().getFormulaAsString() + ")" + InternalOperatorNames.INTERNAL_OR_OPERATOR + "(" + getB().getFormulaAsString() + ")" ;
    }

    @Override
    public String getCNFAsString() {
        return getA().getCNFAsString() + InternalOperatorNames.INTERNAL_OR_OPERATOR + getB().getCNFAsString();
    }
}
