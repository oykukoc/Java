import java.util.stream.Stream;

/**
 * Dies ist die Hauptklasse eines Spiels. Sie enthält die Hauptmethode, die zum
 * Starten des Spiels aufgerufen werden muss.
 *
 * @author Öykü Koç
 */
abstract class PI1Game extends Game
{
    /** Das Spiel beginnt durch Aufruf dieser Methode.
     * 
     *  Wenn die IP-Adresse null oder leer ist,
     *  wird eine kontrollierte Spielfigur aus der Klasse ControlledPlayer erstellt.
     *  Wenn die IP-Adresse voll ist, 
     *  wird eine Controller-Spielfigur aus der RemotePlayer-Klasse erstellt.
     *  @param serverAddress Die IP-Adresse des Computers des ferngesteuerten Charakters.
     *  @param serverPort Der Port des Computers der ferngesteuerten Spielfigur.
     */

    static void main(final String serverAddress, final int serverPort)
    {
        // Den Level erzeugen
        final Level level = new Level("levels/1.lvl", serverAddress, serverPort);

        // Die Hauptschleife des Spiels
        // Die durch den Datenstrom transportierten Werte werden überhaupt nicht
        // verwendet. Einzig wichtig ist, dass keine mehr erzeugt werden, sobald
        // die Bedingung, die als zweiter Parameter formuliert ist, falsch wird.
        // Dann endet die Verarbeitung.
        Stream.generate(() -> level.getActors())
        .takeWhile(actors -> actors.get(0).isVisible())
        .forEach(actors -> actors.forEach(actor -> actor.act()));
        Stream.iterate(level.getActors(), actors -> actors.get(0).isVisible(), actors -> actors)
        .forEach(actors -> actors.forEach(actor -> actor.act()));

        level.hide();
    }
}
