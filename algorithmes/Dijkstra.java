package algorithmes;

import java.util.*;
import modele.*;

public class Dijkstra {

    public static double calculerDistance(Graphe g, Sommet depart, Sommet arrivee) {
        if (depart.equals(arrivee))
            return 0.0;

        Map<Sommet, Double> dist = new HashMap<>();
        Set<Sommet> visite = new HashSet<>();
        PriorityQueue<Map.Entry<Double, Sommet>> pq = new PriorityQueue<>(
                (a1, a2) -> Double.compare(a1.getKey(), a2.getKey()));

        for (Sommet s : g.getSommets())
            dist.put(s, Double.MAX_VALUE);
        dist.put(depart, 0.0);
        pq.add(new AbstractMap.SimpleEntry<>(0.0, depart));

        while (!pq.isEmpty()) {
            Map.Entry<Double, Sommet> entry = pq.poll();
            Sommet u = entry.getValue();
            if (visite.contains(u))
                continue;
            visite.add(u);

            for (Sommet v : g.getVoisins(u)) {
                Arete a = g.getArete(u, v);
                if (a == null)
                    continue;

                double nouveau = dist.get(u) + a.getLongueur();
                if (nouveau < dist.get(v)) {
                    dist.put(v, nouveau);
                    pq.add(new AbstractMap.SimpleEntry<>(nouveau, v));
                }
            }
        }
        return dist.getOrDefault(arrivee, Double.MAX_VALUE);
    }

    public static List<Sommet> cheminLePlusCourt(Graphe g, Sommet depart, Sommet arrivee) {
        if (depart.equals(arrivee))
            return List.of(depart);

        Map<Sommet, Double> dist = new HashMap<>();
        Map<Sommet, Sommet> pred = new HashMap<>();
        Set<Sommet> visite = new HashSet<>();
        PriorityQueue<Map.Entry<Double, Sommet>> pq = new PriorityQueue<>(
                (a1, a2) -> Double.compare(a1.getKey(), a2.getKey()));

        for (Sommet s : g.getSommets())
            dist.put(s, Double.MAX_VALUE);
        dist.put(depart, 0.0);
        pq.add(new AbstractMap.SimpleEntry<>(0.0, depart));

        while (!pq.isEmpty()) {
            Map.Entry<Double, Sommet> entry = pq.poll();
            Sommet u = entry.getValue();
            if (visite.contains(u))
                continue;
            visite.add(u);

            for (Sommet v : g.getVoisins(u)) {
                Arete a = g.getArete(u, v);
                if (a == null)
                    continue;

                double nouveau = dist.get(u) + a.getLongueur();
                if (nouveau < dist.get(v)) {
                    dist.put(v, nouveau);
                    pred.put(v, u);
                    pq.add(new AbstractMap.SimpleEntry<>(nouveau, v));
                }
            }
        }

        if (!pred.containsKey(arrivee))
            return List.of();

        List<Sommet> chemin = new ArrayList<>();
        Sommet courant = arrivee;
        while (courant != null) {
            chemin.add(0, courant);
            courant = pred.get(courant);
        }
        return chemin;
    }
}