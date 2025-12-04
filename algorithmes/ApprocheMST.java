package algorithmes;

import modele.*;

import java.util.*;

/**
 * Classe implémentant l'approche MST pour le Thème 2.
 * Utilise l'arbre couvrant minimal (Kruskal), le parcours préfixe, et le
 * shortcutting pour approximer le TSP.
 * Prend en compte la capacité du camion pour découper en tournées.
 */
public class ApprocheMST {

    /** Classe interne pour les arêtes MST */
    private static class AreteMST implements Comparable<AreteMST> {
        Sommet u, v;
        double poids;

        AreteMST(Sommet u, Sommet v, double poids) {
            this.u = u;
            this.v = v;
            this.poids = poids;
        }

        @Override
        public int compareTo(AreteMST o) {
            return Double.compare(this.poids, o.poids);
        }
    }

    /** Classe interne pour l'Union-Find de Kruskal */
    private static class UnionFind {
        private final Map<Sommet, Sommet> parent = new HashMap<>();

        Sommet find(Sommet x) {
            parent.putIfAbsent(x, x);
            if (!parent.get(x).equals(x)) {
                parent.put(x, find(parent.get(x)));
            }
            return parent.get(x);
        }

        void union(Sommet x, Sommet y) {
            parent.put(find(x), find(y));
        }
    }

    /**
     * Résout le TSP avec l'approche MST + shortcutting et capacité du camion.
     * 
     * @param g              Le graphe routier.
     * @param depot          Le dépôt.
     * @param tousLesPoints  Tous les points.
     * @param capaciteCamion La capacité du camion.
     * @return Liste des tournées.
     */
    public static List<Tournee> resoudreAvecMST(Graphe g, Sommet depot,
            List<Sommet> tousLesPoints, double capaciteCamion) {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("   THÈME 2 : APPROCHE MST + SHORTCUTTING + CAPACITÉ CAMION");
        System.out.printf("   Capacité par camion : %.1f tonnes%n", capaciteCamion);
        System.out.println("=".repeat(80));

        // 1. Filtrer les points à visiter (exclure dépôt + quantité > 0)
        List<Sommet> points = new ArrayList<>();
        for (Sommet s : tousLesPoints) {
            if (!s.equals(depot) && s.getQuantiteDechets() > 0.01) {
                points.add(s);
            }
        }
        points.add(0, depot); // on veut que le dépôt soit le sommet 0

        if (points.size() <= 1) {
            System.out.println("Rien à collecter.");
            return new ArrayList<>();
        }

        // 2. Construire le graphe complet avec distances Dijkstra
        List<AreteMST> toutesAretes = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dist = Dijkstra.calculerDistance(g, points.get(i), points.get(j));
                if (dist < Double.MAX_VALUE) {
                    toutesAretes.add(new AreteMST(points.get(i), points.get(j), dist));
                }
            }
        }

        // 3. Kruskal → Arbre couvrant minimal
        Collections.sort(toutesAretes);
        UnionFind uf = new UnionFind();
        List<AreteMST> mst = new ArrayList<>();

        for (AreteMST a : toutesAretes) {
            if (uf.find(a.u) != uf.find(a.v)) {
                uf.union(a.u, a.v);
                mst.add(a);
            }
        }

        // 4. Construire l'arbre (liste d'adjacence)
        Map<Sommet, List<Sommet>> arbre = new HashMap<>();
        for (Sommet s : points)
            arbre.put(s, new ArrayList<>());
        for (AreteMST a : mst) {
            arbre.get(a.u).add(a.v);
            arbre.get(a.v).add(a.u);
        }

        // 5. Parcours préfixe (DFS) → ordre de visite avec doublons
        List<Sommet> parcoursDoublons = new ArrayList<>();
        dfs(depot, arbre, new HashSet<>(), parcoursDoublons);

        // 6. Shortcutting : supprimer les visites redondantes
        List<Sommet> ordreVisite = new ArrayList<>();
        Set<Sommet> dejaVu = new HashSet<>();
        for (Sommet s : parcoursDoublons) {
            if (!dejaVu.contains(s) || s.equals(depot)) {
                ordreVisite.add(s);
                dejaVu.add(s);
            }
        }

        // 7. Découpage en tournées selon capacité
        List<Tournee> tournees = new ArrayList<>();
        int numero = 1;

        List<Sommet> restants = new ArrayList<>(ordreVisite);
        restants.remove(0); // on enlève le premier dépôt

        while (!restants.isEmpty()) {
            Tournee t = new Tournee(numero++, depot);
            double charge = 0.0;
            Sommet courant = depot;

            System.out.printf("%n=== TOURNÉE MST N°%d ===%n", numero - 1);

            while (!restants.isEmpty()) {
                Sommet candidat = restants.get(0);
                double dist = Dijkstra.calculerDistance(g, courant, candidat);

                if (charge + candidat.getQuantiteDechets() <= capaciteCamion + 1e-9) {
                    t.ajouterPoint(candidat, dist);
                    charge += candidat.getQuantiteDechets();
                    courant = candidat;
                    restants.remove(0);
                    System.out.printf("   + %s [%.1f t] --> %.2f km%n",
                            candidat.getId(), candidat.getQuantiteDechets(), dist);
                } else {
                    break;
                }
            }

            double retour = Dijkstra.calculerDistance(g, courant, depot);
            t.ajouterRetourDepot(retour);
            System.out.printf("   Retour dépôt --> %.2f km%n", retour);
            tournees.add(t);
        }

        // Résumé final
        System.out.println("\n" + "=".repeat(80));
        System.out.println("             RÉSULTATS FINAUX - APPROCHE MST");
        System.out.println("=".repeat(80));
        double totalKm = 0.0;
        for (Tournee t : tournees) {
            t.afficher();
            totalKm += t.getDistanceParcourue();
        }
        System.out.printf("%nCamions nécessaires       : %d%n", tournees.size());
        System.out.printf("Distance totale parcourue : %.2f km%n", totalKm);
        System.out.println("=".repeat(80));

        return tournees;
    }

    /**
     * Parcours DFS pour le préfixe.
     * 
     * @param courant  Le sommet courant.
     * @param arbre    L'arbre MST.
     * @param visite   Set des visités.
     * @param resultat Liste du parcours.
     */
    private static void dfs(Sommet courant, Map<Sommet, List<Sommet>> arbre,
            Set<Sommet> visite, List<Sommet> resultat) {
        visite.add(courant);
        resultat.add(courant);
        for (Sommet voisin : arbre.get(courant)) {
            if (!visite.contains(voisin)) {
                dfs(voisin, arbre, visite, resultat);
                resultat.add(courant); // retour dans l'arbre → doublon volontaire
            }
        }
    }
}
