// Importiert assertEquals usw. sowie Test-Annotationen
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Diese Klasse definiert die Tests für die Klasse RingBuffer.
 *
 * @author Elena Gorbachuk und Thomas Röfer
 */
public class RingBufferTest
{
    @Test
    public void testCapacity0()
    {
        RingBuffer ringBuff1 = new RingBuffer(0);
        assertEquals(0, ringBuff1.size());
        ringBuff1.push(10);
        assertEquals(0, ringBuff1.size());
    }

    @Test
    public void testSize1()
    {
        RingBuffer ringBuff1 = new RingBuffer(1);
        assertEquals(0, ringBuff1.size());
        ringBuff1.push(10);
        assertEquals(1, ringBuff1.size());
        ringBuff1.pop();
        assertEquals(0, ringBuff1.size());
        ringBuff1.push(20);
        ringBuff1.push(30);
        assertEquals(1, ringBuff1.size());
        ringBuff1.pop();
        assertEquals(0, ringBuff1.size());
        ringBuff1.push(40);
        ringBuff1.push(50);
        ringBuff1.push(60);
        assertEquals(1, ringBuff1.size());
    }

    @Test
    public void testSize3()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        assertEquals(0, ringBuff1.size());
        ringBuff1.push(10);
        assertEquals(1, ringBuff1.size());
        ringBuff1.push(20);
        assertEquals(2, ringBuff1.size());
        ringBuff1.push(30);
        assertEquals(3, ringBuff1.size());
        ringBuff1.pop();
        assertEquals(2, ringBuff1.size());
        ringBuff1.push(40);
        ringBuff1.push(50);
        assertEquals(3, ringBuff1.size());
        ringBuff1.pop();
        assertEquals(2, ringBuff1.size());
        ringBuff1.pop();
        assertEquals(1, ringBuff1.size());
        ringBuff1.pop();
        assertEquals(0, ringBuff1.size());
    }

    @Test
    public void testPop1()
    {
        RingBuffer ringBuff1 = new RingBuffer(1);
        ringBuff1.push(10);
        assertEquals(10, ringBuff1.pop());
        ringBuff1.push(20);
        ringBuff1.push(30);
        assertEquals(30, ringBuff1.pop());
        ringBuff1.push(40);
        ringBuff1.push(50);
        ringBuff1.push(60);
        assertEquals(60, ringBuff1.pop());
    }

    @Test
    public void testPop3()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(10);
        assertEquals(10, ringBuff1.pop());
        ringBuff1.push(20);
        ringBuff1.push(30);
        assertEquals(20, ringBuff1.pop());
        assertEquals(30, ringBuff1.pop());
        ringBuff1.push(40);
        ringBuff1.push(50);
        ringBuff1.push(60);
        assertEquals(40, ringBuff1.pop());
        assertEquals(50, ringBuff1.pop());
        ringBuff1.push(70);
        ringBuff1.push(80);
        ringBuff1.push(90);
        assertEquals(70, ringBuff1.pop());
        assertEquals(80, ringBuff1.pop());
        assertEquals(90, ringBuff1.pop());
    }

    @Test
    public void testPeek1()
    {
        RingBuffer ringBuff1 = new RingBuffer(1);
        ringBuff1.push(10);
        assertEquals(10, ringBuff1.peek());
        ringBuff1.pop();
        ringBuff1.push(20);
        ringBuff1.push(30);
        assertEquals(30, ringBuff1.peek());
        ringBuff1.pop();
        ringBuff1.push(40);
        ringBuff1.push(50);
        ringBuff1.push(60);
        assertEquals(60, ringBuff1.peek());
    }

    @Test
    public void testPeek3()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(10);
        assertEquals(10, ringBuff1.peek());
        ringBuff1.pop();
        ringBuff1.push(20);
        ringBuff1.push(30);
        assertEquals(20, ringBuff1.peek());
        ringBuff1.pop();
        assertEquals(30, ringBuff1.peek());
        ringBuff1.pop();
        ringBuff1.push(40);
        ringBuff1.push(50);
        ringBuff1.push(60);
        assertEquals(40, ringBuff1.peek());
        ringBuff1.pop();
        assertEquals(50, ringBuff1.peek());
        ringBuff1.pop();
        ringBuff1.push(70);
        ringBuff1.push(80);
        ringBuff1.push(90);
        assertEquals(70, ringBuff1.peek());
        ringBuff1.pop();
        assertEquals(80, ringBuff1.peek());
        ringBuff1.pop();
        assertEquals(90, ringBuff1.peek());
    }

    @Test
    public void testAll1000()
    {
        final int n = 1000;
        final RingBuffer ringBuff1 = new RingBuffer(n);
        int pushValue = 0;
        int popValue = 0;

        // Puffer füllen n * (push, pop, push), noch geht nichts verloren
        while (pushValue < n * 2) {
            assertEquals(pushValue / 2, ringBuff1.size());
            ringBuff1.push(pushValue++);
            assertEquals(popValue, ringBuff1.peek());
            assertEquals(popValue++, ringBuff1.pop());
            ringBuff1.push(pushValue++);
        }

        // Puffer voll, jeder zweite Wert geht verloren
        while (pushValue < n * 6) {
            assertEquals(n, ringBuff1.size());
            ringBuff1.push(pushValue++);
            assertEquals(++popValue, ringBuff1.peek());
            assertEquals(popValue++, ringBuff1.pop());
            ringBuff1.push(pushValue++);
        }
    }
}
