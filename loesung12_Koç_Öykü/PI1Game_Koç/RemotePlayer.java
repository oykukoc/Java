import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.IOException;

/**
 * Diese Klasse definiert den Player, der über die Tastatur gesteuert wird.
 * Die Richtungsinformationen der Spieler werden über das Netzwerk via Socket gesendet.
 * Die Richtungsangaben des Spielers bestimmen die Richtungsangaben der ferngesteuerten Figur.
 * @author (Öykü Koç)
 */
class RemotePlayer extends Player
{
    /** Socket für die Kommunikation. */
    private Socket socket = null;

    /**
     * Der Konstruktor, der den Player und die Netzwerkumgebung erstellt.
     * @param x Die x-Koordinate des Spielers.
     * @param y Y-Koordinate des Spielers.
     * @param rotation Richtungsangabe des Spielers (0=Rechts 1=Unten 2=Links 3=Oben).
     * @param field Spielplatz.
     * @param serverAddress IP-Adresse des Computers des ferngesteuerten Charakters.
     * @param serverPort Der Port des Computers der ferngesteuerten Figur.
     */
    RemotePlayer(final int x, final int y, final int rotation, final Field field, final String serverAddress, final int serverPort)
    {
        super(x, y, rotation, field);
        sleep(300);
        try{
            socket = new Socket(serverAddress, serverPort);
        }
        catch(final IOException e){
            System.err.println(serverAddress + ":" + serverPort + " Verbindung zu Serveradresse und Port fehlgeschlagen!");

            setVisible(false);
        }
    }

    /**
     * Diese Methode verwaltet die Bewegung des Spielers und sendet Richtungsinformationen an das Netzwerk.
     * Der Spieler erhält Richtungsinformationen und bewegt sich mit Hilfe der auf der Tastatur eingegebenen Taste.
     * Die Richtungsinformationen des Spielers werden über das Netzwerk via Socket an die andere Partei übertragen.
     */
    @Override
    void act(){
        super.act();

        try{
            socket.getOutputStream().write(getRotation());
            socket.getOutputStream().flush();
        }
        catch(final IOException e){
            System.err.println("Richtungsangaben wurden nicht gesendet!");
            setVisible(false);
        }
    }

    /**
     * Mit dieser Methode werden die Sichtbarkeit und der Netzwerkstatus des Spielers angepasst.
     * Wenn der Spieler versteckt ist, wird der Zug abgebrochen und der Sockel geschlossen.
     * Wenn der Socket geschlossen wird, wird auch die Netzwerkverbindung geschlossen.
     * @params visible Bestimmt den Sichtbarkeitsstatus des Spielers.
     */
    @Override
    public void setVisible(final boolean visible)
    {
        super.setVisible(visible);

        if(!visible && socket != null) {
            try {
                socket.close();
            }
            catch(final IOException e){
                System.err.println("Die Netzwerkverbindung konnte nicht geschlossen werden!");
            }
        }
    }
}

