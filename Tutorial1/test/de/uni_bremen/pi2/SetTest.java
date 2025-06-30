package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

/**
 *@Author Öykü Koç
 * Test-Klasse für die Klasse Set<E>.
 * Diese Klasse enthält Testmethoden für die Methoden der Set-Klasse,
 * wie size, iterator, contains, add, intersect, union und diff.
 */
public class SetTest {

    /**
     * Inintialisierung von zwei Sets(SetA und SetB), mit denen im Folgenden die Tests durchgeführt werden.
     */
    Set<Integer> setA = new Set<>();
    Set<Integer> setB = new Set<>();

    /**
     * Einfügen von Integern in die Sets, sodass setA = [-256,-17,0,17,256] und setB = [-256,-18,0,17,128]
     */
    @BeforeEach
    void addItems() {
        setA.add(-256);
        setA.add(-17);
        setA.add(0);
        setA.add(17);
        setA.add(256);

        setB.add(-256);
        setB.add(-18);
        setB.add(0);
        setB.add(17);
        setB.add(128);
    }

    /**
     * Testet die Methode size(), um die Anzahl der Elemente im Set zu überprüfen.
     */
    @Test
    @DisplayName("Test the size of elements.")
    void getElementsSizeTest() {
        int size = setA.size();

        assertEquals(5, size);
    }

    /**
     * Testet den Iterator, um sicherzustellen, dass hasNext() False zurückgibt, wenn das Set leer ist.
     */
    @Test
    @DisplayName("Test Iterator HasNext With Non-Existent Value - Return False")
    void iteratorHasNextFalseTest(){
        Set<Integer> setIterator = new Set<>();

        Iterator<Integer> it = setIterator.iterator();
        boolean hasNext = it.hasNext();

        assertFalse(hasNext);
    }

    /**
     * Testet den Iterator, um sicherzustellen, dass hasNext() True zurückgibt, wenn das Set nicht leer ist.
     */
    @Test
    @DisplayName("Test Iterator HasNext With Existing Value - Return True")
    void iteratorHasNextTrueTest(){
        Set<Integer> setIterator = new Set<>();
        setIterator.add(300);

        Iterator<Integer> it = setIterator.iterator();
        boolean hasNext = it.hasNext();

        assertTrue(hasNext);
    }

    /**
     * Testet den Iterator, um sicherzustellen, dass der nächste Wert im Set korrekt zurückgegeben wird.
     */
    @Test
    @DisplayName("Test Iterator Next Value")
    void iteratorNextTest(){
        Iterator<Integer> it = setA.iterator();
        int nextValue = it.next();

        assertEquals(-256, nextValue);
    }

    /**
     * Testet die Methode contains(), um zu überprüfen, ob eine positive Zahl im Set enthalten ist.
     * Das Ergebnis sollte True sein.
     */
    @Test
    @DisplayName("Test for the existence of a positive value - Return True.")
    void existPositiveValueTest() {
        int existValue = 17;
        boolean isContain = setA.contains(existValue);

        assertTrue(isContain);
    }

    /**
     * Testet die Methode contains(), um sicherzustellen, dass eine positive Zahl nicht im Set enthalten ist.
     * Das Ergebnis sollte False sein.
     */
    @Test
    @DisplayName("Test whether positive value is not contained - Return False")
    void notExistPositiveValueTest() {
        int notExistValue = 33;
        boolean isContain = setA.contains(notExistValue);

        assertFalse(isContain);
    }

    /**
     * Testet die Methode contains(), um zu überprüfen, ob eine negative Zahl im Set enthalten ist.
     * Das Ergebnis sollte True sein.
     */
    @Test
    @DisplayName("Test if there is a negative value present - Return True.")
    void existNegativeValueTest() {
        int existValue = -17;
        boolean isContain = setA.contains(existValue);

        assertTrue(isContain);
    }

    /**
     * Testet die Methode contains(), um sicherzustellen, dass eine negative Zahl nicht im Set enthalten ist.
     * Das Ergebnis sollte False sein.
     */
    @Test
    @DisplayName("Test if negative value is not contained - Return False")
    void notExistNegativeValueTest() {
        int notExistValue = -44;
        boolean isContain = setA.contains(notExistValue);

        assertFalse(isContain);
    }

    /**
     * Testet die Methode add(), um das Hinzufügen von Werten zum Set zu überprüfen.
     */
    @Test
    @DisplayName("Test adding a new value - Added")
    void addNotExistValueTest() {
        int firstSize = setA.size();
        int notExistValue = 100;

        setA.add(notExistValue);
        int lastSize = setA.size();

        assertEquals(firstSize + 1, lastSize);
    }

    /**
     * Testet die Methode add(), um zu überprüfen, dass ein bereits vorhandener Wert nicht erneut hinzugefügt wird.
     */
    @Test
    @DisplayName("Test adding an existing value - Not Added.")
    void addExistValueTest() {
        int firstSize = setA.size();
        int existValue = 256;

        setA.add(existValue);
        int lastSize = setA.size();

        assertEquals(firstSize, lastSize);
    }

    /**
     * Testet die Methode add(), um sicherzustellen, dass ein null-Wert nicht zum Set hinzugefügt werden kann.
     */
    @Test
    @DisplayName("Test adding a null value - Not Added.")
    void addNullValueTest() {
        int firstSize = setA.size();
        setA.add(null);
        int lastSize = setA.size();

        assertEquals(firstSize, lastSize);
    }

    /**
     * Testet die Methode intersect(), um die Größe der Schnittmenge zweier Sets zu überprüfen.
     * Intersection size = 3 => [-256, 0, 17]
     */
    @Test
    @DisplayName("Test the size of the intersection")
    void intersectionSizeTest() {
        Set<Integer> intersectionSet = setA.intersect(setB);

        assertEquals(3, intersectionSet.size());
    }

    /**
     * Testet die Methode intersect(), um sicherzustellen, dass alle sich schneidenden Werte in der Schnittmenge enthalten sind.
     * Das Ergebnis sollte True sein.
     */
    @Test
    @DisplayName("Test All Intersection Values - Return True")
    void intersectionValuesTest() {
        Set<Integer> intersectionSet = setA.intersect(setB);

        assertTrue(intersectionSet.contains(-256));
        assertTrue(intersectionSet.contains(0));
        assertTrue(intersectionSet.contains(17));
    }

    /**
     * Testet die Methode intersect(), die beweist, dass ein Wert, der nicht zur Schnittmenge gehört, nicht in der Schnittmenge enthalten ist.
     * Das Ergebnis sollte False sein.
     */
    @Test
    @DisplayName("Test If No Intersection Values - Return False")
    void notIntersectionValueTest() {
        Set<Integer> intersectionSet = setA.intersect(setB);
        int notIntersectedValue = 128;

        assertFalse(intersectionSet.contains(notIntersectedValue));
    }

    /**
     * Testet die Methode union(), um die Größe der Vereinigung zweier Sets zu überprüfen.
     * Union size = 7 => [-256, -17, 0, 17, 256, -18, 128]
     */
    @Test
    @DisplayName("Test Union Size")
    void unionSizeTest() {
        Set<Integer> unionSet = setA.union(setB);

        assertEquals(7, unionSet.size());
    }

    /**
     * Testet die Methode union(), um sicherzustellen, dass alle Werte in der Vereinigungsmenge enthalten sind.
     * Das Ergebnis sollte True sein.
     */
    @Test
    @DisplayName("Test If All Union Values Exist - Return True")
    void unionValuesTest() {
        Set<Integer> unionSet = setA.union(setB);

        assertTrue(unionSet.contains(-256));
        assertTrue(unionSet.contains(-17));
        assertTrue(unionSet.contains(0));
        assertTrue(unionSet.contains(17));
        assertTrue(unionSet.contains(256));
        assertTrue(unionSet.contains(-18));
        assertTrue(unionSet.contains(128));
    }

    /**
     * Testet die Methode diff(), um die Größe der Differenz zweier Sets zu überprüfen.
     * Diff setA/setB size = 2 => [-17, 256]
     */
    @Test
    @DisplayName("Test Difference Size")
    void differencedSizeTest() {
        Set<Integer> differenceSet = setA.diff(setB);

        assertEquals(2, differenceSet.size());
    }

    /**
     * Testet die Methode diff(), um sicherzustellen, dass alle Werte, die zur Differenz gehören, in der Differenz Menge enthalten sind.
     * Das Ergebnis sollte True sein.
     */
    @Test
    @DisplayName("Test All Difference Values - Return True")
    void differencedValuesTest() {
        Set<Integer> differenceSet = setA.diff(setB);

        assertTrue(differenceSet.contains(-17));
        assertTrue(differenceSet.contains(256));
    }

    /**
     * Testet die Methode diff(), um sicherzustellen, dass ein Nicht-Differenzwert nicht in die Differenzmenge aufgenommen wird.
     * Das Ergebnis sollte False sein.
     */
    @Test
    @DisplayName("Test If No Difference Values Exist - Return False")
    void notDifferencedValueTest() {
        Set<Integer> differenceSet = setA.diff(setB);
        int notDifferencedValue = 0;

        assertFalse(differenceSet.contains(notDifferencedValue));
    }

}