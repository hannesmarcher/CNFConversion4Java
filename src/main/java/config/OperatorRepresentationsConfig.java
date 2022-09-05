package config;

public class OperatorRepresentationsConfig implements IOperatorRepresentationsConfig {
    @Override
    public String getAnd() {
        return OperatorRepresentations.AND_OPERATOR;
    }

    @Override
    public String getOr() {
        return OperatorRepresentations.OR_OPERATOR;
    }

    @Override
    public String getNot() {
        return OperatorRepresentations.NOT_OPERATOR;
    }

    @Override
    public String getEquivalence() {
        return OperatorRepresentations.EQUIVALENCE_OPERATOR;
    }

    @Override
    public String getImplies() {
        return OperatorRepresentations.IMPLIES_OPERATOR;
    }

    @Override
    public String getXor() {
        return OperatorRepresentations.XOR_OPERATOR;
    }
}
