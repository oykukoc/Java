import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

/**
 * Diese Klasse definiert den Level eines Spiels. Ein Level wird
 * aus einer Beschreibung erzeugt, die in einer Datei gespeichert ist.
 * Die Datei enthält Symbole für leere Zellen (' '), freie Gitterzellen
 * ('O'), Wasserzellen ('W'), Zellverbindungen ('|', '-'), die Spielfigur
 * ('p', 'P', 'q', 'Q' für die Rotationen 0-3), drei Sorten
 * Spaziergänger:innen ('l', 'L', 'i', 'I' für Laila, 'c', 'C', 'd', 'D'
 * für Clausius und 's', 'S', 'z', 'Z' für das Kind, jeweils für die
 * Rotationen 0-3) sowie das Ziel und zwei Brückensymbole ('G', 'b', 'B').
 * Beispiel:
 * <pre>
 * O-d-O-G b
 *     |   |
 * l-O-O-O W-
 *     |
 * O-O-O-z-O
 *     |
 * p-O-O W-W-
 *       |
 * </pre>
 *
 * @author Öykü Koç
 */
class Level
{
    /** Die Gitterstruktur des Levels. */
    private final Field field;

    /** Die Spielfigur. */ 
    private Player player;

    /** Die Liste aller Akteur:innen. */
    private final List<Actor> actors = new ArrayList<>();

    /** Die Liste aller erzeugten Spielobjekte. */
    private final List<GameObject> gameObjects = new ArrayList<>();

    /**
     * Erzeugt einen neuen Level aus einer Datei und stellt ihn dar.
     * Auf der Grundlage der IP-Adresse und der Portinformationen
     * wird der kontrollierende Spieler oder die kontrollierte Spielfigur erstellt.
     * @param fileName Der Name der Datei mit der Level-Beschreibung.
     * @param serverAddress Die IP-Adresse des Computers des ferngesteuerten Charakters.
     * @param serverPort Der Port des Computers der ferngesteuerten Figur.        
     * @throws IllegalArgumentException Die Datei kann nicht gefunden oder
     *         gelesen werden oder ihr Inhalt ist ungültig.
     */
    Level(final String fileName, final String serverAddress, final int serverPort)
    {

        // Alle Zeilen des Levels einlesen
        final List<String> lines;
        try (final BufferedReader stream = new BufferedReader(new InputStreamReader(Game.Jar.getInputStream(fileName)))) {
            lines = stream.lines().collect(Collectors.toList());
        }
        catch (final FileNotFoundException e) {
            throw new IllegalArgumentException("Level '" + fileName + "' wurde nicht gefunden.");
        }
        catch (final IOException e) {
            throw new IllegalArgumentException("Fehler beim Lesen des Levels + '" + fileName + "'.");
        }

        // Die Gitterstruktur konstruieren.
        field = new Field(lines.toArray(new String[lines.size()]));

        // Die Spielfigur vorab erzeugen, da sie die erste Akteur:in sein muss.
        // Außerdem erwarten sie die Spaziergänger:innen als Parameter.
        // Die x-Koordinate -1 wird als Markierung für eine noch nicht
        // initialisierte Spielfigur verwendet. Außerdem sorgt sie dafür,
        // dass die Spielfigur noch nicht zu sehen ist.

        //Wenn die IP-Adresse null oder leer ist,
        //wird eine kontrollierte Spielfigur aus der Klasse ControlledPlayer erstellt.
        //Wenn die IP-Adresse voll ist,
        // wird eine Controller-Spielfigur aus der RemotePlayer-Klasse erstellt.
        if(serverAddress == null || serverAddress == "")
            player = new ControlledPlayer(-1, 0, 0, field , serverPort);
        else
            player = new RemotePlayer(-1, 0, 0, field , serverAddress , serverPort);   
            
        actors.add(player);
        gameObjects.add(player);

        // Alle Zellen des Feldes durchlaufen
        IntStream.iterate(0, y -> y < lines.size(), y -> y + 2)
        .forEach(y -> IntStream.iterate(0, x -> x < lines.get(y).length(), x -> x + 2)
                .forEach(x -> {
                            // Zelle mit erlaubten Symbolen vergleichen
                            final int index = "pPqQlLiIcCdDsSzZGbBWO ".indexOf(lines.get(y).charAt(x));

                            if (index == -1) {
                                // Kein erlaubtes Symbol gefunden
                                throw new IllegalArgumentException("Unbekanntes Symbol '" + lines.get(y).charAt(x)
                                    + "' in Level '" + fileName + "', Zeile " + (y + 1) + ", Spalte " + (x + 1)
                                    + " gefunden.");
                            }
                            else if(index < 4) {
                                // Es darf nur eine Spielfigur geben.
                                if (player.getX() != -1) {
                                    throw new IllegalArgumentException("Zweite Spielfigur in Level '"
                                        + fileName + "', Zeile " + (y + 1) + ", Spalte " + (x + 1)
                                        + " gefunden.");
                                }

                                // Existierende Spielfigur platzieren.
                                player.setLocation(x / 2, y / 2);
                                player.setRotation(index);
                            }
                            else if (index < 16) {
                                // Spaziergänger:innen platzieren, sind nach Bild und Rotationen geordnet.
                                final String[] images = {"laila", "claudius", "child"};
                                final Actor actor = new Walker(x / 2, y / 2, index % 4, images[index / 4 - 1], field, player);
                                actors.add(actor);
                                gameObjects.add(actor);
                            }
                            else if (index < 19) {
                                // Ziel und Brückensymbole einfügen
                                final String[] images = {"goal", "bridge-0", "bridge-1"};
                                gameObjects.add(new GameObject(x / 2, y / 2, 0, images[index - 16]));
                            }
                    }));

        // Wurde die Spielfigur nicht bewegt, wurde keine gefunden.
        if (player.getX() == -1) {
            throw new IllegalArgumentException("Keine Spielfigur in Level '" + fileName + "' gefunden");
        }

       
    }

    /**
     * Liefere alle Akteur:innen dieses Levels.
     * @return Die Akteur:innen des Levels. Die Spielfigur steht immer an erster
     *         Stelle.
     */
    List<Actor> getActors()
    {
        return actors;
    }

    /**
     * Lässt den Level wieder vom Bildschirm verschwinden.
     */
    void hide()
    {
        gameObjects.forEach(gameObject -> gameObject.setVisible(false));
        field.hide();
    }
}
