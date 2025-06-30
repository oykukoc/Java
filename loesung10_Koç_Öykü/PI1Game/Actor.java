/**
 * Diese abstrakte Klasse definiert die Basisklasse für alle aktiven
 * Spielobjekte, d.h. welche, die sich bewegen. Sie definiert eine
 * abstrakte Methode, die alle abgeleiteten Klassen überschreiben
 * müssen, um das Verhalten des Spielobjekts zu definieren. Sie
 * bietet außerdem eine Methode, über die abgefragt werden kann, ob
 * sich dieses Spielobjekt entsprechend der Gitterstruktur des Feldes
 * in eine bestimmte Richtung bewegen darf.
 *
 * @author Thomas Röfer
 */
abstract class Actor extends GameObject
{
    /** Das Spielfeld. */
    private final Field field;

    /**
     * Erzeugt eine neue Akteur:in.
     * @param x Die x-Koordinate der Akteur:in im Gitter.
     * @param y Die y-Koordinate der Akteur:in im Gitter.
     * @param rotation Die Rotation dieser Akteur:in (0 = rechts ... 3 = oben).
     * @param fileName Der Dateiname des Bildes, durch das diese Akteur:in
     *         dargestellt wird.
     * @param field Das Spielfeld, auf dem sich diese Akteur:in bewegt.
     */
    Actor(final int x, final int y, int rotation, final String fileName,
            final Field field)
    {
        super(x, y, rotation, fileName);
        this.field = field;
    }

    /**
     * Prüfen, ob die Akteur:in in eine bestimmte Richtung laufen darf.
     * @param direction Die geprüfte Richtung (0 = rechts ... 3 = oben).
     */
    boolean canWalk(final int direction)
    {
        return field.hasNeighbor(getX(), getY(), direction);
    }

    /**
     * Diese Methode muss überschrieben werden, um das Verhalten dieser
     * Akteur:in zu definieren.
     */
    abstract void act();
}
