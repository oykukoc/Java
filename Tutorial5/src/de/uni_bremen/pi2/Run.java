package de.uni_bremen.pi2;

/**
 * Repräsentiert einen Lauf in der Union-Find-Datenstruktur.
 */
public class Run {

    /**
     * Die Start-x-Koordinate des Laufs.
     */
    private final int xStart;

    /**
     * Die End-x-Koordinate des Laufs.
     */
    private final int xEnd;

    /**
     * Die y-Koordinate des Laufs.
     */
    private final int y;

    /**
     * Der Oberlauf dieses Laufs.
     */
    private Run root;

    /**
     * Erstellt einen neuen Lauf mit den angegebenen x-Start-, x-End- und y-Koordinaten.
     *
     * @param xStart die Start-x-Koordinate des Laufs
     * @param xEnd   die End-x-Koordinate des Laufs
     * @param y      die y-Koordinate des Laufs
     */
    public Run(int xStart, int xEnd, int y) {
        this.xStart = xStart;
        this.xEnd = xEnd;
        this.y = y;
        this.root = this;
    }

    /**
     * Gibt die Start-x-Koordinate des Laufs zurück.
     *
     * @return die Start-x-Koordinate des Laufs
     */
    public int getXStart() {
        return xStart;
    }

    /**
     * Gibt die End-x-Koordinate des Laufs zurück.
     *
     * @return die End-x-Koordinate des Laufs
     */
    public int getXEnd() {
        return xEnd;
    }

    /**
     * Gibt die y-Koordinate des Laufs zurück.
     *
     * @return die y-Koordinate des Laufs
     */
    public int getY() {
        return y;
    }

    /**
     * Gibt den Wurzel-Lauf dieses Laufs zurück.
     *
     * @return der Wurzel-Lauf dieses Laufs
     */
    public Run getRoot() {
        if (root != this) {
            root = root.getRoot(); // Pfadkomprimierung
        }
        return root;
    }

    /**
     * Setzt den Oberlauf dieses Laufs.
     *
     * @param root der neue Oberlauf
     */
    public void setRoot(Run root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "(" + "Lauf: " + xStart + "-" + xEnd + "-" + y + " --> Wurzel: " + root.xStart + "-" + root.xEnd + "-" + root.y + ")";
    }

    /**
     * Gibt die Länge des Laufs zurück.
     *
     * @return die Länge des Laufs
     */
    public int getLength() {
        return xEnd - xStart;
    }

    private int color;
    /**
     * Gibt die Farbe des Laufs zurück.
     *
     * @return die Farbe des Laufs
     */
    public int getColor() {
        return color;
    }

    /**
     * Setzt die Farbe des Laufs.
     *
     * @param color die neue Farbe des Laufs
     */
    public void setColor(int color) {
        this.color = color;
    }
}
