package treerepresentation;

import java.util.Objects;

public class Atom implements IFormula {

    private final String a;

    public Atom(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return Objects.equals(a, atom.a);
    }

    @Override
    public IFormula getCNF() {
        return new Atom(a);
    }

    @Override
    public boolean isCNF() {
        return true;
    }

    @Override
    public String getFormulaAsString() {
        return a;
    }

    @Override
    public String getCNFAsString() {
        return a;
    }
}
