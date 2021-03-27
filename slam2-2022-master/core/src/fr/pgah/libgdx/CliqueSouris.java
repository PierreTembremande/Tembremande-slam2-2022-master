package fr.pgah.libgdx;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CliqueSouris {
    Rectangle rect;

    Pixmap imag = new Pixmap(Gdx.files.internal("curseur.png"));

    int coordX;
    int coordY;

    int hauteurImage;
    int longueurImage;

    int longueurFenetre;
    int hauteurFenetre;

    double facteurTailleSouris;

    CliqueSouris souris;

    Cursor Curseur;

    public CliqueSouris() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        rect = new Rectangle(coordX, coordY, longueurImage, hauteurImage);

        coordX = longueurImage/2;
        coordY = hauteurImage/2;

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(imag, 0, 0));
        imag.dispose();
    }

    public boolean clicGauche() {
        if (appuyeClicGauche()) {
            return true;
        }
        return false;
    }

    private boolean appuyeClicGauche() {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
    
    private void actualiser(){
        coordX = Gdx.input.getX();
        coordY = Gdx.graphics.getHeight() - Gdx.input.getY();
        rect.setPosition(coordX, coordY);
    }

    public void majEtat(){
        resterDansLeCadreSouris();
        actualiser();
    }

    public boolean estEncollisionAvec(ArrayList<Sprite> sprites) {
        for (Sprite sprite : sprites) {
            if (estEncollisionAvec(sprite)) {
                return true;
            }
        }
        return false;
    }

    public boolean estEncollisionAvec(Sprite sprite) {
        if (rect.overlaps(sprite.rect)) {
            return true;
        } else {
            return false;
        }

    }

}
