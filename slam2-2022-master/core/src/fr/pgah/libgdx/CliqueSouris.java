package fr.pgah.libgdx;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;

public class CliqueSouris {
    Rectangle rect;

    private Pixmap imag = new Pixmap(Gdx.files.internal("curseur.png"));

    private int coordX;
    private int coordY;

    private int hauteurImage;
    private int longueurImage;

    private int longueurFenetre;
    private int hauteurFenetre;

    private static int degats;

    public CliqueSouris() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        rect = new Rectangle(coordX, coordY, longueurImage, hauteurImage);

        coordX = longueurImage / 2;
        coordY = hauteurImage / 2;

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(imag, 0, 0));

        degats = 3;

    }

    public void dessiner() {
        if (Protagonistes.getFragile() == 8) {
            imag = new Pixmap(Gdx.files.internal("curseurBrise.png"));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(imag, 0, 0));
        }

        if (Protagonistes.getFragile() == 16) {
            imag = new Pixmap(Gdx.files.internal("curseurPoussiere.png"));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(imag, 0, 0));
        }
    }

    public boolean clicGauche() {
        if (appuyeClicGauche()) {
            return true;
        }
        return false;
    }

    private boolean appuyeClicGauche() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            return true;
        }
        return false;
    }

    private void resterDansLeCadreSouris() {

        // Gestion bordure droite
        if (coordX + longueurImage > longueurFenetre) {
            coordX = longueurFenetre - longueurImage;

        }

        // Gestion bordure gauche
        if (coordX < 0) {
            coordX = 0;

        }

        // Gestion bordures haute
        if (coordY + hauteurImage > hauteurFenetre) {
            coordY = hauteurFenetre - hauteurImage;

        }

        // Gestion bordure basse
        if (coordY < 0) {
            coordY = 0;

        }

        rect.setPosition(coordX, coordY);
    }

    private void actualiser() {
        coordX = Gdx.input.getX();
        coordY = Gdx.graphics.getHeight() - Gdx.input.getY();
        rect.setPosition(coordX, coordY);
    }

    public void majEtat() {
        resterDansLeCadreSouris();
        actualiser();
        degradation();
    }

    public boolean estEncollisionAvec(ArrayList<Ennemis> sprites) {
        for (Ennemis sprite : sprites) {
            if (estEncollisionAvec(sprite)) {
                return true;
            }
        }
        return false;
    }

    public boolean estEncollisionAvec(Ennemis sprite) {
        if (rect.overlaps(sprite.rect)) {
            return true;
        } else {
            return false;
        }

    }

    public static int getDegats() {
        return degats;
    }

    private void degradation() {
        if (Protagonistes.getFragile() >= 8 && Protagonistes.getFragile() < 16) {
            degats = 2;
        } else if (Protagonistes.getFragile() >= 16) {
            degats = 1;
        }
    }

}
