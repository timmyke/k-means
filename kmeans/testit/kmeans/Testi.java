package kmeans;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;

import kmeans.Clusterer.Piste;
import kmeans.Clusterer.Clusterer;

public class Testi extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int[] data = {
        21, 14, 18, 03, 86, 88, 74, 87, 54, 77,
        61, 55, 48, 60, 49, 36, 38, 27, 20, 18
    };
    final int PAD = 20;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();

        
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));

        
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        double xInc = (double)(w - 2*PAD)/(data.length-1);
        double scale = (double)(h - 2*PAD)/getMax();
        
        
        // Mark data points.
        g2.setPaint(Color.red);
        ArrayList<Piste> pisteet = new ArrayList<Piste>();
        for(int i = 0; i < data.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[i];
            Piste p = new Piste(x, y);
            pisteet.add(p);
        }
        ArrayList<Piste> kp = Clusterer.GetKmeans(pisteet, 4, 100);
        
        for (Piste p : pisteet) {
        	if (p.Group == 0) {
            	g2.setPaint(Color.red);
        		g2.fill(new Ellipse2D.Double(p.X, p.Y, 4, 4));
        	}
        	if (p.Group == 1) {
            	g2.setPaint(Color.blue);
        		g2.fill(new Ellipse2D.Double(p.X, p.Y, 4, 4));        		
        	}
        	if (p.Group == 2) {
            	g2.setPaint(Color.cyan);
        		g2.fill(new Ellipse2D.Double(p.X, p.Y, 4, 4));
        	}
        	if (p.Group == 3) {
            	g2.setPaint(Color.black);
        		g2.fill(new Ellipse2D.Double(p.X, p.Y, 4, 4));        		
        	}
        }
        for (Piste p : kp) {
        	g2.setPaint(Color.green);
    		g2.fill(new Ellipse2D.Double(p.X, p.Y, 4, 4));        		
        	
        }
    }

    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Testi());
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}