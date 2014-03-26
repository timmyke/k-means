package clusterer.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clusterer.kmeans2d.Kmeans2d;
import clusterer.kmeans2d.Piste;

public class Testi extends JPanel {
    private static final long serialVersionUID = 1L;
    final int PAD = 20;

    final List<Piste> pisteet;
    final List<Piste> kpt;

    final Color[] varit = { Color.RED, Color.BLUE, Color.BLACK, Color.CYAN,
            Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW, Color.GRAY };

    /**
     * Konstruktori
     *
     * @param pisteet
     *            pisteet
     * @param kpt
     *            klustereiden keskipisteet
     */
    public Testi(List<Piste> pisteet, List<Piste> kpt) {
        this.pisteet = pisteet;
        this.kpt = kpt;
//        this.setMaximumSize(new Dimension(500, 500));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = this.getWidth();
        int h = this.getHeight();

        // piirra koordinaattiviivat
        g2.draw(new Line2D.Double(this.PAD, this.PAD, this.PAD, h - this.PAD));
        g2.draw(new Line2D.Double(this.PAD, h - this.PAD, w - this.PAD, h
                - this.PAD));

        double scaleX = (w - (2 * this.PAD)) / 1000.0;
        double scaleY = (h - (2 * this.PAD)) / 1000.0;

        // piirra pisteet
        for (Piste p : this.pisteet) {
            g2.setPaint(this.varit[p.Group % 9]);
            double x = this.PAD + (p.X * scaleX);
            double y = h - this.PAD - (p.Y * scaleY);
            g2.fill(new Ellipse2D.Double(x, y, 4, 4));
        }

        // piirra keskipisteet
        for (Piste p : this.kpt) {
            g2.setPaint(Color.green);
            double x = this.PAD + (p.X * scaleX);
            double y = h - this.PAD - (p.Y * scaleY);
            g2.fill(new Ellipse2D.Double(x, y, 4, 4));
        }
    }

    public static void main(String[] args) {
        List<Piste> tempKpt = new ArrayList<Piste>();
        List<Piste> pisteet = new ArrayList<Piste>();
        Random rnd = new Random();

        // 3-9 klusteria
        int k = 3 + rnd.nextInt(7);
        for (int i = 0; i < k; i++) {
            int x = 100 + rnd.nextInt(800);
            int y = 100 + rnd.nextInt(800);
            Piste kp = new Piste(x, y);
            tempKpt.add(kp);

            // 100-200 pistetta / klusteri
            int n = 100 + rnd.nextInt(100);
            for (int j = 0; j < n; j++) {

                // +- 100 pistetta sen keskipisteesta
                double nX = (x - 100) + rnd.nextInt(200);
                double nY = (y - 100) + rnd.nextInt(200);
                Piste p = new Piste(nX, nY);
                pisteet.add(p);
            }
        }

        // lista algoritmin antamille keskipisteille.
        List<Piste> kpt = new ArrayList<Piste>(k);
        try {
            kpt = Kmeans2d.GetKmeans2d(pisteet, k, 1000);
        } catch (Exception e) {
            return;
        }

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(new Testi(pisteet, kpt));
        f.setTitle("k = " + k);
        f.pack();

        f.setSize(500, 500);
        f.setVisible(true);
    }

} // Testi
