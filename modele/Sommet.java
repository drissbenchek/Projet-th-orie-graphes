package modele;

/**
 * Classe représentant un sommet (intersection ou point de collecte) dans le
 * graphe routier.
 * Elle encapsule l'ID, le nom, et la quantité de déchets associée.
 * Cette classe est utilisée pour tous les algorithmes impliquant des points
 * dans la commune.
 */
public class Sommet {
    private String id; // ID unique du sommet (ex: "A")
    private String nom; // Nom descriptif (ex: "Dépôt central")
    private double quantiteDechets; // Quantité de déchets en tonnes (0.0 si aucun)

    /**
     * Constructeur sans quantité de déchets (par défaut 0.0).
     * 
     * @param id  L'ID du sommet.
     * @param nom Le nom du sommet.
     */
    public Sommet(String id, String nom) {
        this(id, nom, 0.0);
    }

    /**
     * Constructeur avec quantité de déchets.
     * 
     * @param id              L'ID du sommet.
     * @param nom             Le nom du sommet.
     * @param quantiteDechets La quantité de déchets en tonnes.
     */
    public Sommet(String id, String nom, double quantiteDechets) {
        this.id = id;
        this.nom = nom;
        this.quantiteDechets = quantiteDechets;
    }

    /**
     * Récupère l'ID du sommet.
     * 
     * @return L'ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Récupère le nom du sommet.
     * 
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère la quantité de déchets.
     * 
     * @return La quantité en tonnes.
     */
    public double getQuantiteDechets() {
        return quantiteDechets;
    }

    /**
     * Met à jour la quantité de déchets.
     * 
     * @param q La nouvelle quantité.
     */
    public void setQuantiteDechets(double q) {
        this.quantiteDechets = q;
    }

    /**
     * Représentation textuelle du sommet (nom, ID, quantité si >0).
     * 
     * @return Une chaîne décrivant le sommet.
     */
    @Override
    public String toString() {
        String info = nom + " (" + id + ")";
        if (quantiteDechets > 0.01) {
            info += " [" + String.format("%.1f", quantiteDechets) + " t]";
        }
        return info;
    }

    /**
     * Vérifie si deux sommets sont égaux (basé sur l'ID).
     * 
     * @param o L'objet à comparer.
     * @return True si égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Sommet))
            return false;
        Sommet s = (Sommet) o;
        return id.equals(s.id);
    }

    /**
     * Code de hachage basé sur l'ID.
     * 
     * @return Le code de hachage.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
