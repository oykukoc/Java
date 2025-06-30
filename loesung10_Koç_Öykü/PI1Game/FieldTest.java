// Importiert assertEquals usw. sowie Test-Annotationen
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Diese Klasse definiert die Tests für die Klasse Field.
 * Es gibt keine Tests für Fälle, die nicht wirklich definiert
 * sind, wie z.B. was passiert, wenn die Nachbarschaft einer
 * Zelle überprüft wird, die selbst nicht im Feld liegt. Alle
 * Tests bis auf einen verwenden ein leicht abgeändertes Feld
 * aus Übungsblatt 5, das ja alle Nachbarschaftskombinationen
 * enthält.
 *
 * @author Thomas Röfer
 */
public class FieldTest
{
    /** Das Feld, das von fast allen Tests verwendet wird. */
    private Field field;

    /**
     * Erzeugen des Standardfeldes, das alle Kombinationen von
     * Nachbarschaften enthält. Die ersten beiden Zeilen sind
     * kürzer, so dass auch implizit getestet wird, ob die
     * Klasse damit umgehen kann.
     */
    @BeforeEach
    public void setUp()
    {
        field = new Field(new String[] {
            "O-O-O-O",
            "|   |",
            "O O-O-O O",
            "| | | | |",
            "O-O-O-O-O",
            "| | | | |",
            "O O-O-O O",
            "    |   |",
            "  O-O-O O"
        });
    }

    /**
     * Testet den Zugriff in Richtungen außerhalb des Feldes
     * auf einem 1x1-Feld. Dort sollte es keine Nachbarn geben.
     */
    @Test
    public void testOutside()
    {
        final Field field = new Field(new String[] {"O"});
        assertFalse(field.hasNeighbor(0, 0, 0));
        assertFalse(field.hasNeighbor(0, 0, 1));
        assertFalse(field.hasNeighbor(0, 0, 2));
        assertFalse(field.hasNeighbor(0, 0, 3));
    }

    /** Testen einer Zelle ohne Nachbarn. */
    @Test
    public void testNone()
    {
        assertFalse(field.hasNeighbor(0, 4, 0));
        assertFalse(field.hasNeighbor(0, 4, 1));
        assertFalse(field.hasNeighbor(0, 4, 2));
        assertFalse(field.hasNeighbor(0, 4, 3));
    }

    /**
     * Testen von Zellen mit einem Nachbarn. Der Nachbar kann
     * in vier Richtungen liegen, die nacheinander getestet
     * werden.
     */
    @Test
    public void testSingle()
    {
        assertTrue(field.hasNeighbor(1, 4, 0));
        assertFalse(field.hasNeighbor(1, 4, 1));
        assertFalse(field.hasNeighbor(1, 4, 2));
        assertFalse(field.hasNeighbor(1, 4, 3));

        assertFalse(field.hasNeighbor(4, 1, 0));
        assertTrue(field.hasNeighbor(4, 1, 1));
        assertFalse(field.hasNeighbor(4, 1, 2));
        assertFalse(field.hasNeighbor(4, 1, 3));

        assertFalse(field.hasNeighbor(3, 0, 0));
        assertFalse(field.hasNeighbor(3, 0, 1));
        assertTrue(field.hasNeighbor(3, 0, 2));
        assertFalse(field.hasNeighbor(3, 0, 3));

        assertFalse(field.hasNeighbor(0, 3, 0));
        assertFalse(field.hasNeighbor(0, 3, 1));
        assertFalse(field.hasNeighbor(0, 3, 2));
        assertTrue(field.hasNeighbor(0, 3, 3));
    }

    /**
     * Testen von Zellen mit gegenüber liegenden Nachbarn.
     * Diese können zwei Ausrichtungen haben, die nacheinander
     * getestet werden.
     */
    @Test
    public void testStraight()
    {
        assertTrue(field.hasNeighbor(1, 0, 0));
        assertFalse(field.hasNeighbor(1, 0, 1));
        assertTrue(field.hasNeighbor(1, 0, 2));
        assertFalse(field.hasNeighbor(1, 0, 3));

        assertFalse(field.hasNeighbor(0, 1, 0));
        assertTrue(field.hasNeighbor(0, 1, 1));
        assertFalse(field.hasNeighbor(0, 1, 2));
        assertTrue(field.hasNeighbor(0, 1, 3));
    }

    /**
     * Testen von Zellen mit zwei Nachbarn in L-Form. Das L kann
     * in vier Richtungen orientiert sein, die nacheinander getestet
     * werden.
     */
    @Test
    public void testL()
    {
        assertTrue(field.hasNeighbor(1, 1, 0));
        assertTrue(field.hasNeighbor(1, 1, 1));
        assertFalse(field.hasNeighbor(1, 1, 2));
        assertFalse(field.hasNeighbor(1, 1, 3));

        assertFalse(field.hasNeighbor(3, 1, 0));
        assertTrue(field.hasNeighbor(3, 1, 1));
        assertTrue(field.hasNeighbor(3, 1, 2));
        assertFalse(field.hasNeighbor(3, 1, 3));

        assertFalse(field.hasNeighbor(3, 3, 0));
        assertFalse(field.hasNeighbor(3, 3, 1));
        assertTrue(field.hasNeighbor(3, 3, 2));
        assertTrue(field.hasNeighbor(3, 3, 3));

        assertTrue(field.hasNeighbor(1, 3, 0));
        assertFalse(field.hasNeighbor(1, 3, 1));
        assertFalse(field.hasNeighbor(1, 3, 2));
        assertTrue(field.hasNeighbor(1, 3, 3));
    }

    /**
     * Testen von Zellen mit drei Nachbarn in T-Form. Das T kann
     * in vier Richtungen orientiert sein, die nacheinander getestet
     * werden.
     */
    @Test
    public void testT()
    {
        assertTrue(field.hasNeighbor(2, 0, 0));
        assertTrue(field.hasNeighbor(2, 0, 1));
        assertTrue(field.hasNeighbor(2, 0, 2));
        assertFalse(field.hasNeighbor(2, 0, 3));

        assertFalse(field.hasNeighbor(4, 2, 0));
        assertTrue(field.hasNeighbor(4, 2, 1));
        assertTrue(field.hasNeighbor(4, 2, 2));
        assertTrue(field.hasNeighbor(4, 2, 3));

        assertTrue(field.hasNeighbor(2, 4, 0));
        assertFalse(field.hasNeighbor(2, 4, 1));
        assertTrue(field.hasNeighbor(2, 4, 2));
        assertTrue(field.hasNeighbor(2, 4, 3));

        assertTrue(field.hasNeighbor(0, 2, 0));
        assertTrue(field.hasNeighbor(0, 2, 1));
        assertFalse(field.hasNeighbor(0, 2, 2));
        assertTrue(field.hasNeighbor(0, 2, 3));
    }

    /** Test einer Zelle mit vier Nachbarn. */
    @Test
    public void testX()
    {
        assertTrue(field.hasNeighbor(2, 2, 0));
        assertTrue(field.hasNeighbor(2, 2, 1));
        assertTrue(field.hasNeighbor(2, 2, 2));
        assertTrue(field.hasNeighbor(2, 2, 3));
    }

    /**
     * Testen, ob x und y vertauscht sind. Rechts unten ist
     * das Feld nicht spiegelsymmetrisch.
     */
    @Test
    public void testXY()
    {
        assertFalse(field.hasNeighbor(4, 4, 2));
        assertTrue(field.hasNeighbor(4, 4, 3));
    }
}
