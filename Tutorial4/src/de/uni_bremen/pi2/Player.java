package de.uni_bremen.pi2;

/**
 * Dieser Aufzählungstyp beschreibt, ob und wer einen Stein auf einem
 * Feld des Spielfeldes platziert hat.
 */
enum Player
{
    /** Das Feld ist leer. */
    EMPTY("."),

    /** Die menschliche Spieler*in hat einen Stein platziert. */
    HUMAN("X"),

    /** Der Computer hat einen Stein platziert. */
    COMPUTER("O");

    /** Die Darstellung des Steins auf dem Spielfeld. */
    private final String symbol;

    /**
     * Privater Konstruktor.
     * @param symbol Die Darstellung des Steins auf dem Spielfeld.
     */
    Player(final String symbol)
    {
        this.symbol = symbol;
    }

    /**
     * Liefert die Darstellung des Steins auf dem Spielfeld.
     * @return Die Darstellung.
     */
    @Override
    public String toString()
    {
        return symbol;
    }
}
