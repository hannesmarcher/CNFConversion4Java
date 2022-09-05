package treerepresentation.operator.impl;

import treerepresentation.Atom;
import treerepresentation.IFormula;
import treerepresentation.operator.InternalOperatorNames;
import treerepresentation.operator.UnaryOperator;

public class Negation extends UnaryOperator {

    public Negation(IFormula a) {
        super(a);
    }

    @Override
    public IFormula getCNF() {
        // Here we have negation
        // After obtaining a as CNF formula, the steps consist purely of applying the De Morgan's law

        var cnfOfA = getA().getCNF();
        if (cnfOfA instanceof Atom)
            return new Negation(cnfOfA); // if cnfOfA is an atom, we can simply negate it (which still is yields CNF)
        if (cnfOfA instanceof Negation)
            return ((Negation) cnfOfA).getA(); // cnfOfA is in CNF, thus we return here an atom
        if (cnfOfA instanceof OrOperator)
            return new AndOperator(new Negation(((OrOperator) cnfOfA).getA()), new Negation(((OrOperator) cnfOfA).getB())).getCNF(); // De Morgan for and-operator

        if (cnfOfA instanceof AndOperator)
            return new OrOperator(new Negation(((AndOperator) cnfOfA).getA()), new Negation(((AndOperator) cnfOfA).getB())).getCNF(); // De Morgan for or-operator


        // Optimization note: if the top-level operator of cnfOfA is an or-operator, we know that cnfOfA is a single clause (otherwise it would not be CNF)
        return null;
    }

    @Override
    public boolean isCNF() {
        return getA() instanceof Atom;
    }

    @Override
    public String getFormulaAsString() {
        return InternalOperatorNames.INTERNAL_NOT_OPERATOR + "(" + getA().getFormulaAsString() + ")" ;
    }

    @Override
    public String getCNFAsString() {
        return InternalOperatorNames.INTERNAL_NOT_OPERATOR + getA().getCNFAsString();
    }
}
