package treerepresentation.operator;

public abstract class InternalOperatorNames {

    public static final String INTERNAL_AND_OPERATOR = "&";
    public static final String INTERNAL_OR_OPERATOR = "|";
    public static final String INTERNAL_NOT_OPERATOR = "-";
    public static final String INTERNAL_EQUIVALENCE_OPERATOR = "=";
    public static final String INTERNAL_IMPLIES_OPERATOR = "~";
    public static final String INTERNAL_XOR_OPERATOR = "^";

    public static final String[] BINARY_OPERATORS = new String[]{
            INTERNAL_AND_OPERATOR, INTERNAL_OR_OPERATOR, INTERNAL_EQUIVALENCE_OPERATOR, INTERNAL_IMPLIES_OPERATOR, INTERNAL_XOR_OPERATOR
    };
}
