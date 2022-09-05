package parser;

import treerepresentation.operator.InternalOperatorNames;
import config.IOperatorRepresentationsConfig;

/**
 * Since config.OperatorRepresentations can be used to specify custom operators, one has to distinguish between internal and external operator representations.
 * This class is used to transform between both representations.
 */
public class FormulaRepresentationParser {

    private final IOperatorRepresentationsConfig operatorRepresentationsConfig;

    public FormulaRepresentationParser(IOperatorRepresentationsConfig operatorRepresentationsConfig) {
        this.operatorRepresentationsConfig = operatorRepresentationsConfig;
    }

    public String getInternalRepresentation(String formula) {
        var newFormula = formula.replace(operatorRepresentationsConfig.getAnd(), InternalOperatorNames.INTERNAL_AND_OPERATOR);
        newFormula = newFormula.replace(operatorRepresentationsConfig.getEquivalence(), InternalOperatorNames.INTERNAL_EQUIVALENCE_OPERATOR);
        newFormula = newFormula.replace(operatorRepresentationsConfig.getImplies(), InternalOperatorNames.INTERNAL_IMPLIES_OPERATOR);
        newFormula = newFormula.replace(operatorRepresentationsConfig.getNot(), InternalOperatorNames.INTERNAL_NOT_OPERATOR);
        newFormula = newFormula.replace(operatorRepresentationsConfig.getOr(), InternalOperatorNames.INTERNAL_OR_OPERATOR);
        newFormula = newFormula.replace(operatorRepresentationsConfig.getXor(), InternalOperatorNames.INTERNAL_XOR_OPERATOR);
        return newFormula;
    }

    public String getExternalRepresentation(String formula) {
        var newFormula = formula.replace(InternalOperatorNames.INTERNAL_AND_OPERATOR, operatorRepresentationsConfig.getAnd());
        newFormula = newFormula.replace(InternalOperatorNames.INTERNAL_EQUIVALENCE_OPERATOR, operatorRepresentationsConfig.getEquivalence());
        newFormula = newFormula.replace(InternalOperatorNames.INTERNAL_IMPLIES_OPERATOR, operatorRepresentationsConfig.getImplies());
        newFormula = newFormula.replace(InternalOperatorNames.INTERNAL_NOT_OPERATOR, operatorRepresentationsConfig.getNot());
        newFormula = newFormula.replace(InternalOperatorNames.INTERNAL_OR_OPERATOR, operatorRepresentationsConfig.getOr());
        newFormula = newFormula.replace(InternalOperatorNames.INTERNAL_XOR_OPERATOR, operatorRepresentationsConfig.getXor());
        return newFormula;
    }
}
