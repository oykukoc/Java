package de.uni_bremen.pi2;

/**
 * Die Klasse repräsentiert eine ausgehende Kante. Sie speichert
 * den Zielknoten und die Kosten, um vom Quellknoten zum Zielknoten
 * zu gelangen.
 * @author Thomas Röfer
 */
class Edge
{
    /** Der Zielknoten. */
    private final Node target;

    /** Die Kosten, die beim Nutzen dieser Kante entstehen. */
    private final double costs;

    /**
     * Konstruktor.
     * @param target Der Zielknoten.
     * @param costs Die Kosten, die bei Nutzung dieser Kante
     *         entstehen.
     */
    Edge(final Node target, final double costs)
    {
        this.target = target;
        this.costs = costs;
    }

    /**
     * Liefert den Zielknoten dieser Kante.
     * @return Der Zielknoten.
     */
    Node getTarget()
    {
        return target;
    }

    /**
     * Liefert die Kosten, die beim Nutzen dieser Kante
     * entstehen.
     * @return Die Kosten.
     */
    double getCosts()
    {
        return costs;
    }
}
