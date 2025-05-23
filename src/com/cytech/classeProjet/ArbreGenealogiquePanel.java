package com.cytech.classeProjet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

/**
 * Panel class for displaying a genealogical tree.
 * This class handles the visual representation of the tree, including the positioning
 * and drawing of individuals and their relationships.
 */
@SuppressWarnings("serial")
public class ArbreGenealogiquePanel extends JPanel {
    /** The genealogical tree to display */
    private ArbreGenealogique arbre;
    
    /** Map storing the position of each person in the panel */
    private Map<Personne, Point> positions = new HashMap<>();
    
    /** Map storing the dimensions of each person's display box */
    private Map<Personne, Dimension> dimensions = new HashMap<>();
    
    /** Map storing the children of each parent */
    private Map<Personne, List<Personne>> enfantsParParent = new HashMap<>();
    
    /** Width of each person's display box */
    private static final int LARGEUR_NOEUD = 120;
    
    /** Height of each person's display box */
    private static final int HAUTEUR_NOEUD = 80;
    
    /** Horizontal spacing between elements */
    private static final int ESPACE_HORIZONTAL = 40;
    
    /** Vertical spacing between elements */
    private static final int ESPACE_VERTICAL = 60;
    
    /** Distance between spouses */
    private static final int DISTANCE_COUPLE = 20;
    
    /** Color for male individuals */
    private static final Color COULEUR_HOMME = new Color(173, 216, 230);  // Light blue
    
    /** Color for female individuals */
    private static final Color COULEUR_FEMME = new Color(255, 182, 193);  // Light pink
    
    /** Color for relationship lines */
    private static final Color COULEUR_LIEN = new Color(100, 100, 100);   // Dark gray
    
    /** Color for spouse relationship lines */
    private static final Color COULEUR_LIEN_COUPLE = new Color(255, 0, 0); // Red
    
    /** Color for text */
    private static final Color COULEUR_TEXTE = new Color(50, 50, 50);     // Almost black
    
    /** Font for person names */
    private static final Font FONT_PERSONNE = new Font("Arial", Font.BOLD, 12);
    
    /** Font for additional information */
    private static final Font FONT_INFO = new Font("Arial", Font.PLAIN, 10);
    
    /** Total width of the panel */
    private int largeurTotale;
    
    /** Total height of the panel */
    private int hauteurTotale;

    /**
     * Constructor for the genealogical tree panel.
     * 
     * @param arbre The genealogical tree to display
     */
    public ArbreGenealogiquePanel(ArbreGenealogique arbre) {
        this.arbre = arbre;
        setBackground(Color.WHITE);
        calculerPositions();
    }

    /**
     * Calculates the positions of all individuals in the tree.
     * This method organizes the layout of the tree, handling parent-child relationships,
     * sibling relationships, and spouse relationships.
     */
    private void calculerPositions() {
        if (arbre == null || arbre.getRacine() == null) return;
        
        positions.clear();
        dimensions.clear();
        enfantsParParent.clear();
        
        // Build parent-child structure
        for (Personne p : arbre.getPersonnes()) {
            for (Personne parent : p.getParents()) {
                if (arbre.getPersonnes().contains(parent)) {
                    enfantsParParent.putIfAbsent(parent, new ArrayList<>());
                    enfantsParParent.get(parent).add(p);
                }
            }
        }
        
        // Group people by depth
        Map<Integer, List<Personne>> personnesParProfondeur = new HashMap<>();
        
        for (Personne p : arbre.getPersonnes()) {
            int profondeur = p.getProfondeur();
            personnesParProfondeur.putIfAbsent(profondeur, new ArrayList<>());
            personnesParProfondeur.get(profondeur).add(p);
        }
        
        // Sort depths in reverse order (ancestors at top, descendants at bottom)
        List<Integer> profondeurs = new ArrayList<>(personnesParProfondeur.keySet());
        Collections.sort(profondeurs, Collections.reverseOrder());
        
        // Calculate total height
        hauteurTotale = (profondeurs.size() * HAUTEUR_NOEUD) + ((profondeurs.size() + 1) * ESPACE_VERTICAL);
        
        // First pass: position people by level without considering relationships
        int maxLargeurNiveau = positionnerPersonnesParNiveau(personnesParProfondeur, profondeurs);
        largeurTotale = maxLargeurNiveau + (2 * ESPACE_HORIZONTAL);
        
        // Second pass: position spouses side by side
        positionnerCouples();
        
        // Third pass: adjust positions to avoid overlaps
        eviterChevauchements();
        
        // Fourth pass: position parents above their children
        positionnerParentsAuDessusEnfants();
        
        // Recalculate total dimensions after adjustments
        recalculerDimensionsTotales();
    }

    /**
     * Positions people by level in the tree.
     * 
     * @param personnesParProfondeur Map of people grouped by depth
     * @param profondeurs List of depths in reverse order
     * @return The maximum width needed for any level
     */
    private int positionnerPersonnesParNiveau(Map<Integer, List<Personne>> personnesParProfondeur, List<Integer> profondeurs) {
        int maxLargeurNiveau = 0;
        int indexNiveau = 0;
        
        for (Integer profondeur : profondeurs) {
            List<Personne> personnesNiveau = personnesParProfondeur.get(profondeur);
            personnesNiveau.sort(Comparator.comparing(Personne::getNom).thenComparing(Personne::getPrenom));
            
            int largeurNiveau = calculerLargeurNiveau(personnesNiveau);
            maxLargeurNiveau = Math.max(maxLargeurNiveau, largeurNiveau);
            
            int startX = ESPACE_HORIZONTAL;
            int y = ESPACE_VERTICAL + (indexNiveau * (HAUTEUR_NOEUD + ESPACE_VERTICAL));
            
            for (Personne p : personnesNiveau) {
                positions.put(p, new Point(startX, y));
                dimensions.put(p, new Dimension(LARGEUR_NOEUD, HAUTEUR_NOEUD));
                startX += LARGEUR_NOEUD + ESPACE_HORIZONTAL;
            }
            
            indexNiveau++;
        }
        
        return maxLargeurNiveau;
    }

    /**
     * Calculates the width needed for a level of the tree.
     * 
     * @param personnes List of people at this level
     * @return The required width for this level
     */
    private int calculerLargeurNiveau(List<Personne> personnes) {
        return (personnes.size() * LARGEUR_NOEUD) + ((personnes.size() - 1) * ESPACE_HORIZONTAL) + (2 * ESPACE_HORIZONTAL);
    }

    /**
     * Positions spouses side by side in the tree.
     * This method identifies couples and adjusts their positions to be adjacent.
     */
    private void positionnerCouples() {
        Set<Personne> personnesTraitees = new HashSet<>();
        
        for (Personne p : arbre.getPersonnes()) {
            if (personnesTraitees.contains(p)) continue;
            
            Personne conjoint = p.getConjoint();
            if (conjoint != null && arbre.getPersonnes().contains(conjoint) && !personnesTraitees.contains(conjoint)) {
                personnesTraitees.add(p);
                personnesTraitees.add(conjoint);
                
                Point posP = positions.get(p);
                Point posConj = positions.get(conjoint);
                
                if (posP != null && posConj != null) {
                    int xMoyen = (posP.x + posConj.x) / 2;
                    
                    positions.put(p, new Point(xMoyen - LARGEUR_NOEUD - DISTANCE_COUPLE/2, posP.y));
                    positions.put(conjoint, new Point(xMoyen + DISTANCE_COUPLE/2, posConj.y));
                }
            }
        }
    }

    /**
     * Adjusts positions to avoid overlapping elements.
     * This method ensures that no two elements overlap horizontally at the same level.
     */
    private void eviterChevauchements() {
        Map<Integer, List<Personne>> personnesParY = new HashMap<>();
        
        for (Personne p : arbre.getPersonnes()) {
            Point pos = positions.get(p);
            if (pos != null) {
                int y = pos.y;
                personnesParY.putIfAbsent(y, new ArrayList<>());
                personnesParY.get(y).add(p);
            }
        }
        
        for (int y : personnesParY.keySet()) {
            List<Personne> personnesNiveau = personnesParY.get(y);
            personnesNiveau.sort(Comparator.comparing(p -> positions.get(p).x));
            
            for (int i = 1; i < personnesNiveau.size(); i++) {
                Personne p1 = personnesNiveau.get(i-1);
                Personne p2 = personnesNiveau.get(i);
                
                Point pos1 = positions.get(p1);
                Point pos2 = positions.get(p2);
                
                int finP1 = pos1.x + LARGEUR_NOEUD;
                int debutP2 = pos2.x;
                
                if (finP1 + ESPACE_HORIZONTAL > debutP2) {
                    int decalage = finP1 + ESPACE_HORIZONTAL - debutP2;
                    
                    for (int j = i; j < personnesNiveau.size(); j++) {
                        Personne pj = personnesNiveau.get(j);
                        Point posj = positions.get(pj);
                        positions.put(pj, new Point(posj.x + decalage, posj.y));
                    }
                }
            }
        }
    }

    /**
     * Positions parents above their children in the tree.
     * This method ensures that parents are centered above their children.
     */
    private void positionnerParentsAuDessusEnfants() {
        for (Personne parent : arbre.getPersonnes()) {
            List<Personne> enfants = enfantsParParent.getOrDefault(parent, Collections.emptyList());
            
            if (!enfants.isEmpty()) {
                Personne conjoint = parent.getConjoint();
                boolean aConjoint = conjoint != null && arbre.getPersonnes().contains(conjoint);
                
                int xMinEnfants = enfants.stream()
                        .mapToInt(e -> positions.get(e).x)
                        .min()
                        .orElse(0);
                int xMaxEnfants = enfants.stream()
                        .mapToInt(e -> positions.get(e).x + LARGEUR_NOEUD)
                        .max()
                        .orElse(0);
                
                int centreEnfants = (xMinEnfants + xMaxEnfants) / 2;
                
                Point posParent = positions.get(parent);
                
                if (aConjoint) {
                    int xParent = centreEnfants - LARGEUR_NOEUD - DISTANCE_COUPLE/2;
                    positions.put(parent, new Point(xParent, posParent.y));
                    
                    Point posConjoint = positions.get(conjoint);
                    int xConjoint = centreEnfants + DISTANCE_COUPLE/2;
                    positions.put(conjoint, new Point(xConjoint, posConjoint.y));
                } else {
                    int xParent = centreEnfants - LARGEUR_NOEUD/2;
                    positions.put(parent, new Point(xParent, posParent.y));
                }
            }
        }
        
        eviterChevauchements();
    }

    /**
     * Recalculates the total dimensions of the panel.
     * This method updates the panel's size based on the positions of all elements.
     */
    private void recalculerDimensionsTotales() {
        int xMax = 0;
        int yMax = 0;
        
        for (Personne p : arbre.getPersonnes()) {
            Point pos = positions.get(p);
            if (pos != null) {
                xMax = Math.max(xMax, pos.x + LARGEUR_NOEUD);
                yMax = Math.max(yMax, pos.y + HAUTEUR_NOEUD);
            }
        }
        
        largeurTotale = xMax + ESPACE_HORIZONTAL;
        hauteurTotale = yMax + ESPACE_VERTICAL;
    }

    /**
     * Paints the genealogical tree.
     * This method is called automatically by Swing to render the panel.
     * 
     * @param g The graphics context to use for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        dessinerLiens(g2d);
        
        for (Personne p : arbre.getPersonnes()) {
            dessinerPersonne(g2d, p);
        }
    }

    /**
     * Draws all relationship lines in the tree.
     * 
     * @param g2d The graphics context to use for drawing
     */
    private void dessinerLiens(Graphics2D g2d) {
        dessinerLiensParentEnfant(g2d);
        dessinerLiensFratrie(g2d);
        dessinerLiensCouples(g2d);
    }

    /**
     * Draws parent-child relationship lines.
     * 
     * @param g2d The graphics context to use for drawing
     */
    private void dessinerLiensParentEnfant(Graphics2D g2d) {
        g2d.setColor(COULEUR_LIEN);
        g2d.setStroke(new BasicStroke(2));
        
        for (Personne parent : arbre.getPersonnes()) {
            Point posParent = positions.get(parent);
            if (posParent == null) continue;
            
            for (Personne enfant : parent.getEnfants()) {
                Point posEnfant = positions.get(enfant);
                if (posEnfant == null) continue;
                
                int x1 = posParent.x + LARGEUR_NOEUD/2;
                int y1 = posParent.y + HAUTEUR_NOEUD;
                int x2 = posEnfant.x + LARGEUR_NOEUD/2;
                int y2 = posEnfant.y;
                
                dessinerFleche(g2d, x1, y1, x2, y2);
            }
        }
    }

    /**
     * Draws sibling relationship lines.
     * 
     * @param g2d The graphics context to use for drawing
     */
    private void dessinerLiensFratrie(Graphics2D g2d) {
        g2d.setColor(COULEUR_LIEN);
        g2d.setStroke(new BasicStroke(1));
        
        for (Personne p : arbre.getPersonnes()) {
            Point posP = positions.get(p);
            if (posP == null) continue;
            
            for (Personne frereSoeur : p.getFreresEtSoeurs()) {
                if (frereSoeur.getNom().compareTo(p.getNom()) > 0) continue; // Draw only once
                
                Point posFrereSoeur = positions.get(frereSoeur);
                if (posFrereSoeur == null) continue;
                
                int x1 = posP.x;
                int y1 = posP.y + HAUTEUR_NOEUD/2;
                int x2 = posFrereSoeur.x + LARGEUR_NOEUD;
                int y2 = posFrereSoeur.y + HAUTEUR_NOEUD/2;
                
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    /**
     * Draws spouse relationship lines.
     * 
     * @param g2d The graphics context to use for drawing
     */
    private void dessinerLiensCouples(Graphics2D g2d) {
        g2d.setColor(COULEUR_LIEN_COUPLE);
        g2d.setStroke(new BasicStroke(2));
        
        for (Personne p : arbre.getPersonnes()) {
            Personne conjoint = p.getConjoint();
            if (conjoint == null || p.getNom().compareTo(conjoint.getNom()) > 0) continue; // Draw only once
            
            Point posP = positions.get(p);
            Point posConjoint = positions.get(conjoint);
            if (posP == null || posConjoint == null) continue;
            
            int x1 = posP.x + LARGEUR_NOEUD;
            int y1 = posP.y + HAUTEUR_NOEUD/2;
            int x2 = posConjoint.x;
            int y2 = posConjoint.y + HAUTEUR_NOEUD/2;
            
            g2d.drawLine(x1, y1, x2, y2);
            dessinerCoeur(g2d, (x1 + x2)/2, (y1 + y2)/2);
        }
    }

    /**
     * Draws a heart symbol for spouse relationships.
     * 
     * @param g2d The graphics context to use for drawing
     * @param x The x-coordinate of the heart's center
     * @param y The y-coordinate of the heart's center
     */
    private void dessinerCoeur(Graphics2D g2d, int x, int y) {
        int taille = 10;
        g2d.setColor(COULEUR_LIEN_COUPLE);
        g2d.fillOval(x - taille/2, y - taille/2, taille, taille);
    }

    /**
     * Draws an arrow for parent-child relationships.
     * 
     * @param g2d The graphics context to use for drawing
     * @param x1 The x-coordinate of the start point
     * @param y1 The y-coordinate of the start point
     * @param x2 The x-coordinate of the end point
     * @param y2 The y-coordinate of the end point
     */
    private void dessinerFleche(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
        
        int taille = 8;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        
        int[] xPoints = {x2, x2 - taille, x2 - taille};
        int[] yPoints = {y2, y2 - taille/2, y2 + taille/2};
        
        AffineTransform oldTransform = g2d.getTransform();
        g2d.translate(x2, y2);
        g2d.rotate(angle);
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setTransform(oldTransform);
    }

    /**
     * Draws a person's information box.
     * 
     * @param g2d The graphics context to use for drawing
     * @param p The person to draw
     */
    private void dessinerPersonne(Graphics2D g2d, Personne p) {
        Point pos = positions.get(p);
        if (pos == null) return;
        
        // Draw background
        g2d.setColor(p.getGenre() == Genre.HOMME ? COULEUR_HOMME : COULEUR_FEMME);
        g2d.fillRoundRect(pos.x, pos.y, LARGEUR_NOEUD, HAUTEUR_NOEUD, 10, 10);
        
        // Draw border
        g2d.setColor(COULEUR_TEXTE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(pos.x, pos.y, LARGEUR_NOEUD, HAUTEUR_NOEUD, 10, 10);
        
        // Draw name
        g2d.setFont(FONT_PERSONNE);
        String nomComplet = p.getNomComplet();
        FontMetrics fm = g2d.getFontMetrics();
        String nomTronque = tronquerTexte(nomComplet, fm, LARGEUR_NOEUD - 10);
        g2d.drawString(nomTronque, pos.x + 5, pos.y + 20);
        
        // Draw dates
        g2d.setFont(FONT_INFO);
        String dates = formatDates(p);
        g2d.drawString(dates, pos.x + 5, pos.y + 35);
        
        // Draw nationality
        if (p.getNationalite() != null) {
            g2d.drawString(p.getNationalite(), pos.x + 5, pos.y + 50);
        }
    }

    /**
     * Formats a person's birth and death dates.
     * 
     * @param p The person whose dates to format
     * @return A string containing the formatted dates
     */
    private String formatDates(Personne p) {
        StringBuilder sb = new StringBuilder();
        if (p.getDateNaissance() != null) {
            sb.append(p.getDateNaissance().getYear() + 1900);
        }
        sb.append(" - ");
        if (p.getDateDeces() != null) {
            sb.append(p.getDateDeces().getYear() + 1900);
        }
        return sb.toString();
    }

    /**
     * Truncates text to fit within a maximum width.
     * 
     * @param texte The text to truncate
     * @param fm The font metrics to use for measuring
     * @param largeurMax The maximum width allowed
     * @return The truncated text
     */
    private String tronquerTexte(String texte, FontMetrics fm, int largeurMax) {
        if (fm.stringWidth(texte) <= largeurMax) return texte;
        
        String resultat = texte;
        while (fm.stringWidth(resultat + "...") > largeurMax && resultat.length() > 0) {
            resultat = resultat.substring(0, resultat.length() - 1);
        }
        return resultat + "...";
    }

    /**
     * Returns the preferred size of the panel.
     * 
     * @return The preferred dimensions of the panel
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(largeurTotale, hauteurTotale);
    }

    /**
     * Updates the panel with a new genealogical tree.
     * 
     * @param nouveauArbre The new genealogical tree to display
     */
    public void mettreAJour(ArbreGenealogique nouveauArbre) {
        this.arbre = nouveauArbre;
        calculerPositions();
        repaint();
    }
}