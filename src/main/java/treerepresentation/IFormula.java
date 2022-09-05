package treerepresentation;

/**
 * Tree representation of a formula.
 * For example, the tree version of A & B & C may be And(A, And(B,C)).
 */
public interface IFormula {

    /**
     * Converts the underlying formula into CNF by transforming equivalence, xor, and implication; applying double negation elimination; applying De Morgan's laws; and applying the distributive law.
     * Note that this is a recursive function, thus if the formula is especially large (may occur if many equivalence/xor relations are used - leading to exponential explosion of clauses),
     *    then the resulting tree of the formula has potentially high broadness and depth, therefore executing this function may cause memory issues.
     *    In such a case, please consider using the iterative version - see CNFConverter class (not implemented yet).
     * @return The logically equivalent formula in CNF
     */
    IFormula getCNF();

    /**
     * Checks whether the underlying formula is in CNF.
     * Note that this is a recursive function, thus vulnerable to StackOverflowErrors - only use it if the formula is small.
     * Otherwise, consider the CNFChecker class.
     * @return true iff the underlying formula is in CNF
     */
    boolean isCNF();

    /**
     * Generates from the tree representation a string representation. Note that many redundant brackets are introduced.
     * @return Formula as String
     */
    String getFormulaAsString();

    /**
     * Generates from a tree representation of a CNF formula a string representation. Note that if the formula is not in CNF, then the return value of this function may be wrong (!).
     * @return Formula as String
     */
    String getCNFAsString();
}
