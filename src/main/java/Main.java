import config.OperatorRepresentationsConfig;
import exception.FormulaNotCNFException;
import exception.FormulaNotCorrectShapeException;
import parser.FormulaRepresentationParser;

public class Main {

    public static void main(String[] args) {
        var formula = "(¬A    ∧ CDS) ∨ (D_C → D)  "; // using operators defined in config.OperatorRepresentations
        //var formula = "(-A & CDS) | (C_C ~ D)"; // using default operators
        try {
            System.out.println(Converter.convert(formula, new FormulaRepresentationParser(new OperatorRepresentationsConfig())));
        } catch (FormulaNotCorrectShapeException e) {
            System.err.println("The supplied formula is not valid: " + e.getMessage());
        } catch (FormulaNotCNFException e) {
            System.err.println("No valid CNF has been generated, this probably indicates an implementation bug: " + e.getMessage());
        }
    }

}
