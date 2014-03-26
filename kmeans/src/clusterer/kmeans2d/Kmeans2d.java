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
     * @param pisteet
     *            Pisteet
     * @param k
     *            Kuinka moneen klusteriin jaetaan i
     * @param maxIter
     *            Iteraatioiden maksimimäärä
     * @return Tarkennetut keskipisteet
     */
    public static List<Piste> GetKmeans2d(List<Piste> pisteet, int k,
                                          int maxIter) throws Exception {
        if (pisteet.size() < k) {
            throw new Exception("Liian vähän pisteitä.");
        }

        // Valitaan randomisti klustereiden keskusta alkupisteistä.
        List<Piste> keskipisteet = new ArrayList<Piste>(k);

        int i = 0;
        while (i < k) {
            int pointer = (int) (Math.random() * (pisteet.size() + 1));

            Piste current = pisteet.get(pointer);
            if (!keskipisteet.contains(current)) {
                current.Group = i;
                keskipisteet.add(current);
                i++;
            }
        }

        // Liittään pisteen arvottuihin klusterihin
        LiitaLahimpaanKeskukseen(pisteet, keskipisteet);

        // Tarkenetaan satunnaisesti valittuja klustereita
        int iter = 0;
        do {
            // Päivitetään klusterin keskipisteiden sijainti klusteriin
            // liitettyjen pisteiden perusteella.
            keskipisteet = PaivitaKeskipisteenSijainti(k, pisteet);
            // Liitetään pisteet tarkennettuihin klustereihin.
            LiitaLahimpaanKeskukseen(pisteet, keskipisteet);
            iter++;
        } while (iter < maxIter);

        return keskipisteet;
    }

    /**
     * Palauttaa pistetta lahinna olevan klusterin numeron.
     *
     * @param vertailupiste
     *            piste
     * @param keskipisteet
     *            klustereiden keskipisteet
     * @return lahimman klusterin numero
     */
    private static int
            LahinKeskus(Piste vertailupiste, List<Piste> keskipisteet) {
        Piste min = keskipisteet.get(0);
        double dis = Double.POSITIVE_INFINITY;
        for (Piste p : keskipisteet) {
            /*
             * Ei tarvitse laskea kahden pisteen välistä etaisyytta, koska
             * etäisyyden neliöistä voidaan jo suoraan päätellä kumpi
             * pisteistä on lähimpänä.
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
     * Liittää pisteet lähimpään klusteriin
     *
     * @param pisteet
     *            Pisteet, jotka liitetään klusteriin (huom. sivuvaikutus)
     * @param keskipisteet
     *            Klusterit
     */
    private static void LiitaLahimpaanKeskukseen(List<Piste> pisteet,
                                                 List<Piste> keskipisteet) {
        for (Piste p : pisteet) {
            p.Group = LahinKeskus(p, keskipisteet);
        }
    }

    /**
     * Laskee kahden pisteen etäisyyden neliön.
     *
     * @param a
     *            1. piste
     * @param b
     *            2. piste
     * @return etaisyyden nelio
     */
    private static double EtaisyydenNelio(Piste a, Piste b) {
        return Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2);
    }

    /**
     * Päivittää klusterin keskipisteen siihen kuuluvien pisteiden sijainnin
     * perusteella
     *
     * @param k
     *            Klusterin numero
     * @param pisteet
     *            Kaikki pisteet, joiden perusteella valitaan klusterin
     *            numeron perusteella klusteriin kuuluvat pisteet.
     * @return Uudet keskipisteet
     */
    private static List<Piste> PaivitaKeskipisteenSijainti(int k,
                                                           List<Piste> pisteet) {
        List<double[]> keskipisteet = new ArrayList<double[]>(k);
        for (int i = 0; i < k; i++) {
            double[] kp = new double[3];
            kp[0] = 0;
            kp[1] = 0;
            kp[2] = 0;
            keskipisteet.add(kp);
        }

        for (Piste piste : pisteet) {
            double[] keski = keskipisteet.get(piste.Group);
            // lasketaan klusterin leveys ja korkeus (eli pisteiden kehys)
            keski[0] += piste.X;
            keski[1] += piste.Y;
            keski[2] += 1; // counteri kuinka monta pistettä klusterissa
        }

        List<Piste> uudetKeskipisteet = new ArrayList<Piste>(k);
        int kIndex = 0;
        for (double[] kp : keskipisteet) {
            Piste p = new Piste();
            p.X = kp[0] / kp[2];
            p.Y = kp[1] / kp[2];
            p.Group = kIndex;
            kIndex++;
            uudetKeskipisteet.add(p);
        }

        return uudetKeskipisteet;
    }
}
