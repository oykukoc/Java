/**
 * Die Klasse implementiert einen Ringpuffer, d.h. einen Puffer, der eine
 * bestimmte Menge von Werten zwischenspeichern kann. Man kann mit
 * {@link push(int)} Werte hinzufügen und mit {@link pop()} wieder entnehmen.
 * Dabei liefert {@link pop()} die Werte in derselben Reihenfolge zurück, in
 * der sie mit {@link push(int)} eingefügt wurden, d.h. {@link pop()} liefert
 * immer das Element zurück, das schon am längsten im Puffer gespeichert ist.
 * Wird ein neues Element in den Puffer eingefügt, wenn seine Kapazität bereits
 * erreicht ist, geht das älteste Element verloren. Es ist nicht erlaubt, auf
 * ein Element zuzugreifen, wenn der Puffer leer ist. In dem Fall ist das
 * Verhalten undefiniert.
 */
class RingBuffer
{
    /** Der eigentliche Puffer, der die Werte speichert. */
    private final int[] buffer;

    /** Der Kopf des Ringpuffers, d.h. die Stelle, an der als Nächstes etwas gelesen wird. */
    private int head = 0;

    /** Der Füllstand des Puffers. */
    private int entries = 0;

    /**
     * Erzeugt einen Ringpuffer.
     * @param capacity Die maximale Anzahl von Einträgen, die gepuffert werden können.
     */
    RingBuffer(final int capacity)
    {
        buffer = new int[capacity];
    }

    /**
     * Fügt ein neues Element in den Ringpuffer ein.
     * @param value Der Wert, der eingefügt werden soll.
     */
    void push(final int value)
    {
        if (buffer.length > 0) {
            if (entries == buffer.length) {
                pop();
            }
            buffer[(head + entries++) % buffer.length] = value;
        }
    }

    /**
     * Liefert das älteste Element aus dem Ringpuffer zurück, ohne es zu entnehmen.
     * @return Das älteste Element im Ringpuffer.
     */
    int peek()
    {
        return buffer[head];
    }

    /**
     * Entnimmt das älteste Element aus dem Ringpuffer.
     * @return Das Element, das entnommen wurde.
     */
    int pop()
    {
        final int value = peek();
        head = (head + 1) % buffer.length;
        --entries;
        return value;
    }

    /**
     * Liefert die Anzahl der Elemente zurück, die sich im Puffer befinden, d.h. die
     * mit {@link pop()} entnommen werden könnten.
     * @return Die Anzahl der belegten Einträge im Puffer.
     */
    int size()
    {
        return entries;
    }
}