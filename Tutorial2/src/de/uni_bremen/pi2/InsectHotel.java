package de.uni_bremen.pi2;

// INVALID, VALID und SOLVED können direkt verwendet werden.

import java.util.Arrays;

import static de.uni_bremen.pi2.InsectHotel.Result.*;

/**
 * Author Öykü Koç
 */
public class InsectHotel {
    /**
     * Die drei möglichen Ergebnisse der Methode {@link #check}.
     * INVALID: Die aktuelle Teilbelegung des Hotels ist bereits ungültig.
     * VALID: Die aktuelle Teilbelegung des Hotels ist gültig.
     * SOLVED: Das Hotel ist voll belegt und gültig.
     */
    enum Result {INVALID, VALID, SOLVED}

    /**
     * Die Methode füllt ein zum Teil belegtes Hotel mit weiteren
     * Einträgen auf. Dabei werden die weiteren Einträge der Reihe nach
     * von oben links nach unten rechts eingetragen (horizontal zuerst).
     * Dabei bleiben die existierenden Einträge erhalten und die neuen
     * Einträge ersetzen nur die Lücken (' ').
     *
     * @param hotel   Das zum Teil belegte Hotel. Wird nicht verändert.
     *                {@see #solve}.
     * @param entries Eine Folge aus 'O' und 'X', die in die Lücken in
     *                {@code hotel} eintragen wird.
     * @return Ein Hotel, bei dem {@code entries} in die Lücken von
     * {@code hotel} eingesetzt wurde, zumindest so weit, wie
     * {@code entries} gereicht hat. Da {@code hotel} nicht geändert
     * werden darf, ist dies ein neues Objekt.
     */
    static String[] fill(final String[] hotel, final String entries) {

        final StringBuilder sb = new StringBuilder();

        for (String value : hotel) {
            sb.append(value);
        }

        for (char character : entries.toCharArray()) {
            final int position = sb.indexOf(" ");
            sb.setCharAt(position, character);
        }

        final int lengthOfRow = hotel[0].length();
        for (int i = 0; i < hotel.length; i++) {
            hotel[i] = sb.substring(i * lengthOfRow, i * lengthOfRow + lengthOfRow);
        }

        return hotel;
    }

    /**
     * Die Methode überprüft, ob ein Hotel gültig (teil-) belegt ist.
     * Es dürfen horizontal und vertikal maximal jeweils zwei gleiche
     * Einträge ('O' bzw. 'X') aufeinander folgen. In jeder voll
     * ausgefüllten Zeile und Spalte müssen gleich viele Einträge beider
     * Sorten stehen.
     *
     * @param hotel Das zum Teil belegte Hotel. {@see #solve}.
     * @return INVALID: Wenn eine der Anforderungen nicht erfüllt ist – oder
     * besser – auch mit späteren Eintragungen nicht mehr erfüllt
     * werden kann.
     * VALID: Bisher ist keine Anforderung verletzt und mit weiteren
     * Eintragungen könnte noch eine gültige Lösung entstehen.
     * SOLVED: Das Hotel ist vollständig belegt und keine der
     * Anforderungen ist verletzt.
     */
    static Result check(final String[] hotel) {
        int X_count;
        int O_count;
        int blank_Count = 0;
        String colValue = "";

        // GIBT EINE FEHLERMELDUNG AUS, WENN ER NULL ODER LEER IST
        if (hotel.length == 0)
            throw new IllegalArgumentException("String-Array-Parameter kann nicht leer sein!");

        if (hotel == null)
            throw new IllegalArgumentException("String-Array-Parameter kann keinen Nullwert annehmen!");

        // HORIZONTALE KONTROLLE (VON LINKS NACH RECHTS) -> UNGÜLTIGE STEUERUNG.
        for (String row : hotel) {

            if (row.contains("XXX") || row.contains("OOO"))
                return Result.INVALID;

            X_count = 0;
            O_count = 0;
            blank_Count = 0;

            for (char character : row.toCharArray()) {
                if (character == 'X')
                    X_count++;
                else if (character == 'O')
                    O_count++;
                else if (character == ' ')
                    blank_Count++;
            }

            if (blank_Count == 0) {
                if (X_count != O_count)
                    return Result.INVALID;
            }
        }

        // VERTIKALE STEUERUNG (VON OBEN NACH UNTEN) -> UNGÜLTIGE STEUERUNG.
        for (int i = 0; i < hotel.length; i++) {
            X_count = 0;
            O_count = 0;
            blank_Count = 0;

            for (String row : hotel) {
                colValue += row.charAt(i);

                if (colValue.toString().contains("XXX") || colValue.toString().contains("OOO")) {
                    return Result.INVALID;
                }

                if (row.charAt(i) == 'X')
                    X_count++;
                else if (row.charAt(i) == 'O')
                    O_count++;
                else if (row.charAt(i) == ' ')
                    blank_Count++;
            }

            if (blank_Count == 0) {
                if (X_count != O_count)
                    return Result.INVALID;
            }
        }

        // GÜLTIGE STEUERUNG.
        if (blank_Count > 0) {
            return Result.VALID;
        }

        return Result.SOLVED;
    }


    /**
     * Die Methode bekommt ein zum Teil belegtes Hotel übergeben und gibt
     * ein gültig voll belegtes Hotel zurück, wenn dies möglich ist. Die
     * Details, was "gültig" bedeutet, stehen auf dem Übungsblatt.
     *
     * @param hotel Das zum Teil belegte Hotel. Es besteht aus den Zeichen
     *              ' ', 'O' und 'X'. Alle Zeilen müssen dieselbe Länge
     *              haben. Weder der Parameter noch eine seiner Zeilen
     *              dürfen null sein. Wenn von diesen Vorgaben, abgewichen
     *              wird, ist das Verhalten undefiniert.
     * @return Ein gültig voll belegtes Hotel oder null, wenn es keine
     * gültige Belegung gibt.
     */
    public static String[] solve(final String[] hotel) {

        //GIBT EINE FEHLERMELDUNG AUS, WENN DAS FELD LEER IST ODER WENN DIE GRÖSSE UND DIE ANZAHL DER ELEMENTE KEIN VIELFACHES VON ZWEI SIND
        if (hotel == null)
            throw new IllegalArgumentException("String-Array-Parameter kann keinen Nullwert annehmen!");

        if (hotel.length == 0)
            throw new IllegalArgumentException("String-Array-Parameter darf nicht leer sein!");

        if (hotel.length % 2 != 0 || hotel[0].length() % 2 != 0) {
            String throwMessage = "\n" + "Aufgrund von Kontrollvorschriften dürfen sich die Größe des Feldes und die Anzahl der Elemente nicht um Vielfache von 2 unterscheiden!";
            throwMessage += "\n" + "X- und O-Zeichen müssen horizontal und vertikal in gleicher Anzahl vorhanden sein.";

            throw new IllegalArgumentException(throwMessage);
        }

        StringBuilder newData = new StringBuilder();

        while (true) {
            final String[] copyOfHotel = hotel.clone();

            fill(copyOfHotel, newData.toString());

            Result result = check(copyOfHotel);
            if (result == Result.VALID) {
                newData.append("O");
            } else if (result == Result.INVALID) {
                final int position = newData.lastIndexOf("O");
                if (position == -1) {
                    return null;
                }
                newData.delete(position, newData.length());
                newData.append("X");
            } else if (result == Result.SOLVED) {
                return copyOfHotel;
            }
        }
    }
}