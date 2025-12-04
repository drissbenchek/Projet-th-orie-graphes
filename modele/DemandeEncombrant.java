
package modele;

import java.util.UUID;

/**
 * Classe représentant une demande de ramassage d'encombrants.
 * Elle encapsule les informations sur une demande spécifique : ID unique,
 * adresse (sommet du graphe), description, et statut (traitée ou non).
 * Utilisée pour le Thème 1a – Gestion des encombrants.
 */
public class DemandeEncombrant {
    private String id; // ID unique généré pour la demande
    private Sommet adresse; // Sommet (adresse) où se trouve l'encombrant
    private String description; // Description de l'encombrant (ex: "Meuble vieux")
    private boolean traitee; // Statut : true si ramassé, false sinon

    /**
     * Constructeur de la demande d'encombrant.
     * Génère un ID unique et initialise le statut à non traité.
     * 
     * @param adresse     Le sommet (adresse) de la demande.
     * @param description La description de l'encombrant.
     */
    public DemandeEncombrant(Sommet adresse, String description) {
        this.id = UUID.randomUUID().toString().substring(0, 8); // ID unique court
        this.adresse = adresse;
        this.description = description;
        this.traitee = false;
    }

    /**
     * Récupère l'ID de la demande.
     * 
     * @return L'ID unique.
     */
    public String getId() {
        return id;
    }

    /**
     * Récupère l'adresse (sommet) de la demande.
     * 
     * @return Le sommet associé.
     */
    public Sommet getAdresse() {
        return adresse;
    }

    /**
     * Récupère la description de l'encombrant.
     * 
     * @return La description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Vérifie si la demande a été traitée.
     * 
     * @return true si traitée, false sinon.
     */
    public boolean estTraitee() {
        return traitee;
    }

    /**
     * Met à jour le statut de la demande.
     * 
     * @param traitee Le nouveau statut (true = traité).
     */
    public void setTraitee(boolean traitee) {
        this.traitee = traitee;
    }

    /**
     * Représentation textuelle de la demande.
     * 
     * @return Une chaîne décrivant la demande (ID, description, adresse).
     */
    @Override
    public String toString() {
        return "Demande #" + id + " : " + description + " à " + adresse.getNom() + " (" + adresse.getId() + ")";
    }
}
