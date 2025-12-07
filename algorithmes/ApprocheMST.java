package algorithmes;

import modele.*;
import utils.Utils;

import java.util.*;

public class ApprocheMST {

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

    public static List<Tournee> resoudreAvecMST(Graphe g, Sommet depot,
            List<Sommet> tousLesPoints, double capaciteCamion) {

        System.out.println("\n" + "=".repeat(80));
        System.out.println("   THÈME 2 : APPROCHE MST + SHORTCUTTING + CAPACITÉ CAMION");
        System.out.printf("   Capacité par camion : %.1f tonnes%n", capaciteCamion);
        System.out.println("=".repeat(80));

        // Filtrer les points à visiter
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

        // Kruskal → MST
        List<AreteMST> aretes = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double d = Dijkstra.calculerDistance(g, points.get(i), points.get(j));
                if (d < Double.MAX_VALUE / 2) {
                    aretes.add(new AreteMST(points.get(i), points.get(j), d));
                }
            }
        }
        Collections.sort(aretes);

        UnionFind uf = new UnionFind();
        Map<Sommet, List<Sommet>> arbre = new HashMap<>();
        for (Sommet s : points)
            arbre.put(s, new ArrayList<>());

        for (AreteMST a : aretes) {
            if (uf.find(a.u) != uf.find(a.v)) {
                uf.union(a.u, a.v);
                arbre.get(a.u).add(a.v);
                arbre.get(a.v).add(a.u);
            }
        }

        // Parcours DFS + Shortcutting
        List<Sommet> prefixe = new ArrayList<>();
        Set<Sommet> visite = new HashSet<>();
        if (!points.isEmpty()) {
            dfs(points.get(0), arbre, visite, prefixe);
        }

        List<Sommet> circuit = new ArrayList<>();
        Set<Sommet> vu = new HashSet<>();
        for (Sommet s : prefixe) {
            if (!vu.contains(s)) {
                circuit.add(s);
                vu.add(s);
            }
        }

        // Découpage en tournées
        List<Tournee> tournees = new ArrayList<>();
        int numero = 1;
        Tournee t = new Tournee(numero++, depot);
        double charge = 0.0;
        Sommet departTournée = depot;

        System.out.printf("%n=== TOURNÉE MST N°%d ===%n", numero - 1);

        for (Sommet prochain : circuit) {

            // Calcul de la distance depuis le dernier point visité (ou dépôt si début de
            // tournée)
            double dist = Dijkstra.calculerDistance(g, departTournée, prochain);

            // Si on dépasse la capacité → on termine la tournée actuelle
            if (charge + prochain.getQuantiteDechets() > capaciteCamion + 1e-9) {
                double retour = Dijkstra.calculerDistance(g, departTournée, depot);
                t.ajouterRetourDepot(retour);
                System.out.printf("   Retour dépôt --> %.2f km%n", retour);
                tournees.add(t);

                // Nouvelle tournée
                t = new Tournee(numero++, depot);
                charge = 0.0;
                departTournée = depot;
                System.out.printf("%n=== TOURNÉE MST N°%d ===%n", numero - 1);

                // Recalculer la distance depuis le dépôt pour le point actuel
                dist = Dijkstra.calculerDistance(g, depot, prochain);
            }

            t.ajouterPoint(prochain, dist);
            charge += prochain.getQuantiteDechets();
            departTournée = prochain; // mise à jour pour le prochain point

            System.out.printf("   + %s [%.1f t] --> %.2f km%n",
                    prochain.getId(), prochain.getQuantiteDechets(), dist);
        }

        // Dernier retour au dépôt
        double dernierRetour = Dijkstra.calculerDistance(g, departTournée, depot);
        t.ajouterRetourDepot(dernierRetour);
        System.out.printf("   Retour dépôt --> %.2f km%n", dernierRetour);
        tournees.add(t);

        // Résumé final
        System.out.println("\n" + "=".repeat(80));
        System.out.println("             RÉSULTATS FINAUX - APPROCHE MST");
        System.out.println("=".repeat(80));
        double total = 0.0;
        for (Tournee tt : tournees) {
            tt.afficher();
            total += tt.getDistanceParcourue();
        }
        System.out.printf("%nCamions nécessaires       : %d%n", tournees.size());
        System.out.printf("Distance totale parcourue : %.2f km%n", total);
        System.out.println("=".repeat(80));

        Utils.appuyerPourContinuer();
        return tournees;
    }

    private static void dfs(Sommet c, Map<Sommet, List<Sommet>> a, Set<Sommet> v, List<Sommet> r) {
        v.add(c);
        r.add(c);
        for (Sommet n : a.getOrDefault(c, List.of())) {
            if (!v.contains(n)) {
                dfs(n, a, v, r);
                r.add(c);
            }
        }
    }
}