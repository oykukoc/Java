package de.uni_bremen.pi2;

/**
 * Dieser Aufzählungstyp beschreibt die möglichen Ergebnisse eines Zugs.
 * @author Öykü Koç
 */
enum Result
{
    /** Spiel geht noch weiter. */
    CONTINUE(""),

    /** Mensch hat gewonnen. */
    HUMAN_WON("Gewonnen"),

    /** Computer hat gewonnen. */
    COMPUTER_WON("Verloren"),

    /** Niemand hat gewonnen, aber das Spielfeld ist voll. */
    DRAW("Unentschieden");

    /** Der Text, der von toString zurückgegeben wird. */
    private final String description;

    /**
     * Privater Konstruktor.
     * @param description Der Text, der von toString zurückgegeben wird.
     */
    Result(final String description)
    {
        this.description = description;
    }

    /**
     * Liefert die Beschreibung zu einem Ergebnis.
     * @return Die Beschreibung.
     */
    @Override
    public String toString()
    {
        return description;
    }
}
