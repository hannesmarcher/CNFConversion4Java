package cnfconversion;

import treerepresentation.IFormula;

/**
 * Transforms, iteratively, the submitted formula to CNF.
 */
public class CNFConverter {

    private final IFormula formula;

    public CNFConverter(IFormula formula) {
        this.formula = formula;
    }

    /**
     * NOTE: this function is not implemented yet - please consider IFormula.getCNF()
     * Performs the following steps:
     * 1. Transformation of Equivalence, Implies, Xor
     * 2. Applying De Morgan's Law, thus resulting
     * 3. Double negation elimination
     * 4. Applying distributive law
     * @return an equivalent version of the underlying formula, but in CNF
     */
    public IFormula transform() {
        return null;
    }

}
