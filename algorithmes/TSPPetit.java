package algorithmes;

import modele.*;
import java.util.*;

/**
 * classe pour résoudre le TSP exact .
 * Utilisé pour le Thème 1.a – Ramassage des encombrants (≤12 points).
 */
public class TSPPetit {

    /**
     * Résout le TSP exact de façon itérative (algorithme de Held-Karp simplifié).
     * 
     * @param g      Le graphe.
     * @param depot  Le dépôt (point de départ et d'arrivée).
     * @param points Les points à visiter (max 12).
     * @return Liste contenant : [0] = circuit optimal, [1] = distance totale
     */
    public static List<Object> resoudreTSPExact(Graphe g, Sommet depot, List<Sommet> points) {
        if (points.isEmpty()) {
            List<Object> res = new ArrayList<>();
            res.add(List.of(depot));
            res.add(0.0);
            return res;
        }

        int n = points.size();

        // 0 = dépôt, 1 à n = points
        List<Sommet> tous = new ArrayList<>();
        tous.add(depot);
        tous.addAll(points);

        // Matrice des distances
        double[][] dist = new double[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    double d = Dijkstra.calculerDistance(g, tous.get(i), tous.get(j));
                    dist[i][j] = (d >= Double.MAX_VALUE) ? Double.MAX_VALUE : d;
                }
            }
        }

        // DP : dp[mask][i] = distance minimale pour visiter le masque en terminant à i
        double[][] dp = new double[1 << n][n + 1];
        int[][] pred = new int[1 << n][n + 1]; // prédécesseur pour reconstruction
        for (double[] row : dp)
            Arrays.fill(row, Double.MAX_VALUE);
        dp[0][0] = 0.0;

        // Remplissage de la table DP
        for (int mask = 0; mask < (1 << n); mask++) {
            for (int i = 0; i <= n; i++) {
                if (dp[mask][i] >= Double.MAX_VALUE)
                    continue;

                for (int j = 1; j <= n; j++) {
                    if ((mask & (1 << (j - 1))) != 0)
                        continue; // déjà visité

                    int nouveauMask = mask | (1 << (j - 1));
                    double nouvelleDist = dp[mask][i] + dist[i][j];

                    if (nouvelleDist < dp[nouveauMask][j]) {
                        dp[nouveauMask][j] = nouvelleDist;
                        pred[nouveauMask][j] = i;
                    }
                }
            }
        }

        // Trouver le meilleur retour au dépôt
        double meilleure = Double.MAX_VALUE;
        int meilleurDernier = -1;
        int masqueComplet = (1 << n) - 1;

        for (int i = 1; i <= n; i++) {
            double totale = dp[masqueComplet][i] + dist[i][0];
            if (totale < meilleure) {
                meilleure = totale;
                meilleurDernier = i;
            }
        }

        if (meilleurDernier == -1) {
            List<Object> res = new ArrayList<>();
            res.add(new ArrayList<Sommet>());
            res.add(0.0);
            return res;
        }

        // Reconstruction du circuit
        List<Sommet> circuit = new ArrayList<>();
        circuit.add(depot);

        int courant = meilleurDernier;
        int mask = masqueComplet;

        while (courant != 0) {
            circuit.add(1, tous.get(courant)); // insère au début après le dépôt
            int precedent = pred[mask][courant];
            mask = mask & ~(1 << (courant - 1));
            courant = precedent;
        }

        List<Object> resultat = new ArrayList<>();
        resultat.add(circuit);
        resultat.add(meilleure);
        return resultat;
    }

    /**
     * Affiche le résultat du TSP.
     */
    public static void afficherResultat(List<Object> res, Graphe g) {
        @SuppressWarnings("unchecked")
        List<Sommet> circuit = (List<Sommet>) res.get(0);
        double distanceTSP = (double) res.get(1);

        System.out.println("\n" + "=".repeat(90));
        System.out.println("   THÈME 1A - TOURNÉE OPTIMALE DES ENCOMBRANTS (TSP EXACT)");
        System.out.println("=".repeat(90));

        // 1. Circuit logique trouvé par le TSP
        System.out.println("CIRCUIT LOGIQUE TROUVÉ PAR LE TSP :");
        System.out.print("   ");
        for (int i = 0; i < circuit.size(); i++) {
            System.out.print(circuit.get(i).getId());
            if (i < circuit.size() - 1)
                System.out.print(" --> ");
        }
        System.out.println(" --> " + circuit.get(0).getId() + " (retour dépôt)");
        System.out.printf("   Distance totale (TSP) : %.2f km%n%n", distanceTSP);

        // 2. Chemin réel emprunté par le camion (avec tous les détails)
        System.out.println("CHEMIN RÉEL EMPRUNTÉ PAR LE CAMION :");
        System.out.print("   ");
        double totalReel = 0.0;

        for (int i = 0; i < circuit.size(); i++) {
            Sommet depart = circuit.get(i);
            Sommet arrivee = circuit.get((i + 1) % circuit.size());

            List<Sommet> segment = Dijkstra.cheminLePlusCourt(g, depart, arrivee);
            double distSegment = Dijkstra.calculerDistance(g, depart, arrivee);
            totalReel += distSegment;

            // Affichage du segment avec flèche
            for (int j = 0; j < segment.size(); j++) {
                System.out.print(segment.get(j).getId());
                if (j < segment.size() - 1) {
                    System.out.print(" --> ");
                }
            }

            if (i < circuit.size() - 1) {
                System.out.printf(" (%.1f km) --> ", distSegment);
            }
        }
        System.out.println();

        // 3. Vérification finale
        System.out.println("\n" + "-".repeat(90));
        System.out.printf("Distance totale réelle calculée : %.2f km%n", totalReel);
        if (Math.abs(totalReel - distanceTSP) < 1e-6) {
            System.out.println("COHÉRENCE PARFAITE : TSP et Dijkstra sont d'accord !");
        } else {
            System.out.printf("LÉGÈRE DIFFÉRENCE (arrondis) : TSP = %.2f km | Réel = %.2f km%n",
                    distanceTSP, totalReel);
        }
        System.out.println("=".repeat(90));
    }
}
