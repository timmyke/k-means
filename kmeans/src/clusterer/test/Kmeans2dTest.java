package clusterer.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import clusterer.kmeans2d.Kmeans2d;
import clusterer.kmeans2d.Piste;

public class Kmeans2dTest {

    @Test
    public void testLahinKeskus() throws Exception {
        List<Piste> kpt = new ArrayList<Piste>();

        Piste kp1 = new Piste(10.0, 0.0);
        kp1.Group = 0;
        kpt.add(kp1);

        Piste kp2 = new Piste(100.0, 0.0);
        kp2.Group = 1;
        kpt.add(kp2);

        Piste p = new Piste(25.0, 25.0);

        assertTrue(Kmeans2d.lahinKeskus(p, kpt) == 0);
        assertFalse(Kmeans2d.lahinKeskus(p, kpt) == 1);
    }

    @Test
    public void testLiitaLahimpaanKeskukseen() throws Exception {
        List<Piste> pisteet = new ArrayList<Piste>();
        List<Piste> kpt = new ArrayList<Piste>();

        Piste kp1 = new Piste(10.0, 0.0);
        kp1.Group = 0;
        kpt.add(kp1);

        Piste kp2 = new Piste(100.0, 0.0);
        kp2.Group = 1;
        kpt.add(kp2);

        Piste p = new Piste(25.0, 25.0);
        p.Group = 1;

        pisteet.add(p);

        assertTrue(Kmeans2d.liitaLahimpaanKeskukseen(pisteet, kpt));
        assertTrue(p.Group == 0);
    }

    @Test
    public void testEtaisyydenNelio() throws Exception {
        Piste p1 = new Piste(150.0, 200.0);
        Piste p2 = new Piste(10.0, 0.0);
        assertTrue(Kmeans2d.etaisyydenNelio(p1, p2) == ((140.0 * 140.0) + (200.0 * 200.0)));
        assertFalse(Kmeans2d.etaisyydenNelio(p1, p2) == 0.0);
    }

    @Test
    public void testPaivitaKeskipisteet() throws Exception {
        int k = 2;
        List<Piste> pisteet = new ArrayList<Piste>();

        Piste p1 = new Piste(100, 100);
        p1.Group = 0;
        Piste p2 = new Piste(100, 150);
        p2.Group = 0;

        Piste p3 = new Piste(0, 25);
        p3.Group = 1;
        Piste p4 = new Piste(0, 0);
        p4.Group = 1;

        pisteet.add(p1);
        pisteet.add(p2);
        pisteet.add(p3);
        pisteet.add(p4);

        String tulos = "[(100.0, 125.0), (0.0, 12.5)]";

        assertTrue(Kmeans2d.paivitaKeskipisteet(k, pisteet).toString()
                .equals(tulos));
        assertFalse(Kmeans2d.paivitaKeskipisteet(k, pisteet).toString().equals(tulos.substring(0,15) + "]"));

    }

}
