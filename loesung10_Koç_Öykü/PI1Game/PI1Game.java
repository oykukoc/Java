import java.util.ArrayList;
import java.util.List;

/**
 * Dies ist die Hauptklasse eines Spiels. Sie enthält die Hauptmethode, die zum
 * Starten des Spiels aufgerufen werden muss.
 *
 * @author Öykü Koç
 */
abstract class PI1Game extends Game
{
    /** Das Spiel beginnt durch Aufruf dieser Methode. */
    static void main()
    {
        // Erzeugt die Klasse Level.
        Level level = new Level("Level.txt");
               
        // Gibt den Spieler in der Liste Akteur zurück.
        Player player = (Player)level.getActors().get(0);
        
        // Array List, in der die der Karte hinzugefügten Objekte gespeichert werden.
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
        gameObjects.add(new GameObject(3, 0, 0, "goal"));
        gameObjects.add(new GameObject(4, 0, 0, "bridge"));
        gameObjects.add(new GameObject(4, 1, 3, "water-l"));
        gameObjects.add(new GameObject(3, 3, 0, "water-l"));
        gameObjects.add(new GameObject(4, 3, 0, "water-i"));

        while (player.isVisible()) {
            for (final Actor actor : level.getActors()) {
                // Ruft die Bewegungsmethode aller Actors auf.
                actor.act();

                // Schleife, die sicherstellt, dass bei der Zerstörung des Players auch alle anderen Akteure zerstört werden
                if(player.isVisible()==false){
                    level.hide();
                   
                    for(GameObject obj : gameObjects){
                        obj.setVisible(false);
                    }
                }

            }
        }
    }
}
