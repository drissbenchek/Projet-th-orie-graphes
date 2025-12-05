package utils;

import java.util.Scanner;

/**
 * Classe utilitaire pour les entrées/sorties sécurisées et l'affichage.
 * Gère les saisies utilisateur, les pauses, et les séparateurs pour une
 * interface console propre.
 */
public final class Utils {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur privé pour empêcher l'instanciation.
     */
    private Utils() {
        throw new UnsupportedOperationException("Classe utilitaire - instanciation interdite");
    }

    /**
     * Lit un entier sécurisé.
     * 
     * @param message Le message à afficher.
     * @return L'entier saisi.
     */
    public static int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            String ligne = scanner.nextLine().trim();
            if (ligne.isEmpty()) {
                System.out.println("   Entrée vide, veuillez réessayer.");
                continue;
            }
            try {
                return Integer.parseInt(ligne);
            } catch (NumberFormatException e) {
                System.out.println("   Erreur : veuillez entrer un nombre entier valide.");
            }
        }
    }

    public static int lireEntier() {
        return lireEntier("--> ");
    }

    /**
     * Lit un double sécurisé.
     * 
     * @param message Le message à afficher.
     * @return Le double saisi.
     */
    public static double lireDouble(String message) {
        while (true) {
            System.out.print(message);
            String ligne = scanner.nextLine().trim();
            if (ligne.isEmpty())
                continue;
            try {
                return Double.parseDouble(ligne.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("   Erreur : entrez un nombre décimal valide (ex: 10.5)");
            }
        }
    }

    public static double lireDouble() {
        return lireDouble("--> ");
    }

    /**
     * Lit une ligne de texte.
     * 
     * @param message Le message à afficher.
     * @return La ligne saisie.
     */
    public static String lireLigne(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static String lireLigne() {
        return lireLigne("--> ");
    }

    /**
     * Pause avec message.
     */
    public static void appuyerPourContinuer() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    /**
     * Affiche un séparateur.
     */
    public static void afficherSeparateur() {
        System.out.println("═".repeat(80));
    }

    /**
     * Affiche un titre avec séparateurs.
     * 
     * @param titre Le titre à afficher.
     */
    public static void afficherTitre(String titre) {
        afficherSeparateur();
        System.out.println("   " + titre);
        afficherSeparateur();
    }

    /**
     * Ferme le scanner.
     */
    public static void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
