package treerepresentation.operator;

import treerepresentation.IFormula;

import java.util.Objects;

public abstract class UnaryOperator implements IFormula {
    private final IFormula a;

    public UnaryOperator(IFormula a) {
        this.a = a;
    }

    public IFormula getA() {
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryOperator that = (UnaryOperator) o;
        return Objects.equals(a, that.a);
    }
}
