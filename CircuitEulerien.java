package algorithmes;

import java.util.*;
import modele.*;

/**
 * Circuit eulérien .
 * Utilisé dans le Postier Chinois après duplication des arêtes.
 */
public class CircuitEulerien {

    /**
     * Trouve un circuit eulérien .
     * On travaille directement sur une copie des arêtes.
     * 
     * @param g      Le graphe (doit être eulérien)
     * @param depart Sommet de départ
     * @return Circuit eulérien sous forme de liste de sommets
     */
    public static List<Sommet> trouverCircuit(Graphe g, Sommet depart) {
        List<Arete> aretesRestantes = new ArrayList<>(g.getAretes());
        List<Sommet> circuit = new ArrayList<>();

        Sommet courant = depart;

        while (true) {
            Arete areteTrouvee = null;
            for (Iterator<Arete> it = aretesRestantes.iterator(); it.hasNext();) {
                Arete a = it.next();

                boolean departNormal = a.getSource().equals(courant);
                boolean departDoubleSens = a.estDoubleSens() && a.getDestination().equals(courant);

                if (departNormal || departDoubleSens) {
                    areteTrouvee = a;
                    it.remove();
                    break;
                }
            }

            if (areteTrouvee != null) {
                Sommet suivant = areteTrouvee.getSource().equals(courant)
                        ? areteTrouvee.getDestination()
                        : areteTrouvee.getSource();
                courant = suivant;
                circuit.add(courant);
            } else {
                break;
            }
        }

        circuit.add(0, depart);

        return circuit;
    }
}