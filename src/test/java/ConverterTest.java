import config.IOperatorRepresentationsConfig;
import exception.FormulaNotCNFException;
import exception.FormulaNotCorrectShapeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import parser.FormulaRepresentationParser;

public class ConverterTest {


    @Mock
    IOperatorRepresentationsConfig operatorRepresentationsConfigMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "-(A ^ (B&R)), (-A|-R|R)&(-A|-R|B)&(-A|-R|A)&(-A|-A|R)&(-A|-A|B)&(-A|-A|A)&(-B|-A|R)&(-B|-A|B)&(-B|-A|A)&(-B|-R|R)&(-B|-R|A)&(-B|-R|B), 1", //equivalent to (B ∨ ¬A) ∧ (R ∨ ¬A) ∧ (A ∨ ¬B ∨ ¬R); problem is that atm trivial clauses are not automatically removed
            "A ^ (B&R), (A|B)&(A|R)&(-A|-B|-R), 2",
            "P|(-R&S&(T~(V=B))), (P|-T|-V|B)&(P|-T|V|-B)&(P|-R)&(P|S), 3",
    })
    public void convert(String formula, String expectedCNF, int operatorSetNumber) throws FormulaNotCorrectShapeException, FormulaNotCNFException {
        //Given
        initOperatorSet(operatorSetNumber);
        var formulaRepresentationParser = new FormulaRepresentationParser(operatorRepresentationsConfigMock);
        formula = formulaRepresentationParser.getExternalRepresentation(formula);

        //Act
        var cnfFormula = Converter.convert(formula, formulaRepresentationParser);

        //Assert
        Assertions.assertEquals(formulaRepresentationParser.getExternalRepresentation(expectedCNF).replaceAll("\\s+", ""), cnfFormula.replaceAll("\\s+", ""));
    }

    private void initOperatorSet(int operatorSetNumber) {
        if (operatorSetNumber == 1) {
            Mockito.when(operatorRepresentationsConfigMock.getAnd()).thenReturn("∧");
            Mockito.when(operatorRepresentationsConfigMock.getOr()).thenReturn("∨");
            Mockito.when(operatorRepresentationsConfigMock.getNot()).thenReturn("¬");
            Mockito.when(operatorRepresentationsConfigMock.getEquivalence()).thenReturn("↔");
            Mockito.when(operatorRepresentationsConfigMock.getImplies()).thenReturn("→");
            Mockito.when(operatorRepresentationsConfigMock.getXor()).thenReturn("^");
        } else if (operatorSetNumber == 2) {
            Mockito.when(operatorRepresentationsConfigMock.getAnd()).thenReturn("&");
            Mockito.when(operatorRepresentationsConfigMock.getOr()).thenReturn("|");
            Mockito.when(operatorRepresentationsConfigMock.getNot()).thenReturn("-");
            Mockito.when(operatorRepresentationsConfigMock.getEquivalence()).thenReturn("=");
            Mockito.when(operatorRepresentationsConfigMock.getImplies()).thenReturn("~");
            Mockito.when(operatorRepresentationsConfigMock.getXor()).thenReturn("^");
        } else if (operatorSetNumber == 3) {
            Mockito.when(operatorRepresentationsConfigMock.getAnd()).thenReturn("and");
            Mockito.when(operatorRepresentationsConfigMock.getOr()).thenReturn("or");
            Mockito.when(operatorRepresentationsConfigMock.getNot()).thenReturn("not");
            Mockito.when(operatorRepresentationsConfigMock.getEquivalence()).thenReturn("eq");
            Mockito.when(operatorRepresentationsConfigMock.getImplies()).thenReturn("impl");
            Mockito.when(operatorRepresentationsConfigMock.getXor()).thenReturn("xor");
        }
    }


}
