package de.uni_bremen.pi2;

/**
 * Die Klasse repräsentiert einen Knoten in einer einfach verketteten Liste.
 * Der Knoten besteht aus einem gespeicherten Wert und einer Referenz auf das
 * nächste Element der Liste. Der gespeicherte Wert lässt sich nicht ändern,
 * der Verweis auf den Nachfolger aber schon.
 * @param <E> Der Typ der Werte, die in der Liste gespeichert werden können.
 *           Diese müssen eine Ordnung definieren.
 */
public class Node<E extends Comparable<E>>
{
    /** Der gespeicherte Wert. */
    private final E value;

    /**
     * Der Knoten mit dem nächsten Element der Liste oder null, wenn dieser der
     * letzte ist.
     */
    private Node<E> next;

    /**
     * Konstruktor eines Knotens.
     * @param value Der gespeicherte Wert.
     * @param next Der Knoten mit dem nächsten Element der Liste oder null, wenn
     *         dieser der letzte ist.
     * @throws IllegalArgumentException Der gespeicherte Wert darf nicht null sein.
     */
    public Node(final E value, final Node<E> next)
    {
        if (value == null) {
            throw new IllegalArgumentException("Gespeicherter Wert darf nicht null sein");
        }
        this.value = value;
        this.next = next;
    }

    /**
     * Konstruktor eines Knotens ohne Nachfolger.
     * @param value Der gespeicherte Wert.
     * @throws IllegalArgumentException Der gespeicherte Wert darf nicht null sein.
     */
    public Node(final E value)
    {
        this(value, null);
    }

    /**
     * Liefert den gespeicherten Wert.
     * @return Der gespeicherte Wert. Kann nicht null sein.
     */
    public E getValue()
    {
        return value;
    }

    /**
     * Liefert den Knoten mit dem nächsten Element der Liste.
     * @return Der Knoten mit dem nächsten Element der Liste oder null,
     *         wenn dieser der letzte ist.
     */
    public Node<E> getNext()
    {
        return next;
    }

    /**
     * Setzt den Knoten mit dem nächsten Element in der Liste.
     * @param next Den Knoten mit dem nächsten Element oder null,
     *         falls dieser Knoten das Ende der Liste sein soll.
     */
    public void setNext(final Node<E> next)
    {
        this.next = next;
    }

    /**
     * Liefert eine Zeichenkette mit den Elementen der Liste ab
     * diesem Knoten.
     * @return Die Liste als String.
     */
    @Override
    public String toString()
    {
        return value + (next == null ? "" : " -> " + next);
    }
}
