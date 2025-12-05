package vue;

import algorithmes.PostierChinois;
import controleur.ControleurGraphe;
import modele.*;
import java.util.*;
import utils.Utils;

/**
 * Classe pour l'interface console (Vue dans MVC).
 * Gère tous les affichages et saisies utilisateur.
 */
public class VueConsole {
    public final Scanner scanner = new Scanner(System.in);

    public void afficherGraphe(Graphe g) {
        g.afficherGraphe();
    }

    /**
     * Affiche un chemin.
     * 
     * @param chemin Le chemin à afficher.
     */
    public void afficherChemin(List<Sommet> chemin) {
        System.out.print("Chemin : ");
        for (int i = 0; i < chemin.size(); i++) {
            System.out.print(chemin.get(i).getId());
            if (i < chemin.size() - 1)
                System.out.print(" → ");
        }
        System.out.println();
    }

    /**
     * Affiche le résultat du Postier Chinois.
     * 
     * @param r Le résultat.
     * @param g Le graphe.
     */
    public void afficherResultatPostier(PostierChinois.ResultatPostierChinois res, Graphe g) {
        PostierChinois.afficherResultat(res, g);
    }

    /**
     * Consulte les quantités de déchets.
     * 
     * @param g Le graphe.
     */
    public void consulterQuantitesDechets(Graphe g) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("       CONSULTATION DES QUANTITÉS DE DÉCHETS À RÉCOLTER");
        System.out.println("=".repeat(80));

        if (g == null || g.getSommets().isEmpty()) {
            System.out.println("Aucun plan de commune chargé !");
            appuyerPourContinuer();
            return;
        }

        Sommet depot = g.getDepot();
        double totalDechets = 0.0;
        boolean depotAffiche = false;

        List<Sommet> pointsAvecDechets = g.getSommets().stream()
                .filter(s -> s.getQuantiteDechets() > 0.01)
                .sorted(Comparator.comparing(Sommet::getId))
                .toList();

        if (pointsAvecDechets.isEmpty()) {
            System.out.println("Aucun déchet à collecter dans cette commune !");
            System.out.println("Toutes les poubelles sont vides.");
        } else {
            System.out.printf("Nombre de points avec déchets : %d%n%n", pointsAvecDechets.size());
            System.out.println("ID    | Nom du lieu                        | Quantité (t)");
            System.out.println("-".repeat(60));

            for (Sommet s : pointsAvecDechets) {
                if (s.equals(depot)) {
                    depotAffiche = true;
                    continue; // on affiche le dépôt à la fin
                }
                System.out.printf("%-5s | %-33s | %.1f t%n",
                        s.getId(), s.getNom(), s.getQuantiteDechets());
                totalDechets += s.getQuantiteDechets();
            }

            // Affichage du dépôt s'il a des déchets
            if (depotAffiche) {
                System.out.printf("%-5s | %-33s | %.1f t (au dépôt)%n",
                        depot.getId(), depot.getNom(), depot.getQuantiteDechets());
                totalDechets += depot.getQuantiteDechets();
            }

            System.out.println("-".repeat(60));
            System.out.printf("TOTAL DES DÉCHETS À COLLECTER : %.2f tonnes%n", totalDechets);

            if (totalDechets > 50) {
                System.out.println("ALERTE ROUGE : Très forte production de déchets !");
            } else if (totalDechets > 20) {
                System.out.println("Attention : Production élevée – renforcez la collecte.");
            } else {
                System.out.println("Situation normale.");
            }
        }

        appuyerPourContinuer();
    }

    public int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrez un nombre : ");
            scanner.next();
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }

    public void appuyerPourContinuer() {
        System.out.println("\nAppuyez sur Entrée...");
        scanner.nextLine();
    }

    public double lireCapacite() {
        System.out.print("Capacité camion (tonnes) : ");
        return scanner.nextDouble();
    }

    /**
     * Lit la destination pour Dijkstra.
     * 
     * @param g Le graphe.
     * @return Le sommet choisi.
     */
    public Sommet lireDestination(Graphe g) {
        System.out.print("ID du sommet destination : ");
        String id = scanner.nextLine();
        return g.getSommetParId(id);
    }

    // Dans VueConsole.java – AJOUTE CETTE MÉTHODE

    public void afficherCheminAvecDistance(ControleurGraphe.ResultatChemin resultat) {
        if (resultat == null || resultat.chemin.isEmpty() || resultat.chemin.size() == 1) {
            System.out.println("Aucun chemin trouvé.");
            return;
        }

        System.out.print("Itinéraire optimal : ");
        for (int i = 0; i < resultat.chemin.size(); i++) {
            System.out.print(resultat.chemin.get(i).getId());
            if (i < resultat.chemin.size() - 1) 
                System.out.print(" --> ");
        }
        System.out.printf("%nDistance totale = %.2f km%n%n", resultat.distance);
    }

    public void afficherPlanActuel(Graphe g) {
        Utils.afficherTitre("PLAN ACTUEL - LISTE DES RUES");

        if (g == null || g.getAretes().isEmpty()) {
            System.out.println("Aucun graphe chargé !");
            Utils.appuyerPourContinuer();
            return;
        }
        System.out.println("Type de graphe chargé : " + g.getTypeGraphe());
        System.out.println("Graphe chargé : " + g.getSommets().size() + " sommets | " + g.getAretes().size() + " rues");
        System.out.println("Dépôt : " + g.getDepot().getId() + " ( " + g.getDepot().getNom() + " )");
        Utils.afficherSeparateur();

        System.out.println("RUES DU RÉSEAU ROUTIER :");
        System.out.println("Départ --> Arrivée        | Distance  | Type");
        System.out.println("--------------------------|-----------|-------------");

        for (Arete a : g.getAretes()) {
            String fleche = a.estDoubleSens() ? "<-->" : "-->";
            String type = a.estDoubleSens() ? "Double sens" : "Sens unique";

            System.out.printf("%-4s %s %-4s          | %6.2f km  | %s%n",
                    a.getSource().getId(),
                    fleche,
                    a.getDestination().getId(),
                    a.getLongueur(),
                    type);
        }

        Utils.afficherSeparateur();
        System.out.println("Plan routier affiché avec succès !");
        Utils.appuyerPourContinuer();
    }

}
