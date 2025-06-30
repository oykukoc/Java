package de.uni_bremen.pi2;

// INVALID, VALID und SOLVED können direkt verwendet werden.
import static de.uni_bremen.pi2.InsectHotel.Result.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
/**
 *Author Altug Uyanik
 */
public class InsectHotelTest
{
    /**
     * Ein komplexer Test aus dem ursprünglichen Puzzle.
     * Ist standardmäßig deaktiviert.
     */
    @Test
    public void testComplex()
    {
        final String[] puzzle = {
                "X       ",
                " OO     ",
                "   X   O",
                "O       ",
                "  XO  X ",
                "X X     ",
                "     X  ",
                "     X  "
        };
        final String[] solution = {
                "XXOOXOOX",
                "XOOXOOXX",
                "OOXXOXXO",
                "OXOOXXOX",
                "XOXOXOXO",
                "XOXXOOXO",
                "OXOXOXOX",
                "OXXOXXOO"
        };
        assertArrayEquals(solution, InsectHotel.solve(puzzle));
    }

    /**
     * Test für die fill-Methode, wenn die Hoteleinträge leer sind.
     */
    @Test
    public void testFill_EmptyEntries() {
        final String[] hotel = {"XO  ", "OX X", "X O ", "O X "};
        final String[] result = InsectHotel.fill(hotel, "");
        assertArrayEquals(hotel, result);
    }

    /**
     * Test für die fill-Methode, wenn die Hoteleinträge voll sind.
     */
    @Test
    public void testFill_FullEntries() {
        final String[] hotel = {"XO  ", "OX X", "X O ", "O X "};
        final String[] result = InsectHotel.fill(hotel, "XOOOXXO");
        assertArrayEquals(hotel, result);
    }

    /**
     * Test für die check-Methode zur Validierung einer gültigen Hotelkonfiguration.
     */
    @Test
    public void testCheckValid() {
        String[] hotel = {"XO  ", "OX X", "X OO", "OOX "};
        assertEquals(InsectHotel.Result.VALID, InsectHotel.check(hotel));
    }

    /**
     * Test auf eine check-Methode zur Erkennung einer ungültigen Hotelkonfiguration.
     */
    @Test
    public void testCheckInvalid() {
        String[] hotel = {"XXXO", "XOXO  ", "OXOX", "XXOO"};
        assertEquals(InsectHotel.Result.INVALID, InsectHotel.check(hotel));
    }

    /**
     * Test auf eine check-Methode zur Erkennung einer gelösten Hotelkonfiguration.
     */
    @Test
    public void testCheckSolved() {
        String[] hotel = {"XOXO", "OXOX", "XXOO", "OOXX"};
        assertEquals(InsectHotel.Result.SOLVED, InsectHotel.check(hotel));
    }

    /**
     * Prüfung der check-Methode, wenn das Hotel vollständig mit „X“ gefüllt ist.
     */
    @Test
    public void testCheck_AllX() {
        final String[] hotel = {"XXXX", "XXXX", "XXXX", "XXXX"};
        assertEquals(INVALID, InsectHotel.check(hotel));
    }

    /**
     * Doppelter Test für die check-Methode mit einer gültigen Hotelkonfiguration.
     */
    @Test
    public void testCheck_ValidHotel() {
        final String[] hotel = {"XO  ", "OX X", "X OO", "OOX "};
        assertEquals(InsectHotel.Result.VALID, InsectHotel.check(hotel));
    }

    /**
     * Test für die check-Methode, wenn das Hotel ein ungültiges Muster hat.
     */
    @Test
    public void testCheck_InvalidPattern() {
        final String[] hotel = {"XXXO", "OOOX", "XOXX", "OXOO"};
        assertEquals(INVALID, InsectHotel.check(hotel));
    }

    /**
     * Test für die check-Methode, wenn das Hotel null ist.
     */
    @Test
    public void testCheck_NullHotel() {
        assertThrowsExactly(NullPointerException.class, () -> InsectHotel.check(null));
    }

    /**
     * Test für die check-Methode zur Überprüfung einer gelösten Hotelkonfiguration, die von der solve-Methode zurückgegeben wird.
     */
    @Test
    public void testCheck_SolvedHotel() {
        final String[] hotel = {"XO  ", "OX X", "X OO", "OOX "};
        final String[] solvedHotel = InsectHotel.solve(hotel);
        assertEquals(SOLVED, InsectHotel.check(solvedHotel));
    }

    /**
     * Test für die check-Methode, wenn das Hotelarray leer ist.
     */
    @Test
    public void testCheck_EmptyHotel() {
        final String[] hotel = {};
        assertThrowsExactly(IllegalArgumentException.class, () -> InsectHotel.solve(hotel));
    }

    /**
     * Test auf solve-Methode, wenn die Hotelkonfiguration ungültig ist.
     */
    @Test
    public void testSolve_InvalidHotel() {
        final String[] hotel = {"XXX ", "OOO ", "X XX", "OOO "};
        assertNull(InsectHotel.solve(hotel));
    }

    /**
     * Test für die solve-Methode zur Lösung einer gültigen Hotelkonfiguration.
     */
    @Test
    public void testSolve_ValidHotel() {
        final String[] hotel = {"XO  ", "OX X", "X OO", "OOX "};
        final String[] solution = {"XOXO", "OXOX", "XXOO", "OOXX"};
        assertArrayEquals(solution, InsectHotel.solve(hotel));
    }

    /**
     * Test für die Methode solve, wenn das Hotel null ist.
     */
    @Test
    public void testSolve_NullHotel() {
        assertThrowsExactly(IllegalArgumentException.class, () -> InsectHotel.solve(null));
    }

    /**
     * Test für die Methode solve, wenn das Hotel-Array leer ist.
     */
    @Test
    public void testSolve_EmptyHotel() {
        final String[] hotel = {" "};
        assertThrowsExactly(IllegalArgumentException.class, () -> InsectHotel.solve(hotel));
    }

    /**
     * Test für die solve-Methode, wenn die Hotelkonfiguration unmöglich zu lösen ist.
     */
    @Test
    public void testSolve_ImpossibleHotel() {
        final String[] hotel = {"XX00", "XXOO", "XXOO", "XXOO"};
        assertNull(InsectHotel.solve(hotel));
    }
}
