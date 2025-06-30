package de.uni_bremen.pi2;

import java.util.Iterator;

/**
 *@Author Öykü Koç
 * Diese Klasse implementiert eine einfache Menge (Set) in Java.
 * @param <E> Der generische Typ der Elemente in der Menge.
 */
public class Set<E> implements Iterable<E> {

    /**
     * Die Elemente der Menge, gespeichert in einem Array.
     */
    public E[] elements;

    /**
     * Konstruktor für eine leere Menge.
     */
    public Set() {
        elements = (E[]) new Object[0];
    }

    /**
     * Gibt die Anzahl der Elemente in der Menge zurück.
     *
     * @return Die Anzahl der Elemente in der Menge.
     */
    public int size() {
        return elements.length;
    }

    /**
     * Gibt einen Iterator zurück, der es ermöglicht, über die Elemente der Menge zu iterieren.
     *
     * @return Ein Iterator für die Elemente der Menge.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public E next() {
                return (E) elements[currentIndex++];
            }
        };
    }

    /**
     * Überprüft, ob die Menge das angegebene Element enthält.
     *
     * @param element Das Element, das überprüft werden soll.
     * @return true, wenn die Menge das Element enthält, andernfalls false.
     */
    public boolean contains(E element) {
        boolean isContain = false;
        Iterator<E> it = iterator();
        E currentElement;

        while (it.hasNext()) {
            currentElement = it.next();
            if (currentElement.equals(element)) {
                isContain = true;
                break;
            }
        }
        return isContain;
    }

    /**
     * Fügt ein Element zur Menge hinzu, sofern es nicht bereits vorhanden ist und nicht null ist.
     *
     * @param input Das Element, das zur Menge hinzugefügt werden soll.
     */
    public void add(E input) {
        if (contains(input) || input == null)
            return;

        E[] temp = (E[]) new Object[size() + 1];

        for (int i = 0; i < size(); i++) {
            temp[i] = elements[i];
        }

        temp[temp.length - 1] = input;

        elements = temp;
    }

    /**
     * Berechnet die Schnittmenge zwischen dieser Menge und einer anderen Menge.
     *
     * @param otherSet Die andere Menge, mit der die Schnittmenge berechnet werden soll.
     * @return Die Schnittmenge der beiden Mengen.
     */
    public Set<E> intersect(Set<E> otherSet) {
        Set<E> intersection = new Set<>();

        for (E element : this) {
            if (otherSet.contains(element)) {
                intersection.add(element);
            }
        }

        return intersection;
    }

    /**
     * Berechnet die Vereinigungsmenge zwischen dieser Menge und einer anderen Menge.
     *
     * @param otherSet Die andere Menge, mit der die Vereinigungsmenge berechnet werden soll.
     * @return Die Vereinigungsmenge der beiden Mengen.
     */
    public Set<E> union(Set<E> otherSet) {
        Set<E> union = new Set<>();

        for (E element : this) {
            union.add(element);
        }

        for (E element : otherSet.elements) {
            if (!contains(element)) {
                union.add(element);
            }
        }

        return union;
    }

    /**
     * Berechnet die Differenzmenge zwischen dieser Menge und einer anderen Menge.
     *
     * @param otherSet Die andere Menge, mit der die Differenzmenge berechnet werden soll.
     * @return Die Differenzmenge der beiden Mengen.
     */
    public Set<E> diff(Set<E> otherSet) {
        Set<E> difference = new Set<>();

        for (E element : this) {
            if (!otherSet.contains(element)) {
                difference.add(element);
            }
        }

        return difference;
    }
}
