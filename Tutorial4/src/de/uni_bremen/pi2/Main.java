package de.uni_bremen.pi2;

import java.util.Arrays;
import java.util.Scanner;

import static de.uni_bremen.pi2.Player.EMPTY;
import static de.uni_bremen.pi2.Result.*;

/**
 * Die Hauptklasse des Spiels.
 * @author Thomas Röfer
 */
public class Main
{
    /**
     * Das Hauptprogramm des Spiels. Liest Zahlen von der Konsole ein
     * und übergibt diese an das Spiel. Gibt passende Meldungen zu den
     * Rückgaben des Spiels aus. Das Spiel endet, wenn Mensch oder
     * Maschine gewonnen hat oder keine Züge mehr möglich sind. Zudem
     * endet es, wenn etwas anderes als eine Zahl eingegeben wird.
     * Tastatureingaben sind durch Leerraum voneinander getrennt.
     * Zeilen- und Spaltennummern sind dabei 1-basiert, während sie
     * intern 0-basiert sind.
     * @param args Werden ignoriert.
     */
    public static void main(final String[] args)
    {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Vier in einer Reihe\n");
        System.out.println("Spielfeldgröße und maximale Suchtiefe?");

        final int size = scanner.nextInt();
        final Player[][] field = new Player[size][size];
        for (final Player[] row : field) {
            Arrays.fill(row, EMPTY);
        }
        final FourInARow game = new FourInARow(field, scanner.nextInt());

        System.out.println("Für jeden Zug jeweils Zeilennummer und Spaltennummer eingeben.");
        System.out.println(game);

        Result result = CONTINUE;

        // Spiel läuft, solange kein Ergebnis feststeht und die Eingabe eine Zahl ist.
        while (result == CONTINUE && scanner.hasNextInt()) {
            while (scanner.hasNextInt()) {
                // Zeile und Spalte einlesen.
                final int row = scanner.nextInt() - 1;
                if (scanner.hasNextInt()) {
                    final int column = scanner.nextInt() - 1;

                    // Überprüfen.
                    if (row < 0 || row >= field.length || column < 0 || column >= field[row].length) {
                        System.out.println("Zug außerhalb des Feldes");
                    }
                    else if (field[row][column] != EMPTY) {
                        System.out.println("Feld bereits belegt");
                    }
                    else {
                        // Zeile und Spalte akzeptiert → Zug ausführen.
                        result = game.humanMove(row, column);

                        // Spielfeld nach dem Zug ausgeben.
                        System.out.println(game);

                        // Computer kommt nur dran, wenn das Spiel noch nicht beendet ist.
                        if (result == CONTINUE) {
                            result = game.computerMove();
                            System.out.println("\n" + game);
                            if (result == CONTINUE) {
                                continue;
                            }
                        }

                        // Alle Ergebnisse außer CONTINUE führen zu einer Ausgabe.
                        System.out.println(result + "!");
                        break;
                    }
                }
            }
        }
    }
}
