package de.uni_bremen.pi2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse repräsentiert einen Knoten. Er hat Koordinaten
 * und eine Liste von Kanten, die ihn mit Nachbarknoten verbinden.
 * @author Öykü Koç
 */
class Node
{
    /** Die id des Knotens laut OSM. */
    private final int id;

    /** Die x-Koordinate des Knotens laut OSM. */
    private final double x;

    /** Die y-Koordinate des Knotens laut OSM. */
    private final double y;

    /** Die Liste von ausgehenden Kanten. */
    private final List<Edge> edges = new ArrayList<>();

    /** Von wo aus wurde Knoten auf dem günstigsten Weg erreicht? */
    private Node from;

    /** Die Abschätzung der Kosten vom Startknoten zu diesem Knoten. */
    private double costs;


    private boolean gesehen = false;

    /**
     * Konstruktor.
     * Die Liste der ausgehenden Kanten ist anfangs leer
     * und wird später ergänzt.
     * @param id Die id des Knotens laut OSM.
     * @param x Die x-Koordinate des Knotens laut OSM.
     * @param y Die y-Koordinate des Knotens laut OSM.
     */
    Node(final int id, final double x, final double y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Liefert die OSM-id des Knotens.
     * @return Die OSM-id.
     */
    int getId()
    {
        return id;
    }

    /**
     * Liefert die ausgehenden Kanten dieses Knotens.
     * @return Die ausgehenden Kanten.
     */
    List<Edge> getEdges()
    {
        return edges;
    }

    /**
     * Setzt den Vorgänger auf dem Weg vom Startknoten zu diesem Knoten sowie die
     * Kosten dieses Wegs bis zu diesem Knoten.
     * @param from Der Vorgänger auf dem Weg. Wenn null, gibt es keinen Vorgänger.
     * @param costs Die obere Schranke der Kosten vom Startknoten bis zu diesem Knoten.
     */
    void reachedFromAtCosts(final Node from, final double costs)
    {
        this.from = from;
        this.costs = costs;
    }

    /**
     * Der Vorgänger auf dem Weg vom Startknoten zu diesem Knoten.
     * @return Der Vorgängerknoten oder null, wenn es keinen gibt.
     */
    Node getFrom()
    {
        return from;
    }

    /**
     * Liefert die Kosten vom Startknoten bis zu diesem Knoten. Tatsächlich
     * ist dies eine obere Schranke der Kosten, d.h. sie sind im schlimmsten
     * Fall so hoch wie hier geliefert.
     * @return Die Kosten.
     */
    double getCosts()
    {
        return costs;
    }

    /**
     * Bestimmt die Distanz zu einem anderen Knoten.
     * @param to Der andere Knoten.
     * @return Der euklidische Abstand zwischen diesem und
     *         dem anderen Knoten.
     */
    double distance(final Node to)
    {
        return Math.sqrt(Math.pow(x - to.x, 2) +
                Math.pow(y - to.y, 2));
    }

    /**
     * Zeichnet diesen Knotens durch einen Punkt. Die Methode darf
     * nur aufgerufen werden, wenn RoutePlanner gerade beim
     * Neuzeichnen ist.
     * @param color Die Farbe, in der gezeichnet wird.
     */
    void draw(final Color color)
    {
        RoutePlanner.draw(x, y, color);
    }

    /**
     * Zeichnet eine Strecke zwischen diesem Knoten und einem
     * anderen. Die Methode darf nur aufgerufen werden, wenn
     * RoutePlanner gerade beim Neuzeichnen ist.
     * @param to Der andere Knoten.
     * @param color Die Farbe, in der gezeichnet wird.
     */
    void draw(final Node to, final Color color)
    {
        RoutePlanner.draw(x, y, to.x, to.y, color);
    }


    void setGesehen(boolean b) {
        gesehen = b;
    }

    boolean getGesehen() {
        return gesehen;
    }
}
