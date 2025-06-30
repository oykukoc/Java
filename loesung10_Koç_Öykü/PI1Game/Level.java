import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.InputStream;
import java.io.IOException;

/**
 * Diese Klasse ermöglicht die Erstellung der Klasse Field und die Erstellung der Klassen Player und Walker.
 * Die Karte wird mit der Klasse Field erstellt. Die Spieler werden mit den Klassen Player und Walker erstellt. 
 * Sie nimmt den Dateinamen, unter dem die Karte gespeichert ist, als Parameter entgegen.
 * 
 * @author Öykü Koç
 */
class Level
{
    /** Standardbereichssymbol der Karte. */
    final private char defaultMapChar = 'O';

    /** Array map, mit dem wir die Karte erstellen können */
    final private String[] map;

    /** Feldklasse, mit der wir die Karte erstellen können */
    final private Field field;

    /** LinkedHashMap, in der wir die Symbole und Namen der Akteure speichern. */
    final private LinkedHashMap<String, String> symbols;

    /** Array List, in der wir die Akteure aufbewahren */
    final public ArrayList<Actor> actors = new ArrayList<Actor>();

    /** Die Spielfigur. */
    private Player player;

    /**
     * Konstrukteur der Klasse Level.
     * @param fileName Es handelt sich um die Variable mit dem Dateinamen,
     * die die Symbole der Karte, des Hauptakteurs und der Akteure enthält,
     * die zur Erstellung einer Karte in der Level-Klasse erforderlich sind.
     */
    public Level(String fileName){
        //Definiere Symbole, die die Namen und Richtungen von Spielern und NPCs enthalten.
        this.symbols = new LinkedHashMap<String, String>();
        this.symbols.put("woman", "0123");
        this.symbols.put("claudius", "@ABC");
        this.symbols.put("laila", "DEFG");
        this.symbols.put("child", "HIJK");

        // Die aus der Datei gelesenen Kartendaten werden der Map Array List hinzugefügt.
        this.map = getMapFromFile(fileName);

        // Map Array List wird an die Klasse Field gesendet, um eine Map zu erstellen.    
        this.field = new Field(map);

        // Spieler und Walker-Akteure werden erstellt.
        createActors();
    }

    /**
    * In der Klasse Level die Methode, die die Kartendaten als String[] aus dem "in der Anwendung angegebenen Dateinamen" an den Benutzer zurückgibt,
    * indem sie die Zeilen der gewünschten Karte in der Datei nacheinander abfährt.
    * @param fileName Die Map, die benötigt wird, um eine Map in der Level-Klasse zu erstellen,
    * die die Symbole des Hauptakteurs und der Akteure enthält ist die Variable des Dateinamens        
    */
    private String[] getMapFromFile(String fileName) 
    {
        int charNum = -1;
        char character;
        String fileText = "";
        InputStream file = null;

        // Fehlermeldung, wenn die Level-Datei nicht gefunden wurde.
        try{
            file = Game.Jar.getInputStream(fileName);

        }catch(FileNotFoundException ex){

            if(file == null)
                throw new IllegalArgumentException("Die Level-Datei wurde nicht gefunden.");
        }

        //Fehlermeldung beim Einlesen der Datei.
        try{
            charNum = file.read();

            if(charNum == -1)
                throw new IllegalArgumentException("Es gab Probleme beim Lesen der Datei.");
        }
        catch (IOException ıoe)
        {
            System.out.println(ıoe);
        }

        try
        {

            while(!(charNum == -1))
            {
                character = (char)charNum;

                // 10 = Neue Zeile, für die Verwendung in der Methode split() wird das Symbol "#" verwendet, um den Zeilenanfang zu kennzeichnen.
                if(charNum == 10)
                    fileText  = fileText + '#';
                else
                    fileText  = fileText + character;

                charNum = file.read();

            }

            file.close();

        }catch(IOException ıoe){
            System.out.println(ıoe);
        }
        finally{
            // Die gelesene Datei wird dem String[] map Array mit der Methode split() hinzugefügt. 
            String[] map = fileText.split("#");
            return map;
        }

    }

    /**
     * Methode zum Abrufen von Akteursnamen, die in der LinkedHashMap named symbols gespeichert sind.
     * Der ASCII-Wert der Symbole aus der Karte definiert sowohl die Richtung als auch den Index des Symbols in der LinkedHashMap VALUE.
     * Wenn die char-Daten in den Symbolen mit den char-Daten aus der Karte übereinstimmen, wird der Akteurname aus dem KEY zurückgegeben.
     * @param charNum Der Wert der Zeichennummern der NSC- und Akteurssymbole aus der Map, die wir anhand ihrer ASCII-Werte ausgewählt haben.
     */
    private String getActorName(int charNum){
       String result = null;

       for(Map.Entry<String, String> actor : this.symbols.entrySet()){

            if(actor.getValue().charAt(charNum & 3) == (char)charNum){
                result = actor.getKey();
                break;
            }

       }
       
       return result;
    }

    /**
     * Die Symbole aus der Karte werden mit den in LinkedHasMap definierten Symbolen verglichen
     * Wenn die für den Player definierten Symbole mit dem Symbol aus der Karte übereinstimmen, wird der Player erstellt.
     * Der erstellte Player wird der Actor Array List hinzugefügt.
     */
    private void createPlayer(){
       int charNum = 79;
       String actorName = null;

       for(int y=0; y < map.length; y=y+2){

            for(int x=0; x < map[y].length(); x=x+2){
                charNum = (int)map[y].charAt(x);

                actorName = getActorName(charNum);

                if(actorName == "woman"){
                    player = new Player(x/2, y/2, charNum & 3, field);
                    actors.add(0, player);  
                    break;
                }

            }
        } 
        
       // Fehlercode, wenn der Spieler nicht auf der Karte gefunden werden kann.
       if(player == null)
            throw new IllegalArgumentException("Die Datei enthält nicht genau einen Player.");

    }

    /**
     * Die Symbole aus der Karte werden mit den in LinkedHasMap definierten Symbolen verglichen.
     * Wenn die für den Walker definierten Symbole mit dem Symbol aus der Karte übereinstimmen, wird der Walker erstellt.
     * Der erstellte Walker wird der Actor Array List hinzugefügt.
     */
    private void createWalker(){
       int charNum;
       String actorName = null;

       for(int y=0; y < map.length; y=y+2){
            for(int x=0; x < map[y].length(); x=x+2){

                charNum = (int)map[y].charAt(x);

                actorName = getActorName(charNum);

                if(actorName != null && actorName != "woman")
                    actors.add(new Walker(x/2, y/2, charNum & 3, actorName, field, player));

                // Fehlercode für ungültige Symbole, wenn der Akteursname leer ist und sich vom Standardzuordnungszeichen ('O') unterscheidet. 
                if(actorName == null && (char)charNum != defaultMapChar)
                    throw new IllegalArgumentException("Die Datei enthält ungültige Symbole an den Positionen von Gitterknoten.");

            }
        }
    }

    /**
     * Methode zur Erstellung von Spielern und Walkern.
     */
    private void createActors(){
        createPlayer();
        createWalker();
    }

    /**
     * Methode, die die erzeugte Akteursliste zurückgibt.      
     */
    public ArrayList<Actor> getActors(){
        return actors;
    }

    /**
     * Methode zum Zerstören aller Spieler-, Walker- und Kartenobjekte.         
     */
    public void hide(){
       for(GameObject actors : actors){
         actors.setVisible(false);
       }
       
       field.hide();
    }

}
