/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kmeans.Clusterer;

/**
 *
 * @author timi
 */
public class Piste {
    // Nää on perkele propertyjä.
    // Eli luvallista käyttää suoraan niitä perkeleen propertyjä
    // ilman aksessorei. Eli ei vikistä siellä.
    public double X;
    public double Y;
    public int Group;
    public Piste(double x, double y) {
    	this.X = x;
    	this.Y = y;
    }
    public Piste() {}
}
