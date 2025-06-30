package de.uni_bremen.pi2;

import org.junit.jupiter.api.Test;

import static de.uni_bremen.pi2.Player.*;
import static de.uni_bremen.pi2.Result.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author Altug Uyanik
 * */
public class FourInARowTest {

    /**
     * Erzeuge ein Spielfeld aus einem String. Zeilen werden durch '\n' getrennt,
     * wobei am Ende kein '\n' steht. '.' repräsentiert leeres Feld, 'X' Steine
     * der menschlichen Spieler*in und 'O' Steine des Computers.
     *
     * @param string Eine Zeichenkette in demselben Format, in dem auch die
     *               toString-Methode des Spiels das Spielfeld darstellt.
     * @return Ein Spielfeld mit den zur Eingabe passenden Belegungen.
     */
    private Player[][] asField(final String string) {
        String[] rows = string.split("\n");
        Player[][] field = new Player[rows.length][rows[0].length()];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                switch (rows[i].charAt(j)) {
                    case 'X':
                        field[i][j] = HUMAN;
                        break;
                    case 'O':
                        field[i][j] = COMPUTER;
                        break;
                    default:
                        field[i][j] = EMPTY;
                        break;
                }
            }
        }
        return field;
    }

    /**
     * Testet den Zug des menschlichen Spielers.
     * Überprüft, ob das Feld nach dem Zug des Menschen korrekt aktualisiert wird.
     */
    @Test
    void testHumanMove() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        game.humanMove(0, 0);
        assertEquals(HUMAN, field[0][0]);
    }

    /**
     * Testet den Zug des Computers.
     * Überprüft, ob das Spiel nach dem Zug des Computers korrekt fortgesetzt wird.
     */
    @Test
    void testComputerMove() {
        Player[][] field = asField(
                "X...\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(CONTINUE, game.computerMove());
    }

    /**
     * Testet, ob der Mensch horizontal gewinnt.
     * Überprüft, ob der Mensch das Spiel gewinnt, wenn er vier Steine horizontal in einer Reihe hat.
     */
    @Test
    void testHumanWinHorizontal() {
        Player[][] field = asField(
                "OOO.\n" +
                        "....\n" +
                        "XXX.\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(HUMAN_WON, game.humanMove(2, 3));
    }

    /**
     * Testet, ob der Mensch vertikal gewinnt.
     * Überprüft, ob der Mensch das Spiel gewinnt, wenn er vier Steine vertikal in einer Reihe hat.
     */
    @Test
    void testHumanWinVertical() {
        Player[][] field = asField(
                "OOOX\n" +
                        "...X\n" +
                        "OXOX\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(HUMAN_WON, game.humanMove(3, 3));
    }

    /**
     * Testet, ob der Mensch diagonal gewinnt.
     * Überprüft, ob der Mensch das Spiel gewinnt, wenn er vier Steine diagonal in einer Reihe hat.
     */
    @Test
    void testHumanWinDiagonal() {
        Player[][] field = asField(
                "OOOX\n" +
                        "..X.\n" +
                        "OXO.\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(HUMAN_WON, game.humanMove(3,0 ));
    }

    /**
     * Testet, ob der Computer horizontal gewinnt.
     * Überprüft, ob der Computer das Spiel gewinnt, wenn er vier Steine horizontal in einer Reihe hat.
     */
    @Test
    void testComputerWinHorizontal() {
        Player[][] field = asField(
                "XX..\n" +
                        "X..X\n" +
                        "OOOO\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(COMPUTER_WON, game.computerMove());
    }

    /**
     * Testet, ob der Computer vertikal gewinnt.
     * Überprüft, ob der Computer das Spiel gewinnt, wenn er vier Steine vertikal in einer Reihe hat.
     */
    @Test
    void testComputerWinVertical() {
        Player[][] field = asField(
                "OX..\n" +
                        "O..X\n" +
                        "OXOX\n" +
                        "O..."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(COMPUTER_WON, game.computerMove());
    }

    /**
     * Testet, ob der Computer diagonal gewinnt.
     * Überprüft, ob der Computer das Spiel gewinnt, wenn er vier Steine diagonal in einer Reihe hat.
     */
    @Test
    void testComputerWinDiagonal() {
        Player[][] field = asField(
                "XX.O\n" +
                        "X.OX\n" +
                        ".O..\n" +
                        "O..."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(COMPUTER_WON, game.computerMove());
    }

    /**
     * Testet, ob das Spiel unentschieden endet.
     * Überprüft, ob das Spiel korrekt als Unentschieden erkannt wird.
     */
    @Test
    void testDraw() {
        Player[][] field = asField(
                "XXXO\n" +
                        "OOOX\n" +
                        "XXXO\n" +
                        "OOOX"
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(DRAW, game.getGameResult(HUMAN));
    }

    /**
     * Testet die toString-Methode.
     * Überprüft, ob die toString-Methode das Spielfeld korrekt darstellt.
     */
    @Test
    void testToString() {
        Player[][] field = asField(
                "XOXO\n" +
                        "OXOX\n" +
                        "XOXO\n" +
                        ".XO."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals("[X][O][X][O]\n[O][X][O][X]\n[X][O][X][O]\n[.][X][O][.]", game.toString());
    }

    /**
     * Testet die Bewertungsfunktion.
     * Überprüft, ob die evaluate-Methode den korrekten Wert für den gegebenen Spielzustand zurückgibt.
     */
    @Test
    void testEvaluate() {
        Player[][] field = asField(
                "OOO.\n" +
                        "....\n" +
                        "XXXX\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(Integer.MAX_VALUE, game.evaluate(HUMAN));
    }

    /**
     * Testet die Methode für mögliche Züge.
     * Überprüft, ob die possibleMoves-Methode die korrekte Anzahl von möglichen Zügen zurückgibt.
     */
    @Test
    void testPossibleMoves() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(16, game.possibleMoves(EMPTY).size());
    }

    /**
     * Testet das Ausführen eines Zugs.
     * Überprüft, ob die makeMove-Methode den Zug korrekt ausführt.
     */
    @Test
    void testMakeMove() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        Move move = new Move(0, 0, 0);
        game.makeMove(move, HUMAN);
        assertEquals(HUMAN, field[0][0]);
    }

    /**
     * Testet das Zurücknehmen eines Zugs.
     * Überprüft, ob die undoMove-Methode den Zug korrekt zurücknimmt.
     */
    @Test
    void testUndoMove() {
        Player[][] field = asField(
                "X...\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        Move move = new Move(0, 0, 0);
        game.undoMove(move);
        assertEquals(EMPTY, field[0][0]);
    }

    /**
     * Testet die Methode other für den Computer.
     * Überprüft, ob die other-Methode den korrekten gegnerischen Spieler zurückgibt.
     */
    @Test
    void testOtherComputer() {
        FourInARow game = new FourInARow(new Player[4][4], 4);
        assertEquals(COMPUTER, game.other(HUMAN));
    }

    /**
     * Testet die Methode other für den Menschen.
     * Überprüft, ob die other-Methode den korrekten gegnerischen Spieler zurückgibt.
     */
    @Test
    void testOtherHuman() {
        FourInARow game = new FourInARow(new Player[6][6], 6);
        assertEquals(HUMAN, game.other(COMPUTER));
    }

    /**
     * Testet die goodScore-Methode.
     * Überprüft, ob die goodScore-Methode den korrekten Wert für die gegebene Höhe zurückgibt.
     */
    @Test
    void testGoodScore() {
        FourInARow game = new FourInARow(new Player[6][6], 6);
        assertEquals(60, game.goodScore(6));
        assertEquals(50, game.goodScore(5));
        assertEquals(40, game.goodScore(4));
        assertEquals(30, game.goodScore(3));
        assertEquals(20, game.goodScore(2));
        assertEquals(10, game.goodScore(1));
    }

    /**
     * Testet das Spielergebnis, wenn der Mensch gewinnt.
     * Überprüft, ob getGameResult den korrekten Wert zurückgibt, wenn der Mensch gewinnt.
     */
    @Test
    void testGetGameResultHumanWon() {
        Player[][] field = asField(
                "XXXX\n" +
                        "OOOX\n" +
                        "OOXX\n" +
                        "XXOO"
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(HUMAN_WON, game.getGameResult(HUMAN));
    }

    /**
     * Testet das Spielergebnis, wenn der Computer gewinnt.
     * Überprüft, ob getGameResult den korrekten Wert zurückgibt, wenn der Computer gewinnt.
     */
    @Test
    void testGetGameResultComputerWon() {
        Player[][] field = asField(
                "OOOO\n" +
                        "XXXO\n" +
                        "XXOO\n" +
                        "OOXX"
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(COMPUTER_WON, game.getGameResult(COMPUTER));
    }

    /**
     * Testet das Spielergebnis bei einem Unentschieden.
     * Überprüft, ob getGameResult den korrekten Wert für ein Unentschieden zurückgibt.
     */
    @Test
    void testGetGameResultDraw() {
        Player[][] field = asField(
                "XOXO\n" +
                        "XXXO\n" +
                        "XXOO\n" +
                        "OOXX"
        );
        FourInARow game = new FourInARow(field, 4);
        assertEquals(DRAW, game.getGameResult(HUMAN));
    }

    /**
     * Testet den NegaMax-Algorithmus.
     * Überprüft, ob der NegaMax-Algorithmus den besten Zug korrekt berechnet.
     */
    @Test
    void testNegaMaxAlphaBeta() {
        Player[][] field = asField(
                "....\n" +
                        "....\n" +
                        "....\n" +
                        "...."
        );
        FourInARow game = new FourInARow(field, 4);
        Move bestMove = game.negaMax(COMPUTER, 4);
        assertNotNull(bestMove);
    }
}
