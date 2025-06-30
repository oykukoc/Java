import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.IOException;

/**
 * Diese Klasse definiert das ferngesteuerte Charakter.
 * Die Richtungsinformation des Charakters wird über das Netzwerk mit Socket empfangen.
 * Die Richtungsinformation des Charakters wird durch die Richtungsinformation des Spielers, der über das Netzwerk kommt, bestimmt.
 * @author (Öykü Koç)
 */
class ControlledPlayer extends Player
{
    /** Die Steckdose, die die Kommunikation ermöglicht. */
    private Socket socket = null;

    /** Server-bildende Steckdose. */
    private ServerSocket serverSocket = null;

    /** Positionsoffsets nach Richtung. */
    private final int [][] offsets = {{1 , 0}, {0, 1}, {-1, 0}, {0, -1}};

    /**
     * Konstrukteur, der die ferngesteuerte Figur und die Netzumgebung erstellt.
     * @param x x-Koordinate des Zeichens.
     * @param y y-Koordinate des Zeichens y.
     * @param rotation Richtungsinformation des Zeichens (0=Rechts 1=Unten 2=Links 3=Oben).
     * @param field Spielplatz.
     * @param serverPort Der Port des Computers des Charakters.
     */
    ControlledPlayer(final int x, final int y, final int rotation, final Field field, final int serverPort)
    {
        super(x, y, rotation, field);

        try{
            serverSocket = new ServerSocket(serverPort);
            socket = serverSocket.accept();
        }
        catch(final IOException e){
            System.err.println(serverPort + " auf dem mit einer Nummer versehenen Anschluss, konnte die Verbindung nicht hergestellt werden.");
            setVisible(false);
        }
    }

    /**
     * Diese Methode empfängt Richtungsinformationen aus dem Netzwerk und steuert die Bewegung der Figur.
     * Je nach den vom Spieler erhaltenen Richtungsangaben erhält die Spielfigur eine bestimmte Richtung und Bewegung.
     * Die an der Kommunikationsbuchse ankommende Richtungsinformation wird gelesen.
     * Die Information über die Leserichtung wird auf das Zeichen gesetzt. 
     * Die Figur bewegt sich entsprechend den eingestellten Richtungsangaben.
     * Wenn die Verbindung beendet wird, wird der Wert als (-1) gelesen.
     */
    @Override
    void act(){
       try{
            int direction = socket.getInputStream().read();

            if (direction != -1){
                setRotation(direction);
                setLocation(getX() + offsets[getRotation()][0], getY() + offsets[getRotation()][1]);
                playSound("step");
                sleep(200);

            }
            else{
                System.err.println("Die Netzwerkverbindung wurde abgebrochen.");
                setVisible(false);
            }
        }
        catch(final IOException e) {
            System.err.println("Es wurde keine Information über die Richtung des Spielers empfangen.");
            setVisible(false);
        }
    }

    /**
     * Diese Methode setzt die Sichtbarkeit und den Netzwerkstatus des Charakters.
     * Wenn die Figur versteckt ist, verschwindet die Bewegung und der Sockel wird geschlossen.
     * Wenn der Socket geschlossen wird, wird auch die Netzwerkverbindung geschlossen.
     * @params visible  Bestimmt den Sichtbarkeitsstatus des Charakters.
     */
    @Override
    public void setVisible(final boolean visible)
    {
        super.setVisible(visible);

        if(!visible && socket != null){
            try{
                socket.close();
            }
            catch(final IOException e){
                System.err.println("Die Netzwerkverbindung konnte nicht geschlossen werden!");
            }
        }
    }
}

