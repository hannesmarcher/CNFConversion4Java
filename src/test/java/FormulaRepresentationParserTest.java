import config.IOperatorRepresentationsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FormulaRepresentationParserTest {

    @Mock
    IOperatorRepresentationsConfig operatorRepresentationsConfigMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
