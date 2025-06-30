package de.uni_bremen.pi2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Instanzen dieser Klasse bieten ein Bild in verschiedenen Formaten an.
 */
class ImageHandler
{
    /** Der Farbwert schwarzer Pixel (RGBA = (0, 0, 0, 255)). */
    private static final int BLACK = 0xff000000;

    /** Der Farbwert weißer Pixel (RGBA = (255, 255, 255, 255)). */
    private static final int WHITE = 0xffffffff;

    /** Das Originalbild. */
    private final BufferedImage original;

    /** Ein Grauwertbild. null, wenn es noch nicht berechnet wurde. */
    private BufferedImage grayscale;

    /** Ein Schwarzweißbild. null, wenn es noch nicht berechnet wurde. */
    private BufferedImage blackAndWhite;

    /** Ein segmentiertes Bild. null, wenn es noch nicht berechnet wurde. */
    private BufferedImage segmented;

    /** Zufallszahlengenerator zum Erzeugen zufälliger Farben. */
    private final Random random = new Random(0);

    /**
     * Konstruktor.
     * @param image Das Bild, das in verschiedenen Formaten bereitgestellt wird.
     */
    ImageHandler(final BufferedImage image)
    {
        original = image;
    }

    /**
     * Liefert das Originalbild.
     * @return Das Originalbild.
     */
    BufferedImage getOriginal()
    {
        return original;
    }

    /**
     * Liefert das Bild in Grauwerten.
     * Das Grauwertbild wird bei Bedarf erst berechnet.
     * @return Das Grauwertbild.
     */
    BufferedImage getGrayscale()
    {
        if (grayscale == null) {
            final BufferedImage image = getOriginal();
            grayscale = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    grayscale.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return grayscale;
    }

    /**
     * Liefert das Bild in Schwarzweiß.
     * Das Schwarzweißbild wird bei Bedarf erst berechnet, indem für das Grauwertbild der sog. Otsu-Schwellwert
     * bestimmt wird. Alle Grautöne unterhalb des Schwellwerts werden schwarz, alle darüber weiß.
     * @return Das Schwarzweißbild.
     */
    BufferedImage getBlackAndWhite()
    {
        if (blackAndWhite == null) {
            final BufferedImage image = getGrayscale();
            blackAndWhite = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            final int threshold = otsuThreshold(image);
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    blackAndWhite.setRGB(x, y, (image.getRGB(x, y) & 255) <= threshold ? 0 : -1);
                }
            }
        }
        return blackAndWhite;
    }

    /**
     * Bestimmung des Otsu-Schwellwerts.
     * Vgl. z.B. <a href="http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html">hier</a>.
     * @param image Das Grauwertbild, zu dem der beste Schwellwert zwischen Schwarz
     *         und Weiß bestimmt wird.
     */
    private int otsuThreshold(final BufferedImage image)
    {
        final int[] histogram = new int[256];
        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                ++histogram[image.getRGB(x, y) & 255];
            }
        }

        int countLow = 0; // Anzahl Pixel bis zum Schwellwert
        int countHigh = image.getWidth() * image.getHeight(); // Anzahl über Schwellwert
        int sumLow = 0; // Gewichtete Summe der Pixel bis zum Schwellwert
        int sumHigh = 0; // Gewichtete Summe der Pixel über dem Schwellwert

        for (int i = 0; i < histogram.length; ++i) {
            sumHigh += histogram[i] * i;
        }

        int bestThreshold = 0;
        double bestVar = 0;
        for (int threshold = 0; threshold < histogram.length; ++threshold) {
            countLow += histogram[threshold];
            countHigh -= histogram[threshold];
            sumLow += histogram[threshold] * threshold;
            sumHigh -= histogram[threshold] * threshold;
            if (countLow > 0 && countHigh > 0) {
                double avgLow = (double) sumLow / countLow;
                double avgHigh = (double) sumHigh / countHigh;
                double diff = avgHigh - avgLow;
                double var = (double) countLow * countHigh * diff * diff;
                if (var > bestVar) {
                    bestVar = var;
                    bestThreshold = threshold;
                }
            }
        }

        return bestThreshold;
    }

    /**
     * Liefert eine zufällige Farbe, die mindestens 50 % Sättigung und Helligkeit hat.
     * @return Die Farbe, deren Rot-, Grün- und Blauanteile in einem int kodiert sind.
     */
    private int getRandomColor()
    {
        return Color.HSBtoRGB(random.nextFloat(),
                random.nextFloat() * 0.5f + 0.5f,
                random.nextFloat() * 0.5f + 0.5f);
    }

    /**
     * Liefert ein segmentiertes Bild, bei dem alle zusammenhängenden, weißen Regionen
     * des Schwarzweißbildes mit zufälligen Farben markiert sind. Das Bild wird erst bei
     * Bedarf berechnet.
     * @return Das segmentierte Bild.
     */
    BufferedImage getSegmented()
    {
        if (segmented == null) {
                    final BufferedImage image = getBlackAndWhite();
                    segmented = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

                    UnionFind uf = new UnionFind(image, WHITE);

                    for (Run run : uf.getRuns()) {
                        Run root = run.getRoot();

                        int color;
                        if (root == run) {
                            color = getRandomColor();
                            root.setColor(color);
                        } else {
                            color = root.getColor();
                        }

                        uf.drawRunSegments(run, segmented, color);
                    }
                }
                return segmented;
    }
}
