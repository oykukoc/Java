package de.uni_bremen.pi2;

import java.util.ArrayList;
import java.util.List;

import static de.uni_bremen.pi2.Player.*;
import static de.uni_bremen.pi2.Result.*;

/**
 * @Author Öykü Koç
 **/
class FourInARow {

    /**
     * Spielfeld.
     */
    private final Player[][] field;
    private final int depth;

    /**
     * Konstruktor.
     * @param field Spielfeld. Muss quadratisch sein.
     * @param depth Maximale Suchtiefe.
     */
    FourInARow(final Player[][] field, int depth) {
        this.field = field;
        this.depth = depth;
    }

    /**
     * Führt den Zug des Menschen aus.
     * Diese Methode setzt das Symbol des menschlichen Spielers an die angegebene Position im Spielfeld
     * und überprüft danach den Spielstatus, um festzustellen, ob der menschliche Spieler gewonnen hat,
     * das Spiel unentschieden ist oder das Spiel weitergeht.
     *
     * @param row Zeile des Zuges.
     * @param column Spalte des Zuges.
     * @return Das Ergebnis des Spiels nach dem Zug des Menschen.
     */
    Result humanMove(final int row, final int column) {
        field[row][column] = HUMAN;
        return getGameResult(HUMAN);
    }

    /**
     * Führt den Zug des Computers aus.
     * Diese Methode verwendet den NegaMax-Algorithmus mit Alpha-Beta-Pruning, um den besten möglichen Zug für den Computer
     * zu berechnen. Nachdem der Zug berechnet wurde, wird das Symbol des Computers an die berechnete Position gesetzt und
     * der Spielstatus wird überprüft.
     *
     * @return Das Ergebnis des Spiels nach dem Zug des Computers.
     */
    Result computerMove() {
        Move bestMove = negaMaxAlphaBeta(COMPUTER, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        field[bestMove.getRow()][bestMove.getColumn()] = COMPUTER;
        return getGameResult(COMPUTER);
    }

    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder();
        String separator = "";
        for (final Player[] row : field) {
            string.append(separator);
            separator = "\n";
            for (final Player player : row) {
                string.append("[").append(player).append("]");
            }
        }
        return string.toString();
    }

    /**
     * Bewertet den Spielzustand.
     * Diese Methode berechnet eine Bewertung für den aktuellen Spielzustand aus Sicht des angegebenen Spielers.
     * Wenn der angegebene Spieler gewonnen hat, wird eine positive Bewertung zurückgegeben, andernfalls null.
     * Die Bewertung basiert auf der Anzahl der verbleibenden möglichen Züge für den menschlichen Spieler.
     *
     * @param player Der Spieler, für den der Spielzustand bewertet wird.
     * @return Die Bewertung des aktuellen Spielzustands.
     */
    public int evaluate(Player player) {
        boolean wonStatus = hasWon(player);
        if (wonStatus)
            return player == HUMAN ? Integer.MAX_VALUE : -Integer.MAX_VALUE;
        else
            return 0;
    }

    /**
     * Macht einen Zug auf dem Spielfeld.
     * Diese Methode setzt das Symbol des angegebenen Spielers an die in der Move-Instanz
     * angegebene Position im Spielfeld.
     *
     * @param move Der zu machende Zug.
     * @param player Der Spieler, der den Zug macht.
     */
    public void makeMove(Move move, Player player) {
        field[move.getRow()][move.getColumn()] = player;
    }

    /**
     * Nimmt einen Zug zurück.
     * Diese Methode entfernt das Symbol an der in der Move-Instanz angegebenen Position im Spielfeld
     * und setzt es auf leer (EMPTY).
     *
     * @param move Der Zug, der zurückgenommen werden soll.
     */
    public void undoMove(Move move) {
        field[move.getRow()][move.getColumn()] = EMPTY;
    }

    /**
     * Gibt den anderen Spieler zurück.
     * Diese Methode gibt den anderen Spieler zurück. Wenn der aktuelle Spieler der Mensch ist, wird der Computer zurückgegeben,
     * und umgekehrt.
     *
     * @param player Der aktuelle Spieler.
     * @return Der andere Spieler.
     */
    public Player other(Player player) {
        return (player == HUMAN) ? COMPUTER : HUMAN;
    }

    /**
     * Berechnet eine gute Punktzahl basierend auf der Tiefe.
     * Diese Methode berechnet eine Punktzahl basierend auf der aktuellen Tiefe der Suche.
     * Dies kann verwendet werden, um den Wert von Zügen zu erhöhen, die weiter in die Zukunft blicken.
     *
     * @param height Die aktuelle Tiefe.
     * @return Die berechnete Punktzahl.
     */
    public int goodScore(int height) {
        return height * 10;
    }

    /**
     * Gibt eine Liste der möglichen Züge zurück.
     * Diese Methode durchsucht das Spielfeld nach leeren Positionen (EMPTY) und erstellt eine Liste aller möglichen Züge
     * für den angegebenen Spieler.
     *
     * @param player Der Spieler, für den die möglichen Züge ermittelt werden.
     * @return Eine Liste der möglichen Züge.
     */
    public List<Move> possibleMoves(Player player) {
        List<Move> emptyMoveList = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == player) {
                    emptyMoveList.add(new Move(i, j, 0));
                }
            }
        }
        return emptyMoveList;
    }

    /**
     * Überprüft, ob ein Spieler gewonnen hat.
     * Diese Methode überprüft, ob der angegebene Spieler gewonnen hat, indem sie das Spielfeld horizontal,
     * vertikal und diagonal nach einer Viererreihe des Symbols des Spielers durchsucht.
     *
     * @param player Der Spieler, der überprüft wird.
     * @return Wahr, wenn der Spieler gewonnen hat, sonst falsch.
     */
    private boolean hasWon(Player player) {
        Player symbol = (player == Player.HUMAN) ? HUMAN : COMPUTER;
        String leftSymbol = "";
        String rightSymbol = "";

        String containsSymbol = player == HUMAN
                ? HUMAN.toString() + HUMAN.toString() + HUMAN.toString() + HUMAN.toString()
                : COMPUTER.toString() + COMPUTER.toString() + COMPUTER.toString() + COMPUTER.toString();

        // Horizontal und vertikal prüfen
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field.length; col++) {
                leftSymbol += field[row][col];
                rightSymbol += field[col][row];
            }
            if (leftSymbol.contains(containsSymbol) || rightSymbol.contains(containsSymbol))
                return true;
            leftSymbol = "";
            rightSymbol = "";
        }

        // Diagonale Überprüfung (von links nach rechts und von rechts nach links) - Erste Hälfte
        int curRow = 0;
        int curCol = 0;
        int lastCol = field.length - 1;

        for (int col = 0; col < field.length; col++) {
            leftSymbol = "";
            rightSymbol = "";

            curRow = 0;
            curCol = col;

            while (curRow < field.length - col) {
                leftSymbol += field[curRow][curCol].toString();
                rightSymbol += field[curRow][lastCol - curCol].toString();

                curRow++;
                curCol++;
            }
            if (leftSymbol.contains(containsSymbol) || rightSymbol.contains(containsSymbol))
                return true;
            if (leftSymbol.length() <= 4 && rightSymbol.length() <= 4)
                break;
        }

        for (int row = 1; row < field.length; row++) {
            leftSymbol = "";
            rightSymbol = "";

            curRow = row;
            curCol = 0;

            while (curCol < field.length - row) {
                leftSymbol += field[curRow][curCol].toString();
                rightSymbol += field[curRow][lastCol - curCol].toString();

                curRow++;
                curCol++;
            }
            if (leftSymbol.contains(containsSymbol) || rightSymbol.contains(containsSymbol))
                return true;
            if (leftSymbol.length() <= 4 && rightSymbol.length() <= 4)
                break;
        }
        return false;
    }

    /**
     * Gibt das Ergebnis des Spiels zurück.
     * Diese Methode überprüft den aktuellen Spielstatus, um festzustellen, ob ein Spieler gewonnen hat,
     * das Spiel unentschieden ist oder das Spiel weitergeht.
     *
     * @param player Der Spieler, der überprüft wird.
     * @return Das Ergebnis des Spiels.
     */
    public Result getGameResult(Player player) {
        if (hasWon(player))
            return player == HUMAN ? HUMAN_WON : COMPUTER_WON;
        if (possibleMoves(EMPTY).size() == 0)
            return DRAW;
        return CONTINUE;
    }

    /**
     * Implementiert den NegaMax-Algorithmus.
     * Diese Methode implementiert den NegaMax-Algorithmus, um den besten Zug für den angegebenen Spieler zu finden.
     * Der Algorithmus durchsucht alle möglichen Züge bis zu einer bestimmten Tiefe und bewertet die Züge,
     * um den Zug mit der höchsten Bewertung zurückzugeben.
     *
     * @param player Der Spieler, der den Zug macht.
     * @param height Die aktuelle Tiefe.
     * @return Der beste Zug.
     */
    public Move negaMax(final Player player, int height) {
        if (height == 0) {
            return new Move(evaluate(player));
        }

        Move bestMove = new Move(-Integer.MAX_VALUE);

        for (final Move move : possibleMoves(EMPTY)) {
            makeMove(move, player);

            final int score = hasWon(player)
                    ? goodScore(height)
                    : -negaMax(other(player), height - 1).getScore();

            if (score > bestMove.getScore()) {
                bestMove = new Move(move.getRow(), move.getColumn(), score);
            }

            undoMove(move);
        }
        return bestMove;
    }

    /**
     * Implementiert den NegaMax-Algorithmus mit Alpha-Beta-Schnitt.
     * Diese Methode implementiert den NegaMax-Algorithmus mit Alpha-Beta-Pruning, um den besten Zug für den angegebenen Spieler
     * zu finden. Der Algorithmus durchsucht alle möglichen Züge bis zu einer bestimmten Tiefe und verwendet Alpha-Beta-Pruning,
     * um die Anzahl der zu bewertenden Züge zu reduzieren und den effizientesten Zug zu finden.
     *
     * @param player Der Spieler, der den Zug macht.
     * @param height Die aktuelle Tiefe.
     * @param alpha Der Alpha-Wert für das Pruning.
     * @param beta Der Beta-Wert für das Pruning.
     * @return Der beste Zug.
     */
    public Move negaMaxAlphaBeta(final Player player, int height, int alpha, int beta) {
        if (height == 0)
            return new Move(evaluate(player));

        Move bestMove = new Move(-Integer.MAX_VALUE);

        for (final Move move : possibleMoves(EMPTY)) {
            makeMove(move, player);

            final int score = hasWon(player)
                    ? goodScore(height)
                    : -negaMaxAlphaBeta(other(player), height - 1, -beta, -alpha).getScore();

            if (score > bestMove.getScore()) {
                bestMove = new Move(move.getRow(), move.getColumn(), score);
            }

            undoMove(move);

            // Alpha-Beta-Pruning
            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
            }
        }
        return bestMove;
    }
}
