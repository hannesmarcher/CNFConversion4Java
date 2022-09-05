import treerepresentation.Atom;
import treerepresentation.operator.impl.*;
import org.junit.jupiter.api.Test;
import parser.FormulaToTreeParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormulaToTreeParserTest {

    FormulaToTreeParser sut;

    @Test
    public void parse() {
        //Given
        var formula = "(-A    & CDS) | (D_C ~ (A =   -(E ^ O) ))";
        sut = new FormulaToTreeParser(formula);

        //Assert
        sut.parse();
        var result = sut.parse();

        //Assert
        assertEquals(
                new OrOperator(
                        new AndOperator(new Negation(new Atom("A")), new Atom("CDS")),
                        new ImpliesOperator(
                                new Atom("D_C"),
                                new EquivalenceOperator(
                                        new Atom("A"),
                                        new Negation(new XorOperator(new Atom("E"), new Atom("O")))))),
                result);
    }
}
