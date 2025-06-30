package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 * @Author Öykü Koç
 */
public class MergeSortTest {

    /**
     * Gibt den mittleren Knoten einer gegebenen verketteten Liste zurück.
     * Wenn die Liste leer ist, wird null zurückgegeben.
     */
    @Test
    void testGetMiddleNodeEmptyList_ShouldBeNull() {
        Node<Integer> emptyList = asList();
        Node<Integer> middleNode = MergeSort.getMiddleNode(emptyList);
        assertNull(middleNode);
    }

    /**
     * Gibt den mittleren Knoten einer verketteten Liste mit einem einzelnen Element zurück.
     */
    @Test
    void testGetMiddleNodeSingleElementList_ShouldBeOne() {
        Node<Integer> singleElement = asList(1);
        Node<Integer> middleNode = MergeSort.getMiddleNode(singleElement);
        assertEquals(1, middleNode.getValue());
    }

    /**
     * Gibt den mittleren Knoten einer verketteten Liste mit einer geraden Anzahl von Elementen zurück.
     */
    @Test
    void testGetMiddleNodeEvenElementsList_ShouldBeTwo() {
        Node<Integer> evenList = asList(1, 2, 3, 4);
        Node<Integer> middleNode = MergeSort.getMiddleNode(evenList);
        assertEquals(2, middleNode.getValue());
    }

    /**
     * Gibt den mittleren Knoten einer verketteten Liste mit einer ungeraden Anzahl von Elementen zurück.
     */
    @Test
    void testGetMiddleNodeOddElementsList_ShouldBeThree() {
        Node<Integer> nodeOddElementList = asList(1, 2, 3, 4, 5);
        Node<Integer> middleNode = MergeSort.getMiddleNode(nodeOddElementList);
        assertEquals(3, middleNode.getValue());
    }

    /**
     * Gibt den mittleren Knoten einer langen verketteten Liste zurück.
     */
    @Test
    void testGetMiddleNodeLongList_ShouldBeFive() {
        Node<Integer> nodeLongList = asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Node<Integer> middleNode = MergeSort.getMiddleNode(nodeLongList);
        assertEquals(5, middleNode.getValue());
    }

    /**
     * Testet die Methode `sortAndMerge` mit zwei positiv sortierten Listen und überprüft,
     * ob die zurückgegebene Liste korrekt zusammengeführt und sortiert ist.
     */
    @Test
    void testSortAndMerge_Positive_ReturnSortedList() {
        Node<Integer> leftNode = asList(1, 3, 5);
        Node<Integer> rightNode = asList(2, 4, 6);
        assertListEquals(MergeSort.sortAndMerge(leftNode, rightNode), 1, 2, 3, 4, 5, 6);
    }

    /**
     * Testet das Sortieren einer unsortierten verketteten Liste.
     */
    @Test
    void testSortingUnsortedList_ShouldReturnTrue() {
        Node<Integer> unsortedList = asList(8, 6, 7, 4);
        Node<Integer> sortedList = MergeSort.sort(unsortedList);
        assertListEquals(sortedList, 4, 6, 7, 8);
    }

    /**
     * Testet das Sortieren einer leeren verketteten Liste.
     */
    @Test
    void testSortingEmptyList_ShouldReturnNull() {
        Node<Integer> emptyList = asList();
        Node<Integer> sortedList = MergeSort.sort(emptyList);
        assertListEquals(sortedList);
    }

    /**
     * Testet das Sortieren einer verketteten Liste mit doppelten Elementen.
     */
    @Test
    void testSortingListWithDuplicateElements_ShouldReturnTrue() {
        Node<Integer> duplicateElementsList = asList(11, 11, 9, 21, 21);
        Node<Integer> sortedList = MergeSort.sort(duplicateElementsList);
        assertListEquals(sortedList, 9, 11, 11, 21, 21);
    }

    /**
     * Testet das Sortieren einer bereits sortierten verketteten Liste.
     */
    @Test
    void testSortingAlreadySortedList_ShouldReturnGivenList() {
        Node<Integer> alreadySortedList = asList(8, 9, 10, 14);
        Node<Integer> sortedList = MergeSort.sort(alreadySortedList);
        assertListEquals(sortedList, 8, 9, 10, 14);
    }

    /**
     * Testet das Sortieren einer verketteten Liste mit einem einzelnen Element.
     */
    @Test
    void testSortingSingleElementList_ShouldReturnOne() {
        Node<Integer> singleElementList = asList(1);
        Node<Integer> sortedList = MergeSort.sort(singleElementList);
        assertListEquals(sortedList, 1);
    }

    /**
     * Testet, ob die sortierten Werte einer Liste den erwarteten Werten entsprechen.
     */
    @Test
    public void testSortedAsListValues_ShouldBeEqualTo_GivenListValues() {
        assertListEquals(MergeSort.sort(asList(5, 4, 3, 2, 1)), 1, 2, 3, 4, 5);
    }

    /**
     * Testet, ob der Wert des Kopfknotens einer sortierten Liste eins ist.
     */
    @Test
    void testSortedNodeHeadValue_ShouldBeOne() {
        assertEquals(1, MergeSort.sort(asList(5, 4, 3, 2, 1)).getValue());
    }

    /**
     * Testet, ob der Wert des nächsten Knotens nach dem Kopfknoten in einer sortierten Liste zwei ist.
     */
    @Test
    void testSortedNodeHeadNextValue_ShouldBeTwo() {
        assertEquals(2, MergeSort.sort(asList(5, 3, 4, 1, 2)).getNext().getValue());
    }

    /**
     * Testet, ob der Wert des Kopfknotens einer sortierten Liste -50 ist.
     */
    @Test
    void testSortedNegativeValues_ShouldBeNegativeFifty() {
        assertEquals(-50, MergeSort.sort(asList(-20, -50, -10, -40, -30)).getValue());
    }

    /**
     * Testet, ob der Wert des Kopfknotens einer sortierten Liste -20 ist.
     */
    @Test
    void testSortedPositiveAndNegativeValues_ShouldBeNegativeTwenty() {
        assertEquals(-20, MergeSort.sort(asList(-20, 50, -10, 40, 30)).getValue());
    }

    /**
     * Testet, ob das nächste Element nach dem letzten Element in einer sortierten Liste null ist.
     */
    @Test
    void testSortedNodeLastNext_ShouldBeNull() {
        assertNull(MergeSort.sort(asList(3, 1, 4)).getNext().getNext().getNext());
    }

    /**
     * Erzeugt eine Liste aus einer Folge von Werten.
     * Z.B. erzeugt asList(1, 2, 3) eine Liste mit den Werten 1, 2 und 3.
     *
     * @param values Die Werte, aus denen die Liste erzeugt wird. Können einfach
     *               aufgezählt werden.
     * @param <E>    Der Typ der Werte.
     * @return Die Liste, die die Werte in der Reihenfolge enthält, in der sie
     * aufgezählt wurden.
     */
    private <E extends Comparable<E>> Node<E> asList(final E... values) {
        if (values == null || values.length == 0) {
            return null;
        }

        Node<E> head = new Node<>(values[0]);
        Node<E> current = head;

        for (int i = 1; i < values.length; i++) {
            Node<E> newNode = new Node<>(values[i]);
            current.setNext(newNode);
            current = current.getNext();
        }

        return head;
    }

    /**
     * Überprüft, ob eine Liste einer bestimmten Folge von Werten entspricht.
     * Kann z.B. wie folgt genutzt werden:
     * <pre>
     * assertListEquals(MergeSort.sort(asList(3, 2, 1)), 1, 2, 3);
     * </pre>
     *
     * @param head   Der erste Knoten der Liste, deren Inhalt verglichen wird.
     * @param values Die Vergleichswerte. Können einfach aufgezählt werden.
     * @param <E>    Der Typ der Werte, die verglichen werden.
     */
    private <E extends Comparable<E>> void assertListEquals(final Node<E> head, final E... values) {
        Node<E> currentNode = head;

        for (E value : values) {
            assertEquals(0, value.compareTo(currentNode.getValue()));
            currentNode = currentNode.getNext();
        }
        assertNull(currentNode);
    }
}

