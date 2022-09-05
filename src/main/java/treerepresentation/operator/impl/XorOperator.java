package treerepresentation.operator.impl;

import treerepresentation.IFormula;
import treerepresentation.operator.BinaryOperator;
import treerepresentation.operator.InternalOperatorNames;

public class XorOperator extends BinaryOperator {
    public XorOperator(IFormula a, IFormula b) {
        super(a, b);
    }

    @Override
    public IFormula getCNF() {
        return new AndOperator(new OrOperator(getA(), getB()), new Negation(new AndOperator(getA(), getB()))).getCNF();
    }

    @Override
    public boolean isCNF() {
        return false;
    }

    @Override
    public String getFormulaAsString() {
        return "(" + getA().getFormulaAsString() + ")" + InternalOperatorNames.INTERNAL_XOR_OPERATOR + "(" + getB().getFormulaAsString() + ")" ;
    }

    @Override
    public String getCNFAsString() {
        return "";
    }
}
