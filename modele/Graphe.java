package modele;

import java.util.*;

/**
 * Classe représentant le graphe routier de la commune.
 * Elle gère les sommets (intersections) et les arêtes (rues) avec support pour
 * les sens uniques.
 * Cette classe est le cœur du modèle, utilisée pour tous les algorithmes de
 * parcours.
 */
public class Graphe {
    private List<Sommet> sommets = new ArrayList<>(); // Liste des sommets
    private List<Arete> aretes = new ArrayList<>(); // Liste des arêtes
    private String typeGraphe = "Graphe personnalisé"; // par défaut

    public void setTypeGraphe(String type) {
        this.typeGraphe = type;
    }

    public String getTypeGraphe() {
        return typeGraphe;
    }

    /**
     * Ajoute un sommet au graphe si non existant.
     * 
     * @param s Le sommet à ajouter.
     */
    public void ajouterSommet(Sommet s) {
        if (s != null && !sommets.contains(s))
            sommets.add(s);
    }

    /**
     * Ajoute une arête au graphe et ajoute les sommets source/destination si
     * nécessaire.
     * 
     * @param a L'arête à ajouter.
     */
    public void ajouterArete(Arete a) {
        if (a != null) {
            aretes.add(a);
            ajouterSommet(a.getSource());
            ajouterSommet(a.getDestination());
        }
    }

    /**
     * Récupère la liste des sommets (copie pour sécurité).
     * 
     * @return Liste des sommets.
     */
    public List<Sommet> getSommets() {
        return new ArrayList<>(sommets);
    }

    /**
     * Récupère la liste des arêtes (copie pour sécurité).
     * 
     * @return Liste des arêtes.
     */
    public List<Arete> getAretes() {
        return new ArrayList<>(aretes);
    }

    /**
     * Récupère un sommet par son ID (insensible à la casse).
     * 
     * @param id L'ID du sommet.
     * @return Le sommet ou null si non trouvé.
     */
    public Sommet getSommetParId(String id) {
        if (id == null)
            return null;
        return sommets.stream()
                .filter(s -> id.equalsIgnoreCase(s.getId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Récupère le dépôt (sommet "A" ou premier sommet).
     * 
     * @return Le dépôt.
     */
    public Sommet getDepot() {
        if (sommets.isEmpty())
            return null;
        for (Sommet s : sommets) {
            if ("A".equalsIgnoreCase(s.getId()))
                return s;
        }
        return sommets.get(0);
    }

    /**
     * Récupère les voisins d'un sommet (respecte les sens uniques).
     * 
     * @param s Le sommet.
     * @return Liste des voisins.
     */
    public List<Sommet> getVoisins(Sommet s) {
        List<Sommet> voisins = new ArrayList<>();
        if (s == null)
            return voisins;

        for (Arete a : aretes) {
            // Arête sortante
            if (a.getSource().equals(s)) {
                voisins.add(a.getDestination());
            }
            // Arête entrante si double sens
            if (a.estDoubleSens() && a.getDestination().equals(s)) {
                voisins.add(a.getSource());
            }
        }
        return voisins;
    }

    /**
     * Récupère une arête entre deux sommets (respecte les sens uniques).
     * 
     * @param u Premier sommet.
     * @param v Second sommet.
     * @return L'arête ou null si non trouvée.
     */
    public Arete getArete(Sommet u, Sommet v) {
        for (Arete a : aretes) {
            // Sens direct
            if (a.getSource().equals(u) && a.getDestination().equals(v)) {
                return a;
            }
            // Sens inverse → autorisé seulement si double sens
            if (a.estDoubleSens() && a.getSource().equals(v) && a.getDestination().equals(u)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Calcule le degré d'un sommet (respecte les sens uniques).
     * 
     * @param s Le sommet.
     * @return Le degré.
     */
    public int getDegre(Sommet s) {
        if (s == null)
            return 0;
        int degre = 0;

        for (Arete a : aretes) {
            // Arête sortante
            if (a.getSource().equals(s)) {
                degre++;
            }
            // Arête entrante si double sens
            if (a.estDoubleSens() && a.getDestination().equals(s)) {
                degre++;
            }
        }
        return degre;
    }

    /**
     * Récupère les sommets de degré impair.
     * 
     * @return Liste des sommets impairs.
     */
    public List<Sommet> getSommetsDegreImpair() {
        List<Sommet> impairs = new ArrayList<>();
        for (Sommet s : sommets) {
            if (getDegre(s) % 2 != 0) {
                impairs.add(s);
            }
        }
        return impairs;
    }

    /**
     * Vérifie si le graphe est eulérien (connexe + tous degrés pairs).
     * 
     * @return True si eulérien, false sinon.
     */
    public boolean estEulerien() {
        if (sommets.isEmpty())
            return false;

        // 1. Vérifier connexité
        Set<Sommet> visites = new HashSet<>();
        dfs(sommets.get(0), visites);
        if (visites.size() != sommets.size())
            return false;

        // 2. Vérifier TOUS les degrés pairs
        for (Sommet s : sommets) {
            int degre = calculerDegreCorrect(s);
            if (degre % 2 != 0) {
                System.out.println("Sommet " + s.getId() + " a degré impair : " + degre);
                return false;
            }
        }
        return true;
    }

    private int calculerDegreCorrect(Sommet s) {
        int degre = 0;

        for (Arete a : aretes) {
            // Si s est source de l'arête → +1
            if (a.getSource().equals(s)) {
                degre++;
            }
            // Si s est destination ET arête double sens → +1
            if (a.estDoubleSens() && a.getDestination().equals(s)) {
                degre++;
            }
        }
        return degre;
    }

    private void dfs(Sommet courant, Set<Sommet> visites) {
        if (courant == null || visites.contains(courant))
            return;
        visites.add(courant);
        for (Sommet voisin : getVoisins(courant)) {
            dfs(voisin, visites);
        }
    }

    /**
     * Affiche le graphe (utilisé pour le débogage).
     */
    public void afficherGraphe() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                   PLAN DE LA COMMUNE");
        System.out.println("=".repeat(70));
        System.out.println("Sommets : " + sommets.size() + " | Arêtes : " + aretes.size());
        boolean aSensUnique = aretes.stream().anyMatch(a -> !a.estDoubleSens());
        System.out.println("Rues à sens unique détectées : " + (aSensUnique ? "OUI" : "NON"));
        System.out.println("Eulerien : " + (estEulerien() ? "OUI (parfait pour le Postier Chinois)" : "NON"));
        System.out.println("=".repeat(70) + "\n");
    }
}
