package de.uni_bremen.pi2;
/**
* @Author Altug Uyanik
*/
public class MergeSort
{
    /**
     * Findet den mittleren Knoten der verketteten Liste.
     * Diese Methode verwendet die Fast- und Slow-Pointer-Technik, um den mittleren Knoten zu finden.
     *
     * @param head der Kopfknoten der verketteten Liste
     * @return der mittlere Knoten der verketteten Liste
     */
    public static Node getMiddleNode(Node head)
    {
        if (head == null)
            return head;

        Node slowNode = head;
        Node fastNode = head.getNext();

        while (fastNode != null) {
            fastNode = fastNode.getNext();
            if (fastNode != null) {
                slowNode = slowNode.getNext();
                fastNode = fastNode.getNext();
            }
        }
        return slowNode;
    }

    /**
     * Verschmilzt zwei sortierte verkettete Listen zu einer einzigen sortierten verketteten Liste.
     *
     * @param leftNode der Kopfknoten der ersten sortierten verketteten Liste
     * @param rightNode der Kopfknoten der zweiten sortierten verketteten Liste
     * @return der Kopfknoten der verschmolzenen und sortierten verketteten Liste
     */
    public static Node sortAndMerge(Node leftNode, Node rightNode)
    {
        Node result = null;
        /* Base cases */
        if (leftNode == null)
            return rightNode;
        if (rightNode == null)
            return leftNode;

        /* Pick either a or b, and recur */
        if (leftNode.getValue().compareTo(rightNode.getValue()) <= 0) {
            result = leftNode;
            result.setNext(sortAndMerge(leftNode.getNext(), rightNode));
        }
        else {
            result = rightNode;
            result.setNext(sortAndMerge(leftNode, rightNode.getNext()));
        }
        return result;
    }


    /**
     * Führt den Merge-Sort-Algorithmus rekursiv auf der verketteten Liste aus.
     *
     * @param head der Kopfknoten der zu sortierenden verketteten Liste
     * @param <E> der Typ der Elemente, die Comparable implementieren
     * @return der Kopfknoten der sortierten verketteten Liste
     */
    public static <E extends Comparable<E>> Node<E> sort(final Node<E> head)
    {
        // Base case : if head is null
        if (head == null || head.getNext() == null) {
            return head;
        }

        // get the middle of the list
        Node<E> firstHalfTail = getMiddleNode(head);
        Node<E> secondHalfHead = firstHalfTail.getNext();

        // set the next of middle Node to null
        firstHalfTail.setNext(null);

        // Apply mergeSort on left list
        Node<E> leftHalf = sort(head);

        // Apply mergeSort on right list
        Node<E> rightHalf = sort(secondHalfHead);

        // Merge the left and right lists
        Node<E> sortedlist = sortAndMerge(leftHalf, rightHalf);

        return sortedlist;
    }


}