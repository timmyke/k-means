/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clusterer.kmeans2d;

/**
 *
 * @author timi
 */
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author timi
 */
public class Kmeans2d {

    public Kmeans2d() {
        System.out.println("Trol");
    }

    /**
     * Laskee K-meanin
     *
     * @param pisteet Pisteet
     * @param clusterNumber Kuinka moneen klusteriin jaetaan
     * @param maxIter Iteraatioiden maksimimäärä
     * @return
     */
    public static ArrayList<Piste> GetKmeans(List<Piste> pisteet, int clusterNumber, int maxIter) { //throws Exception {
//        if (pisteet.size() < clusterNumber) {
//            throw new Exception("Liian vähän pisteitä.");
//        }

        // Valitaan randomisti clusterNumberien määrä alkupisteitä
        ArrayList<Piste> keskipisteet = new ArrayList<Piste>(clusterNumber);

        int i = 0;
        while (i < clusterNumber) {
            int pointer = (int) (Math.random() * (pisteet.size() + 1));

            Piste current = pisteet.get(pointer);
            if (!keskipisteet.contains(current)) {
                current.Group = i;
                keskipisteet.add(current);
                i++;
            }
        }

        for (Piste p : pisteet) {
            p.Group = LahinKeskus(p, keskipisteet);
        }

        int iter = 0;
        do {
            for (int j = 0; j < keskipisteet.size(); j++) {
                keskipisteet.set(j, PaivitaKeskipisteenSijainti(j, pisteet));
            }
            for (Piste p : pisteet) {
            	p.Group = LahinKeskus(p, keskipisteet);
            }
            iter++;
        } while (iter < maxIter);

        return keskipisteet;

    }

    private static int LahinKeskus(Piste vertailupiste, ArrayList<Piste> keskipisteet) {
        Piste min = keskipisteet.get(0);
        double dis = Double.POSITIVE_INFINITY;
        for (Piste p : keskipisteet) {
            /*
             * Huomauautus tarkastajalle: Ei tarvitse laskea kahden pisteen välistä matkaa, koska
             * etäisyyden neliöistä voidaan jo suoraan päätellä kumpi mikä pisteistä on lähimpänä.
             * Ts. säästytään laskemasta turhaan neliöjuurta.
             */
            double d = EtaisyydenNelio(vertailupiste, p);
            if (d < dis) {
                min = p;
                dis = d;
            }
        }
        return min.Group;
    }

    /**
     * Laskee kahden pisteen etäisyyden neliön.
     *
     * @param a
     * @param b
     * @return
     */
    private static double EtaisyydenNelio(Piste a, Piste b) {
        return Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2);
    }

    private static Piste PaivitaKeskipisteenSijainti(int index, List<Piste> pisteet) {
        double x = 0, y = 0;
        
        // Counteri keskiarvon laskemista varten.
        int c = 0;
        for (Piste piste : pisteet) {
            if (piste.Group == index) {
                x += piste.X;
                y += piste.Y;
                c++;
            }
        }
        Piste p = new Piste();
        p.X = x/c;
        p.Y = y/c;
        p.Group = index;
        return p;
        
        
    }
}
