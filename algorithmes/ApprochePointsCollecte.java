package algorithmes;

import java.util.*;
import modele.*;
import utils.Utils;

/**
 * Approche Plus Proche Voisin + capacité camion (Thème 2)
 */
public class ApprochePointsCollecte {

    public static List<Tournee> approcheAvecCapacite(Graphe g, Sommet depot,
            List<Sommet> tousLesPoints, double capaciteCamion) {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("   THÈME 2 : COLLECTE AVEC CAPACITÉ CAMION (Plus Proche Voisin)");
        System.out.printf("   Capacité par camion : %.1f tonnes%n", capaciteCamion);
        System.out.println("=".repeat(80));

        // 1. Filtrer les points à collecter (hors dépôt + quantité > 0)
        List<Sommet> points = new ArrayList<>();
        for (Sommet s : tousLesPoints) {
            if (!s.equals(depot) && s.getQuantiteDechets() > 0.01) {
                points.add(s);
            }
        }

        if (points.isEmpty()) {
            System.out.println("Aucun point de collecte à traiter.");
            Utils.appuyerPourContinuer();
            return new ArrayList<>();
        }

        List<Tournee> tournees = new ArrayList<>();
        Set<Sommet> nonVisites = new TreeSet<>(Comparator.comparing(Sommet::getId)); // ← CORRIGÉ : TreeSet trié par ID
        nonVisites.addAll(points);
        int numeroTournee = 1;

        while (!nonVisites.isEmpty()) {
            Tournee tournee = new Tournee(numeroTournee++, depot);
            double charge = 0.0;
            Sommet courant = depot;

            System.out.printf("%n=== TOURNÉE N°%d ===%n", numeroTournee - 1);

            boolean ajoutPossible = true;
            while (ajoutPossible && !nonVisites.isEmpty()) {
                ajoutPossible = false;
                Sommet meilleur = null;
                double distMin = Double.MAX_VALUE;

                for (Sommet candidat : nonVisites) {
                    double dist = Dijkstra.calculerDistance(g, courant, candidat);

                    if (dist < Double.MAX_VALUE &&
                            charge + candidat.getQuantiteDechets() <= capaciteCamion + 1e-9 &&
                            dist < distMin) {

                        distMin = dist;
                        meilleur = candidat;
                        ajoutPossible = true;
                    }
                }

                if (meilleur != null) {
                    tournee.ajouterPoint(meilleur, distMin);
                    charge += meilleur.getQuantiteDechets();
                    nonVisites.remove(meilleur);
                    courant = meilleur;
                    System.out.printf("   + %s [%.1f t] --> %.2f km%n",
                            meilleur.getId(), meilleur.getQuantiteDechets(), distMin);
                }
            }

            double retour = Dijkstra.calculerDistance(g, courant, depot);
            if (retour >= Double.MAX_VALUE) {
                System.out.println("   IMPOSSIBLE DE REVENIR AU DÉPÔT DEPUIS " + courant.getId() + " !");
                System.out.println("   Le camion est bloqué - tournée incomplète.");
                retour = 0.0;
            }
            tournee.ajouterRetourDepot(retour);
            tournees.add(tournee);
            System.out.printf("   Retour dépôt : %.2f km%n", retour);
        }

        // Résumé final
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                   RÉSULTATS FINAUX - THÈME 2");
        System.out.println("=".repeat(80));
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
        System.out.println("=".repeat(80));

        Utils.appuyerPourContinuer();
        return tournees;
    }

}