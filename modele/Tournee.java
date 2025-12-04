package modele;

import java.util.*;

/**
 * Classe représentant une tournée de collecte des déchets.
 * Elle encapsule les informations sur une tournée spécifique : numéro, dépôt,
 * points visités, charge totale, et distance parcourue.
 * Utilisée pour le Thème 2 – Optimisation des ramassages avec capacité du
 * camion.
 */
public class Tournee {
    private int numero; // Numéro de la tournée (ex: 1, 2, 3...)
    private Sommet depot; // Dépôt de départ et retour
    private List<Sommet> pointsVisites = new ArrayList<>(); // Points de collecte visités
    private double chargeTotale = 0.0; // Charge totale des déchets collectés (en tonnes)
    private double distanceParcourue = 0.0; // Distance totale parcourue (en km)

    /**
     * Constructeur de la tournée.
     * Initialise le numéro et le dépôt.
     * 
     * @param numero Le numéro de la tournée.
     * @param depot  Le dépôt de départ.
     */
    public Tournee(int numero, Sommet depot) {
        this.numero = numero;
        this.depot = depot;
    }

    /**
     * Ajoute un point de collecte à la tournée et met à jour la charge et la
     * distance.
     * 
     * @param point                   Le point de collecte à ajouter.
     * @param distanceDepuisPrecedent La distance depuis le point précédent.
     */
    public void ajouterPoint(Sommet point, double distanceDepuisPrecedent) {
        pointsVisites.add(point);
        chargeTotale += point.getQuantiteDechets();
        distanceParcourue += distanceDepuisPrecedent;
    }

    /**
     * Ajoute le retour au dépôt et met à jour la distance.
     * 
     * @param distanceRetour La distance du dernier point au dépôt.
     */
    public void ajouterRetourDepot(double distanceRetour) {
        distanceParcourue += distanceRetour;
    }

    /**
     * Affiche les détails de la tournée (charge, distance, itinéraire).
     */
    public void afficher() {
        System.out.printf("%nTOURNÉE #%d --> Charge: %.2f t | Distance: %.2f km%n",
                numero, chargeTotale, distanceParcourue);
        System.out.print("   Dépôt");
        for (Sommet s : pointsVisites) {
            System.out.printf(" --> %s[%.1ft]", s.getId(), s.getQuantiteDechets());
        }
        System.out.println(" --> Dépôt");
    }

    /**
     * Récupère la distance parcourue.
     * 
     * @return La distance totale en km.
     */
    public double getDistanceParcourue() {
        return distanceParcourue;
    }

    /**
     * Récupère la liste des points visités.
     * 
     * @return Liste des points.
     */
    public List<Sommet> getPointsVisites() {
        return pointsVisites;
    }
}
