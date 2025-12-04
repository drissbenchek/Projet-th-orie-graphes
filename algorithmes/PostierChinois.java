package algorithmes;

import modele.*;
import utils.Utils;
import java.util.*;

/**
 * Classe pour résoudre le problème du Postier Chinois (Chinese Postman
 * Problem).
 * Gère les graphes eulériens, avec 2 ou plus de sommets impairs.
 * Utilise l'appariement des impairs et l'algorithme de Hiérholzer.
 */
public class PostierChinois {
    /**
     * Classe interne pour le résultat du Postier Chinois.
     */
    public static class ResultatPostierChinois {
        public List<Sommet> circuit;
        public double distanceTotale;
        public List<Arete> aretesDupliquees = new ArrayList<>();
        public int nombreImpairs;

        public ResultatPostierChinois(int nombreImpairs) {
            this.nombreImpairs = nombreImpairs;
        }
    }

    /**
     * Résout le problème du Postier Chinois.
     * 
     * @param g Le graphe.
     * @return Le résultat (circuit, distance, duplications).
     */
    public static ResultatPostierChinois resoudrePostierChinois(Graphe g) {
        ResultatPostierChinois res = new ResultatPostierChinois(0);

        List<Sommet> impairs = g.getSommetsDegreImpair();
        res.nombreImpairs = impairs.size();

        System.out.println("Sommets de degré impair détectés : " + res.nombreImpairs);

        if (res.nombreImpairs % 2 != 0) {
            System.out.println("ERREUR : nombre impair de sommets impairs --> impossible !");
            return res;
        }

        // Cas eulérien parfait
        if (res.nombreImpairs == 0) {
            System.out.println("Graphe eulérien --> circuit direct");
            res.circuit = CircuitEulerien.trouverCircuit(g, g.getDepot());
            if (res.circuit == null || res.circuit.isEmpty()) {
                System.out.println("ERREUR : graphe non connexe ou non eulérien !");
                return res;
            }
            res.distanceTotale = calculerDistanceCircuit(g, res.circuit);
            return res;
        }
        // Cas avec sommets impairs → on ajoute (n/2) chemins
        System.out.println("Arêtes dupliquées : " + (res.nombreImpairs / 2));

        Graphe augmente = copierGraphe(g);

        // Appariement des sommets impairs
        for (int i = 0; i < impairs.size(); i += 2) {
            Sommet u = impairs.get(i);
            Sommet v = impairs.get(i + 1);

            List<Sommet> chemin = Dijkstra.cheminLePlusCourt(augmente, u, v);
            if (chemin == null || chemin.size() < 2) {
                System.out.println("Pas de chemin entre " + u.getId() + " et " + v.getId());
                continue;
            }
            // Dupliquer les arêtes du chemin
            for (int j = 0; j < chemin.size() - 1; j++) {
                Arete originale = g.getArete(chemin.get(j), chemin.get(j + 1));
                if (originale != null) {
                    augmente.ajouterArete(new Arete(
                            originale.getSource(),
                            originale.getDestination(),
                            originale.getLongueur(),
                            originale.estDoubleSens()));
                    res.aretesDupliquees.add(originale);
                }
            }
            // Détection des sens uniques
            boolean aSensUnique = g.getAretes().stream().anyMatch(a -> !a.estDoubleSens());
            if (aSensUnique) {
                System.out.println("ATTENTION : Ce graphe contient des sens uniques.");
                System.out.println("Le Postier Chinois classique ne fonctionne que sur graphes non orientés.");
                System.out.println("→ Solution non optimale possible ou impossible.");
                System.out.println("Nous limitons le Postier Chinois à HO1 comme indiqué dans le CDC.");
                // Tu peux soit bloquer, soit continuer avec un avertissement
            }

        }

        // Circuit eulérien sur le graphe augmenté
        res.circuit = CircuitEulerien.trouverCircuit(augmente, g.getDepot());

        if (res.circuit == null || res.circuit.isEmpty()) {
            System.out.println("ERREUR : impossible de trouver un circuit eulérien même après duplication !");
            res.circuit = new ArrayList<>();
            res.distanceTotale = 0.0;
        } else {
            res.distanceTotale = calculerDistanceCircuit(g, res.circuit);
        }

        return res;
    }

    /**
     * Calcule la distance totale d'un circuit.
     * 
     * @param g       Le graphe.
     * @param circuit Le circuit.
     * @return La distance totale.
     */
    private static double calculerDistanceCircuit(Graphe g, List<Sommet> circuit) {
        if (circuit.size() < 2)
            return 0.0;
        double total = 0.0;
        for (int i = 0; i < circuit.size() - 1; i++) {
            Arete a = g.getArete(circuit.get(i), circuit.get(i + 1));
            if (a != null)
                total += a.getLongueur();
        }
        return total;
    }

    /**
     * Copie un graphe.
     * 
     * @param g Le graphe original.
     * @return La copie.
     */
    private static Graphe copierGraphe(Graphe g) {
        Graphe copie = new Graphe();
        for (Arete a : g.getAretes()) {
            copie.ajouterArete(new Arete(a.getSource(), a.getDestination(), a.getLongueur(), a.estDoubleSens()));
        }
        return copie;
    }

    /**
     * Affiche le résultat du Postier Chinois.
     * 
     * @param r Le résultat.
     * @param g Le graphe.
     */
    public static void afficherResultat(ResultatPostierChinois r, Graphe g) {
        Utils.afficherSeparateur();
        System.out.println("           RÉSULTATS POSTIER CHINOIS - CIRCUIT OPTIMAL");
        Utils.afficherSeparateur();
        System.out.printf("Distance totale : %.2f km%n", r.distanceTotale);
        System.out.println("Arêtes dupliquées : " + r.aretesDupliquees.size());
        System.out.println("Sommets de degré impair : " + r.nombreImpairs);

        if (r.circuit == null || r.circuit.isEmpty()) {
            System.out.println("\nERREUR : Aucun circuit trouvé (graphe non connexe ?)");
        } else {
            System.out.println("\nPARCOURS COMPLET :");
            System.out.print("   ");
            for (int i = 0; i < r.circuit.size(); i++) {
                System.out.print(r.circuit.get(i).getId());
                if (i < r.circuit.size() - 1)
                    System.out.print(" --> ");
                if ((i + 1) % 12 == 0)
                    System.out.println("\n   ");
            }
            System.out.println("\n");
        }
        Utils.afficherSeparateur();
    }
}
