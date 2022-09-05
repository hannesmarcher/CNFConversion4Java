package treerepresentation.operator.impl;

import treerepresentation.IFormula;
import treerepresentation.operator.BinaryOperator;
import treerepresentation.operator.InternalOperatorNames;

public class AndOperator extends BinaryOperator {
    public AndOperator(IFormula a, IFormula b) {
        super(a, b);
    }

    @Override
    public IFormula getCNF() {
        // Here we have conjunction
        // The steps here are rather simple, i.e. if a and b are in CNF, then, trivially, the conjunction of a and b is CNF

        var cnfA = getA().getCNF();
        var cnfB = getB().getCNF();

        return new AndOperator(cnfA, cnfB);
    }

    @Override
    public boolean isCNF() {
        return getA().isCNF() && getB().isCNF();
    }

    @Override
    public String getFormulaAsString() {
        return "(" + getA().getFormulaAsString() + ")" + InternalOperatorNames.INTERNAL_AND_OPERATOR + "(" + getB().getFormulaAsString() + ")" ;
    }

    @Override
    public String getCNFAsString() {
        var formula = "";
        var a = getA();
        var b = getB();
        if (a instanceof OrOperator)
            formula += "(" + a.getCNFAsString() + ")";
        else
            formula += a.getCNFAsString();

        formula += InternalOperatorNames.INTERNAL_AND_OPERATOR;

        if (b instanceof OrOperator)
            formula += "(" + b.getCNFAsString() + ")";
        else
            formula += b.getCNFAsString();

        return formula;
    }
}
