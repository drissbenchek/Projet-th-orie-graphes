package modele;

/**
 * Classe représentant une arête (rue) dans le graphe routier.
 * Elle encapsule les informations sur une connexion entre deux sommets :
 * source, destination, distance, et type (double sens ou sens unique).
 * Cette classe est essentielle pour modéliser le réseau routier avec des
 * contraintes réelles comme les sens uniques.
 */
public class Arete {
    private Sommet source; // Sommet de départ
    private Sommet destination; // Sommet d'arrivée
    private double longueur; // Distance en km
    private boolean doubleSens; // True si double sens, false si sens unique

    /**
     * Constructeur pour une arête double sens par défaut.
     * 
     * @param source      Le sommet de départ.
     * @param destination Le sommet d'arrivée.
     * @param longueur    La distance en km.
     */
    public Arete(Sommet source, Sommet destination, double longueur) {
        this(source, destination, longueur, true);
    }

    /**
     * Constructeur pour une arête avec type spécifié.
     * 
     * @param source      Le sommet de départ.
     * @param destination Le sommet d'arrivée.
     * @param longueur    La distance en km.
     * @param doubleSens  True si double sens, false si sens unique.
     */
    public Arete(Sommet source, Sommet destination, double longueur, boolean doubleSens) {
        this.source = source;
        this.destination = destination;
        this.longueur = longueur;
        this.doubleSens = doubleSens;
    }

    /**
     * Récupère le sommet de départ.
     * 
     * @return Le sommet source.
     */
    public Sommet getSource() {
        return source;
    }

    /**
     * Récupère le sommet d'arrivée.
     * 
     * @return Le sommet destination.
     */
    public Sommet getDestination() {
        return destination;
    }

    /**
     * Récupère la distance de l'arête.
     * 
     * @return La longueur en km.
     */
    public double getLongueur() {
        return longueur;
    }

    /**
     * Vérifie si l'arête est à double sens.
     * 
     * @return True si double sens, false si sens unique.
     */
    public boolean estDoubleSens() {
        return doubleSens;
    }

    /**
     * Représentation textuelle de l'arête.
     * 
     * @return Une chaîne décrivant l'arête (source, destination, distance, type).
     */
    @Override
    public String toString() {
        return source.getId() + " —" + String.format("%.2f", longueur) + "km→ " + destination.getId() +
                (doubleSens ? " (↔)" : " (→)");
    }

    /**
     * Vérifie si deux arêtes sont égales (basé sur source et destination).
     * 
     * @param o L'objet à comparer.
     * @return True si égales, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Arete))
            return false;
        Arete a = (Arete) o;
        return source.equals(a.source) && destination.equals(a.destination);
    }

    /**
     * Code de hachage basé sur source et destination.
     * 
     * @return Le code de hachage.
     */
    @Override
    public int hashCode() {
        return source.hashCode() * 31 + destination.hashCode();
    }
}
