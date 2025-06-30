package de.uni_bremen.pi2;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Die Union-Find-Datenstruktur zur Verarbeitung von Run-Segmenten in einem Bild.
 */
public class UnionFind {

    private final ArrayList<Run> runs = new ArrayList<>();

    /**
     * Gibt die Liste der Runs zurück.
     *
     * @return die Liste der Runs
     */
    public ArrayList<Run> getRuns() {
        return runs;
    }

    /**
     * Konstruktor, der Run-Segmente aus einem Bild erstellt und sie miteinander vereint.
     * Erstellt zunächst horizontale Segmente, indem es weiße Pixel im Bild verfolgt und
     * anschließend werden benachbarte Segmente miteinander vereint.
     *
     * @param image das zu verarbeitende Bild
     * @param white der Farbwert für Weiß
     */
    public UnionFind(BufferedImage image, int white) {
        createRunSegments(image, white);
        unionRunSegments(this.runs);
    }

    /**
     * Fügt einen Run zur Union-Find-Datenstruktur hinzu.
     *
     * @param run der hinzuzufügende Run
     */
    public void insert(Run run) {
        this.runs.add(run);
    }

    /**
     * Gibt den Run mit der kleinsten y-Koordinate zurück.
     * Diese Methode durchläuft alle gespeicherten Runs und findet denjenigen mit der kleinsten y-Koordinate.
     *
     * @return der Run mit der kleinsten y-Koordinate
     */
    public Run accessMin() {
        Run minRun = runs.get(0);
        for (Run run : runs) {
            if (run.getY() < minRun.getY()) {
                minRun = run;
            }
        }
        return minRun;
    }

    /**
     * Überprüft, ob ein Run in der Union-Find-Datenstruktur enthalten ist.
     * Diese Methode durchsucht die Liste der gespeicherten Runs, um zu prüfen, ob der gegebene Run vorhanden ist.
     *
     * @param run der zu überprüfende Run
     * @return true, wenn der Run enthalten ist, andernfalls false
     */
    public boolean contains(Run run) {
        return runs.contains(run);
    }

    /**
     * Findet den Nachfolger eines Runs in der Union-Find-Datenstruktur.
     * Diese Methode sucht in der Liste der Runs nach einem Run, der eine höhere y-Koordinate hat als der gegebene Run.
     *
     * @param run der Run, dessen Nachfolger gefunden werden soll
     * @return der Nachfolger des Runs, oder null wenn kein Nachfolger gefunden wird
     */
    public Run successor(Run run) {
        for (Run nextRun : runs) {
            if (nextRun.getY() > run.getY()) {
                return nextRun;
            }
        }
        return null;
    }

    /**
     * Gibt den k-ten Run in der Union-Find-Datenstruktur zurück.
     * Diese Methode gibt den Run am Index k in der Liste der gespeicherten Runs zurück.
     *
     * @param k der Index des zurückzugebenden Runs
     * @return der k-te Run
     * @throws IndexOutOfBoundsException wenn der Index außerhalb des Bereichs liegt
     */
    public Run kthElement(int k) {
        if (k < 0 || k >= runs.size()) {
            throw new IndexOutOfBoundsException();
        }
        return runs.get(k);
    }

    /**
     * Erstellt horizontale Segmente, indem es weiße Pixel im Bild verfolgt.
     * Diese Methode durchläuft das Bild zeilenweise und erstellt Run-Objekte für zusammenhängende weiße Pixel.
     *
     * @param image das zu verarbeitende Bild
     * @param WHITE der Farbwert für Weiß
     */
    void createRunSegments(BufferedImage image, int WHITE) {
        for (int y = 0; y < image.getHeight(); y++) {
            int xStart = -1;
            int xEnd = -1;
            boolean isContinue = false;

            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) == WHITE && !isContinue) {
                    xStart = x;
                    isContinue = true;
                } else if (image.getRGB(x, y) != WHITE && isContinue) {
                    xEnd = x;
                    isContinue = false;
                    insert(new Run(xStart, xEnd, y));
                }
            }
            if (isContinue)
                insert(new Run(xStart, image.getWidth(), y));
        }
    }

    /**
     * Vereint benachbarte Segmente in der gegebenen Liste von Run-Objekten.
     * Diese Methode überprüft, ob zwei Segmente benachbart sind (d.h. ihre y-Koordinaten sind benachbart und ihre x-Koordinaten überschneiden sich),
     * und führt eine Union-Operation durch, um diese Segmente zu vereinen.
     *
     * @param runs die Liste der zu vereinigenden Run-Objekte
     */
    public void unionRunSegments(ArrayList<Run> runs) {
        int j = 0;
        int i = 1;
        while (j < runs.size()) {
            if (i < runs.size() && isNeighbors(runs.get(j), runs.get(i))) {
                union(runs.get(j), runs.get(i));
                i++;
            } else if (i < runs.size() && (runs.get(j).getY() <= runs.get(i).getY() || runs.get(j).getXEnd() < runs.get(i).getXEnd())) {
                i++;
            } else {
                j++;
                i = j;
            }
        }
    }

    /**
     * Überprüft, ob zwei Segmente Nachbarn sind.
     * Diese Methode prüft, ob zwei Segmente benachbarte y-Koordinaten haben und ob sich ihre x-Koordinaten überschneiden.
     *
     * @param runi das erste Segment
     * @param runj das zweite Segment
     * @return true, wenn die beiden Segmente Nachbarn sind, andernfalls false
     */
    public boolean isNeighbors(Run runj, Run runi) {
        return runj.getY() + 1 == runi.getY() && runj.getXStart() < runi.getXEnd() && runi.getXStart() < runj.getXEnd();
    }

    /**
     * Vereint zwei Segmente.
     * Diese Methode führt die Union-Operation durch, um zwei Segmente in der Union-Find-Datenstruktur zu vereinen.
     * Dabei wird das Wurzel-Element des einen Segments als Wurzel des anderen Segments gesetzt.
     *
     * @param runi das erste Segment
     * @param runj das zweite Segment
     */
    public void union(Run runi, Run runj) {
        Run rooti = runi.getRoot();
        Run rootj = runj.getRoot();

        if (rooti == rootj) {
            return;
        }

        if (rooti.getY() <= rootj.getY()) {
            rootj.setRoot(rooti);
        } else {
            rooti.setRoot(rootj);
        }
    }

    /**
     * Zeichnet ein Segment auf ein Bild.
     * Diese Methode zeichnet die x-Koordinaten des gegebenen Segments auf der angegebenen y-Koordinate in der angegebenen Farbe auf das Bild.
     *
     * @param run       das zu zeichnende Segment
     * @param segmented das Bild, auf das gezeichnet wird
     * @param color     die Farbe zum Zeichnen
     */
    public void drawRunSegments(Run run, BufferedImage segmented, int color) {
        int xstart = run.getXStart();
        int xend = run.getXEnd();
        int y = run.getY();

        for (int x = xstart; x < xend; x++) {
            segmented.setRGB(x, y, color);
        }
    }
}
