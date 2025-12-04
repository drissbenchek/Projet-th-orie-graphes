package algorithmes;

import java.util.*;
import modele.*;

/**
 * Classe pour l'algorithme de Dijkstra (plus court chemin).
 * Utilisée pour le Thème 1a (encombrants) et dans les autres algorithmes pour
 * les distances.
 */
public class Dijkstra {

    /**
     * Calcule la distance minimale entre deux sommets (Dijkstra).
     * 
     * @param g       Le graphe.
     * @param depart  Le sommet de départ.
     * @param arrivee Le sommet d'arrivée.
     * @return La distance minimale ou MAX_VALUE si aucun chemin.
     */
    public static double calculerDistance(Graphe g, Sommet depart, Sommet arrivee) {
        if (depart == null || arrivee == null || depart.equals(arrivee)) {
            return 0.0;
        }

        List<Sommet> sommets = g.getSommets();
        int n = sommets.size();

        // Tableau des distances 
        double[] distances = new double[n];
        boolean[] visites = new boolean[n];
        Arrays.fill(distances, Double.MAX_VALUE);

        int indexDepart = sommets.indexOf(depart);
        distances[indexDepart] = 0.0;

        // Boucle principale : on répète n fois (un sommet traité à chaque tour)
        for (int count = 0; count < n; count++) {
            // 1. Trouver le sommet non visité avec la plus petite distance
            int u = -1;
            double minDist = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visites[i] && distances[i] < minDist) {
                    minDist = distances[i];
                    u = i;
                }
            }
            if (u == -1)
                break; // plus rien à traiter

            visites[u] = true;
            Sommet sommetU = sommets.get(u);

            // 2. Mettre à jour les voisins
            for (Sommet voisin : g.getVoisins(sommetU)) {
                Arete arete = g.getArete(sommetU, voisin);
                if (arete == null)
                    continue;

                int indexV = sommets.indexOf(voisin);
                double nouvelleDistance = distances[u] + arete.getLongueur();

                if (nouvelleDistance < distances[indexV]) {
                    distances[indexV] = nouvelleDistance;
                }
            }
        }

        int indexArrivee = sommets.indexOf(arrivee);
        return distances[indexArrivee] == Double.MAX_VALUE ? Double.MAX_VALUE : distances[indexArrivee];
    }

    /**
     * Version avec reconstruction du chemin réel (même principe simplifié)
     */
    public static List<Sommet> cheminLePlusCourt(Graphe g, Sommet depart, Sommet arrivee) {
        if (depart.equals(arrivee))
            return List.of(depart);

        List<Sommet> sommets = g.getSommets();
        int n = sommets.size();

        double[] distances = new double[n];
        int[] predecesseur = new int[n]; // stocke l'index du prédécesseur
        boolean[] visites = new boolean[n];
        Arrays.fill(distances, Double.MAX_VALUE);
        Arrays.fill(predecesseur, -1);

        int indexDepart = sommets.indexOf(depart);
        distances[indexDepart] = 0.0;

        for (int count = 0; count < n; count++) {
            int u = -1;
            double minDist = Double.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visites[i] && distances[i] < minDist) {
                    minDist = distances[i];
                    u = i;
                }
            }
            if (u == -1)
                break;

            visites[u] = true;
            Sommet sommetU = sommets.get(u);

            for (Sommet voisin : g.getVoisins(sommetU)) {
                Arete arete = g.getArete(sommetU, voisin);
                if (arete == null)
                    continue;

                int v = sommets.indexOf(voisin);
                double nouvelle = distances[u] + arete.getLongueur();

                if (nouvelle < distances[v]) {
                    distances[v] = nouvelle;
                    predecesseur[v] = u;
                }
            }
        }

        // Reconstruction du chemin
        List<Sommet> chemin = new ArrayList<>();
        int courant = sommets.indexOf(arrivee);
        while (courant != -1) {
            chemin.add(0, sommets.get(courant));
            courant = predecesseur[courant];
        }

        return chemin.isEmpty() ? List.of(depart, arrivee) : chemin;
    }
}