package treerepresentation.operator.impl;

import treerepresentation.IFormula;
import treerepresentation.operator.BinaryOperator;
import treerepresentation.operator.InternalOperatorNames;

public class EquivalenceOperator extends BinaryOperator {
    public EquivalenceOperator(IFormula a, IFormula b) {
        super(a, b);
    }

    @Override
    public IFormula getCNF() {
        return new AndOperator(
                new OrOperator(getA(), new Negation(getB())).getCNF(),
                new OrOperator(new Negation(getA()), getB())).getCNF();
    }

    @Override
    public boolean isCNF() {
        return false;
    }

    @Override
    public String getFormulaAsString() {
        return "(" + getA().getFormulaAsString() + ")" + InternalOperatorNames.INTERNAL_EQUIVALENCE_OPERATOR + "(" + getB().getFormulaAsString() + ")" ;
    }

    @Override
    public String getCNFAsString() {
        return "";
    }
}
