package treerepresentation.operator;

import treerepresentation.IFormula;

import java.util.Objects;

public abstract class BinaryOperator implements IFormula {

    private final IFormula a;
    private final IFormula b;

    public BinaryOperator(IFormula a, IFormula b) {
        this.a = a;
        this.b = b;
    }

    public IFormula getA() {
        return a;
    }

    public IFormula getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperator that = (BinaryOperator) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b);
    }
}
