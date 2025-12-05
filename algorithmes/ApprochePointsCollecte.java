package algorithmes;

import java.util.*;

import modele.Graphe;
import modele.Sommet;
import modele.Tournee;

/**
 * Classe implémentant l'approche Plus Proche Voisin pour le Thème 2.
 * Gère le calcul des tournées avec capacité du camion en choisissant toujours
 * le point le plus proche non visité.
 */
public class ApprochePointsCollecte {
    /**
     * Calcule les tournées avec l'approche Plus Proche Voisin et capacité du
     * camion.
     * 
     * @param g              Le graphe routier.
     * @param depot          Le dépôt.
     * @param tousLesPoints  Tous les points.
     * @param capaciteCamion La capacité du camion.
     * @return Liste des tournées.
     */
    public static List<Tournee> approcheAvecCapacite(Graphe g, Sommet depot,
            List<Sommet> tousLesPoints, double capaciteCamion) {

        System.out.println("\n" + "=".repeat(75));
        System.out.println("   THÈME 2 : COLLECTE AVEC CAPACITÉ CAMION (Plus Proche Voisin)");
        System.out.printf("Capacité par camion : %.1f tonnes%n", capaciteCamion);
        System.out.println("=".repeat(75));

        // Filtrer les points valides (exclure le dépôt)
        List<Sommet> points = new ArrayList<>();
        for (Sommet s : tousLesPoints) {
            if (!s.equals(depot) && s.getQuantiteDechets() > 0.01) {
                points.add(s);
            }
        }

        if (points.isEmpty()) {
            System.out.println("Aucun point de collecte à traiter.");
            appuyerPourContinuer();
            return new ArrayList<>();
        }

        List<Tournee> tournees = new ArrayList<>();
        Set<Sommet> nonVisites = new HashSet<>(points);
        int numeroTournee = 1;

        while (!nonVisites.isEmpty()) {
            Tournee tournee = new Tournee(numeroTournee++, depot);
            double chargeActuelle = 0.0;
            Sommet courant = depot;

            System.out.printf("%n=== TOURNÉE N° %d ===%n", numeroTournee - 1);

            boolean progres = true;
            while (progres && !nonVisites.isEmpty()) {
                progres = false;
                Sommet meilleur = null;
                double distMin = Double.MAX_VALUE;

                for (Sommet candidat : nonVisites) {
                    double dist = Dijkstra.calculerDistance(g, courant, candidat);

                    // Condition stricte : chemin existant + capacité respectée
                    if (dist < Double.MAX_VALUE &&
                            chargeActuelle + candidat.getQuantiteDechets() <= capaciteCamion + 1e-9 &&
                            dist < distMin) {

                        distMin = dist;
                        meilleur = candidat;
                        progres = true;
                    }
                }

                if (meilleur != null) {
                    tournee.ajouterPoint(meilleur, distMin);
                    chargeActuelle += meilleur.getQuantiteDechets();
                    nonVisites.remove(meilleur);
                    courant = meilleur;
                    System.out.printf("   + %s [%.1f t] --> charge = %.2f t (%.2f km)%n",
                            meilleur.getId(), meilleur.getQuantiteDechets(), chargeActuelle, distMin);
                }
            }

            double retour = Dijkstra.calculerDistance(g, courant, depot);
            if (retour >= Double.MAX_VALUE)
                retour = 0.0;
            tournee.ajouterRetourDepot(retour);
            tournees.add(tournee);
            System.out.printf("   Retour dépôt : %.2f km%n", retour);
        }

        // Résumé final
        System.out.println("\n" + "=".repeat(75));
        System.out.println("                   RÉSULTATS FINAUX - THÈME 2");
        System.out.println("=".repeat(75));
        double totalKm = 0.0;
        for (Tournee t : tournees) {
            t.afficher();
            totalKm += t.getDistanceParcourue();
        }
        System.out.printf("%nCamions nécessaires       : %d%n", tournees.size());
        System.out.printf("Distance totale parcourue : %.2f km%n", totalKm);
        System.out.printf("Points collectés          : %d / %d%n", points.size() - nonVisites.size(), points.size());
        if (!nonVisites.isEmpty()) {
            System.out.println("Points inaccessibles      : " + nonVisites.size());
        }
        System.out.println("=".repeat(75));

        appuyerPourContinuer();
        return tournees;
    }

    /**
     * Méthode pour mettre en pause l'exécution (appuyer sur Entrée).
     */
    private static void appuyerPourContinuer() {
        System.out.println("\nAppuyez sur Entrée pour revenir au menu...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignorer
        }
    }
}
