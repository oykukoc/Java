package de.uni_bremen.pi2;

/**
 * Die Klasse repräsentiert einen Zug und seine Bewertung.
 * Für die Bewertung gilt normalerweise, je größer, desto besser.
 * @author Öykü Koç
 */
class Move
{
    /** Die Zeile, in der ein Stein platziert wird. -1, wenn undefiniert. */
    private final int row;

    /** Die Spalte, in der ein Stein platziert wird. -1, wenn undefiniert. */
    private final int column;

    /** Die Bewertung des Zugs. */
    private final int score;

    /**
     * Konstruktor.
     * @param row Die Zeile, in der ein Stein platziert wird.
     * @param column Die Spalte, in der ein Stein platziert wird.
     * @param score Die Bewertung des Zugs.
     */
    Move(final int row, final int column, final int score)
    {
        this.row = row;
        this.column = column;
        this.score = score;
    }

    /**
     * Konstruktor für die Bewertung einer Situation ohne
     * spezifischen Zug.
     * @param score Die Bewertung der Situation.
     */
    Move(final int score)
    {
        this(-1, -1, score);
    }

    /**
     * Liefert die Zeile, in der ein Stein platziert wird.
     * @return Die Nummer der Zeile.
     */
    int getRow()
    {
        assert row >= 0 : "Undefinierter Zug";
        return row;
    }

    /**
     * Liefert die Spalte, in der ein Stein platziert wird.
     * @return Die Nummer der Spalte.
     */
    int getColumn()
    {
        assert column >= 0 : "Undefinierter Zug";
        return column;
    }

    /**
     * Die Bewertung des Zugs.
     * @return Die Bewertung.
     */
    int getScore()
    {
        return score;
    }
}
