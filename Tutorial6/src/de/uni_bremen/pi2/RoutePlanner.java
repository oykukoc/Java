package de.uni_bremen.pi2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.util.*;

/**
 * Die Klasse repräsentiert das Fenster des Routenplaners. In ihm wird die
 * Karte angezeigt. Die Klasse skaliert die gezeichnete Karte automatisch
 * so, dass sie komplett dargestellt wird. Wenn man einen Knoten anklickt,
 * wird vom zuletzt angeklickten Knoten zum neuen der kürzeste Weg
 * eingezeichnet.
 */
class RoutePlanner extends JPanel
{
    /** Hilfsklasse zum Zeichnen. */
    private static class Drawable
    {
        /** Ist diese eine Strecke oder ein Punkte? */
        final boolean isLine;

        /** Koordinaten. x2 und y2 werden für Punkte nicht genutzt. */
        final double x1, y1, x2, y2;

        /** Die Farbe, in der gezeichnet wird. */
        final Color color;

        /**
         * Erzeugt einen Punkt.
         * @param x     Die x-Koordinate des Punkts.
         * @param y     Die y-Koordinate des Punkts.
         * @param color Die Farbe, in der gezeichnet wird.
         */
        Drawable(final double x, final double y, final Color color)
        {
            isLine = false;
            x1 = x;
            y1 = y;
            x2 = y2 = 0;
            this.color = color;
        }

        /**
         * Erzeugt eine Strecke.
         * @param x1    Die x-Koordinate des einen Endpunkts.
         * @param y1    Die y-Koordinate des einen Endpunkts.
         * @param x2    Die x-Koordinate des anderen Endpunkts.
         * @param y2    Die y-Koordinate des anderen Endpunkts.
         * @param color Die Farbe, in der gezeichnet wird.
         */
        Drawable(final double x1, final double y1,
                        final double x2, final double y2, final Color color)
        {
            isLine = true;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    /** Alle Zeichenanweisungen, die während eines "paint"-Aufrufs anfallen. */
    private static final List<Drawable> drawables = new ArrayList<>();

    /** Die kleinste x-Koordinate in den Daten. Erst nach "paint" gültig. */
    private double xMin;

    /** Die kleinste y-Koordinate in den Daten. Erst nach "paint" gültig. */
    private double yMin;

    /** Die x-Skalierung für das Zeichnen der Daten. Erst nach "paint" gültig. */
    private double xScale;

    /** Die y-Skalierung für das Zeichnen der Daten. Erst nach "paint" gültig. */
    private double yScale;

    /** Die Karte. */
    private final Map map;

    /** Der Startknoten, wie er durch Mausklick gewählt wurde. */
    private Node start;

    /** Der Zielknoten, wie er durch Mausklick gewählt wurde. */
    private Node goal;

    /**
     * Der Konstruktor erzeugt das Hauptfenster und die Zeichenfläche.
     * Hier muss auch die Karte erzeugt werden.
     * @throws IOException Das Einlesen der Karte hat nicht geklappt.
     */
    private RoutePlanner() throws IOException
    {
        // Beim Lesen von Text wird der Punkt als Dezimalzeichen verwendet
        Locale.setDefault(new Locale("C"));

        map = new Map();

        final JFrame frame = new JFrame("Routenplaner"); // Fensterrahmen erzeugen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this); // Zeichenfläche hinzufügen
        setBackground(Color.white); // Hintergrund ist weiss
        setPreferredSize(new Dimension(700, 700)); // Größe der Zeichenfläche setzen
        frame.pack(); // Fenster so anpassen, dass die Zeichenfläche hineinpasst
        frame.setVisible(true); // Fenster anzeigen

        // Setzen von Start- und Zielposition per Mausklick.
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(final MouseEvent event)
            {
                // Fensterkoordinaten in Kartenkoordinaten umrechnen
                final double x = event.getX() / xScale + xMin;
                final double y = (getSize().height - 1 - event.getY()) / yScale + yMin;

                // Das bisherige Ziel wird der neue Startpunkt.
                start = goal;

                // Der dichteste Knoten wird das neue Ziel.
                goal = map.getClosest(x, y);

                // Karte neuzeichnen.
                repaint();
            }
        });
    }

    /**
     * Zeichnen eines Punkts in die Zeichenfläche. Diese Methode kann nur während
     * der Ausführung von "paint" verwendet werden. Dafür kann sie aber von
     * überall aus aufgerufen werden.
     * @param x     x-Koordinate des Punkts.
     * @param y     y-Koordinate des Punkts.
     * @param color Die Farbe, in der gezeichnet wird.
     */
    static void draw(final double x, final double y, final Color color)
    {
        drawables.add(new Drawable(x, y, color));
    }

    /**
     * Zeichnen einer Strecke in die Zeichenfläche. Diese Methode kann nur
     * während der Ausführung von "paint" verwendet werden. Dafür kann sie
     * aber von überall aus aufgerufen werden.
     * @param x1    x-Koordinate des Anfangspunkts.
     * @param y1    y-Koordinate des Anfangspunkts.
     * @param x2    x-Koordinate des Endpunkts.
     * @param y2    y-Koordinate des Endpunkts.
     * @param color Die Farbe, in der gezeichnet wird.
     */
    static void draw(final double x1, final double y1,
                            final double x2, final double y2, final Color color)
    {
        drawables.add(new Drawable(x1, y1, x2, y2, color));
    }

    /**
     * Zeichnen von Karte und Weg. Start- und Zielpunkt werden gezeichnet,
     * wenn sie bekannt sind. Der Weg wird nur eingezeichnet, wenn sowohl
     * Start- als auch Zielpunkt bekannt sind.
     * @param graphics Der Grafikkontext, in den gezeichnet wird.
     */
    public void paintComponent(final Graphics graphics)
    {
        super.paintComponent(graphics);

        // Alte Zeichnung löschen
        drawables.clear();

        // Zeichnen der Karte.
        map.draw();

        // Wurden sowohl Start als auch Ziel gewählt, wird auch der kürzeste Weg bestimmt
        // und eingezeichnet.
        if (start != null && goal != null) {
            map.reset();
            shortestPath(start, goal);
        }

        // Markieren der gewählten Knoten
        if (start != null) {
            start.draw(Color.RED);
        }
        if (goal != null) {
            goal.draw(Color.RED);
        }

        // Bestimmen der Grenzen der zu zeichnenden Elemente
        xMin = Double.POSITIVE_INFINITY;
        yMin = Double.POSITIVE_INFINITY;
        double xMax = Double.NEGATIVE_INFINITY;
        double yMax = Double.NEGATIVE_INFINITY;
        for (final Drawable drawable : drawables) {
            xMin = Math.min(xMin, drawable.x1);
            xMax = Math.max(xMax, drawable.x1);
            yMin = Math.min(yMin, drawable.y1);
            yMax = Math.max(yMax, drawable.y1);
            if (drawable.isLine) {
                xMin = Math.min(xMin, drawable.x2);
                xMax = Math.max(xMax, drawable.x2);
                yMin = Math.min(yMin, drawable.y2);
                yMax = Math.max(yMax, drawable.y2);
            }
        }

        // Zeichnen aller Elemente mit der richtigen Skalierung
        xScale = xMax - xMin > 0 ? getSize().width / (xMax - xMin) : 1;
        yScale = yMax - yMin > 0 ? getSize().height / (yMax - yMin) : 1;
        for (final Drawable drawable : drawables) {
            graphics.setColor(drawable.color);
            if (drawable.isLine) {
                graphics.drawLine((int) ((drawable.x1 - xMin) * xScale),
                        (int) (getSize().height - 1 - (drawable.y1 - yMin) * yScale),
                        (int) ((drawable.x2 - xMin) * xScale),
                        (int) (getSize().height - 1 - (drawable.y2 - yMin) * yScale));
            }
            else {
                graphics.fillOval((int) ((drawable.x1 - xMin) * xScale - 2),
                        (int) (getSize().height - 1 - (drawable.y1 - yMin) * yScale - 2), 5, 5);
            }
        }
    }

    /**
     * Methode bestimmt den kürzesten Weg zwischen Quell- und Zielknoten.
     * Zeichnet Rand und kürzesten Weg in die Karte ein.
     *
     * @param from Der Quellknoten.
     * @param to Der Zielknoten.
     */
    private void shortestPath(final Node from, final Node to) {
        Node start = from;
        Node end = to;

        Queue<Node> border = new PriorityQueue<>(Comparator.comparingDouble(Node::getCosts).thenComparingInt(Node::getId));
        ArrayList<Node> chosen = new ArrayList();

        start.reachedFromAtCosts(start, start.distance(start));

        while (!chosen.contains(end)) {

            List<Edge> edges = start.getEdges();
            chosen.add(start);

            for (Edge edge : edges) {
                if (border.contains(edge.getTarget())) {
                    if (edge.getTarget().getCosts() > (start.getCosts() + edge.getCosts())) {
                        border.remove(edge.getTarget());
                        edge.getTarget().reachedFromAtCosts(start, start.getCosts() + edge.getCosts());
                        border.add(edge.getTarget());
                        start.draw(edge.getTarget(), Color.blue);
                    }
                } else if (!chosen.contains(edge.getTarget())) {
                    edge.getTarget().reachedFromAtCosts(start, start.getCosts() + edge.getCosts());
                    border.add(edge.getTarget());
                    start.draw(edge.getTarget(), Color.blue);
                }
            }
            start = border.poll();
            chosen.add(start);

        }

        if (end.getFrom() != null) {
            Node chosenL = end;
            while (chosenL.getFrom() != chosenL) {
                chosenL.draw(chosenL.getFrom(), Color.red);
                chosenL = chosenL.getFrom();
            }
        }
    }


    /**
     * Das Hauptprogramm öffnet das Hauptfenster.
     * @param args Die Parameter werden ignoriert.
     * @throws IOException Das Einlesen der Karte hat nicht geklappt.
     */
    public static void main(final String[] args) throws IOException
    {
        new RoutePlanner();
    }
}
