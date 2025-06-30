// Importieren der VK_*-Tastenkonstanten
import static java.awt.event.KeyEvent.*;

/**
 * Diese Klasse definiert die Figur, die von der Spieler:in gesteuert
 * wird. Die Figur bewegt sich dabei auf der Gitterstruktur des
 * Spielfeldes.
 *
 * @author Öykü Koç
 */
class Player extends Actor
{
    /**
     * Erzeugen und Anzeigen einer neuen Spielfigur.
     * @param x Die x-Koordinate dieser Spielfigur im Gitter.
     * @param y Die y-Koordinate dieser Spielfigur im Gitter.
     * @param rotation Die Rotation dieser Spielfigur (0 = rechts ... 3 = oben).
     * @param field Das Spielfeld, auf dem sich diese Spielfigur bewegt.
     */
    Player(final int x, final int y, final int rotation, final Field field)
    {
        super(x, y, rotation, "woman", field);
    }

    /**
     * Die Spielfigur bewegt sich entsprechend der von der Spieler:in
     * bewegten Tasten. Diese Methode kehrt erst zurück, wenn ein
     * gültiger Zug gemacht wurde.
     */
    @Override
    void act()
    {
        while (true) {
            final int key = getNextKey();
            if (key == VK_RIGHT && canWalk(0)) {
                setRotation(0);
                setLocation(getX() + 1, getY());
            }
            else if (key == VK_DOWN && canWalk(1)) {
                setRotation(1);
                setLocation(getX(), getY() + 1);
            }
            else if (key == VK_LEFT && canWalk(2)) {
                setRotation(2);
                setLocation(getX() - 1, getY());
            }
            else if (key == VK_UP && canWalk(3)) {
                setRotation(3);
                setLocation(getX(), getY() - 1);
            }
            else {
                playSound("error");
                continue;
            }

            playSound("step");
            sleep(200);
            break;
        }
    }
}
