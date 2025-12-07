package modele;

public class Arete {
    private final Sommet source;
    private final Sommet destination;
    private final double longueur;
    private final boolean doubleSens;

    // Constructeur principal – double sens PAR DÉFAUT (conforme CDC page 7)
    public Arete(Sommet source, Sommet destination, double longueur) {
        this(source, destination, longueur, true);
    }

    public Arete(Sommet source, Sommet destination, double longueur, boolean doubleSens) {
        this.source = source;
        this.destination = destination;
        this.longueur = longueur;
        this.doubleSens = doubleSens;
    }

    public Sommet getSource() { return source; }
    public Sommet getDestination() { return destination; }
    public double getLongueur() { return longueur; }
    public boolean estDoubleSens() { return doubleSens; }
}
