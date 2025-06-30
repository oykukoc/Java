package de.uni_bremen.pi2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

/**
 * Die Klasse implementiert einen einfachen Bildbetrachter, der Bilder
 * laden und in unterschiedlichen Formaten darstellen kann. Die Darstellung
 * passiert immer unskaliert, wodurch Teile des Bildes verborgen sein können.
 */
class ImageViewer extends JFrame
{
    /** Ein Dateiauswahldialog zum Öffnen einer Bilddatei. */
    private final JFileChooser fileChooser = new JFileChooser(".");

    /** Das Ansichtsmenü. Wird benötigt, um Menüpunkte nachträglich zu aktivieren. */
    private JMenu viewMenu;

    /** Das darzustellende Bild. null, wenn noch kein Bild geladen wurde. */
    private BufferedImage image;

    /** Ein Objekt, das das geladene Bild in verschiedenen Formaten liefern kann. */
    private ImageHandler handler;

    /**
     * Erzeugen des Hauptfensters.
     */
    private ImageViewer()
    {
        super("Bildbetrachter");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createMenu();
        getContentPane().add(new JComponent() {
            public Dimension getPreferredSize() {
                if (image == null) {
                    return new Dimension(300, 300);
                } else {
                    return new Dimension(image.getWidth(), image.getHeight());
                }
            }

            public void paintComponent(Graphics g) {
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                }
            }
        });
        pack();
        setVisible(true);
    }

    /**
     * Erzeugen des Menüs.
     * Von den Einträgen im Ansichtsmenü kann immer nur einer zurzeit
     * ausgewählt sein.
     */
    private void createMenu()
    {
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Datei");
        getJMenuBar().add(fileMenu);

        JMenuItem item = new JMenuItem("Öffnen");
        item.addActionListener(event -> fileOpen());
        fileMenu.add(item);

        item = new JMenuItem("Beenden");
        item.addActionListener(event -> System.exit(0));
        fileMenu.add(item);

        viewMenu = new JMenu("Ansicht");
        getJMenuBar().add(viewMenu);

        final JMenuItem original = new JRadioButtonMenuItem("Original");
        original.addActionListener(event -> {
                original.setSelected(true);
                image = handler.getOriginal();
                getContentPane().repaint();
            });
        viewMenu.add(original);

        final JMenuItem grayscale = new JRadioButtonMenuItem("Grauwert");
        grayscale.addActionListener(event -> {
                grayscale.setSelected(true);
                image = handler.getGrayscale();
                getContentPane().repaint();
            });
        viewMenu.add(grayscale);

        final JMenuItem blackAndWhite = new JRadioButtonMenuItem("Schwarzweiß");
        blackAndWhite.addActionListener(event -> {
                blackAndWhite.setSelected(true);
                image = handler.getBlackAndWhite();
                getContentPane().repaint();
            });
        viewMenu.add(blackAndWhite);

        final JMenuItem segmented = new JRadioButtonMenuItem("Segmentiert");
        segmented.addActionListener(event -> {
                segmented.setSelected(true);
                image = handler.getSegmented();
                getContentPane().repaint();
            });
        viewMenu.add(segmented);

        final ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < viewMenu.getItemCount(); ++i) {
            group.add(viewMenu.getItem(i));
            viewMenu.getItem(i).setEnabled(false);
        }
    }

    /**
     * Laden eines Bildes mithilfe des Dateiauswahldialogs.
     * Wenn ein Bild erfolgreich geladen wurde, wird dieses angezeigt.
     */
    private void fileOpen()
    {
         if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
             try {
                 final BufferedImage newImage = ImageIO.read(fileChooser.getSelectedFile());
                 if (newImage != null) {
                     handler = new ImageHandler(newImage);
                     image = handler.getOriginal();

                     // "Original" auswählen und alle Ansichtseinträge anwählbar machen
                     viewMenu.getItem(0).setSelected(true);
                     for (int i = 0; i < viewMenu.getItemCount(); ++i) {
                         viewMenu.getItem(i).setEnabled(true);
                     }

                     pack();
                     getContentPane().repaint();
                 }
                 else {
                     JOptionPane.showMessageDialog(this,
                            "Die Datei hat keines der unterstützte Formate.",
                            "Fehler beim Öffnen", JOptionPane.ERROR_MESSAGE);
                 }
             }
             catch (final IOException e) {
                 JOptionPane.showMessageDialog(this, "Lesefehler.",
                        "Fehler beim Öffnen", JOptionPane.ERROR_MESSAGE);
             }
         }
    }

    /**
     * Das Hauptprogramm erzeugt das Fenster.
     * @param args Die Argumente werden ignoriert.
     */
    public static void main(final String[] args)
    {
        new ImageViewer();
    }
}
