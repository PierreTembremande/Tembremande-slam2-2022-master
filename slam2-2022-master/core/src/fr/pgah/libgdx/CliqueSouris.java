package fr.pgah.libgdx;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class CliqueSouris {

    Rectangle rect;

    int coordX=0;
    int coordY=0;

    int hauteurImage;
    int longueurImage;

    int longueurFenetre;
    int hauteurFenetre;

    double facteurTailleSouris;

    CliqueSouris souris;

    public CliqueSouris() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        facteurTailleSouris = 0.15;

        rect = new Rectangle(coordX, coordY, longueurImage, hauteurImage);

        // longueurImage = (int) (img.getWidth() * facteurTailleSouris);
        // hauteurImage = (int) (img.getHeight() * facteurTailleSouris);

        coordX = Gdx.input.getX();
        coordY = Gdx.graphics.getHeight() - Gdx.input.getY();
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

    private boolean estEncollisionAvec(Sprite sprite) {
        if (rect.overlaps(sprite.rect)) {
            return true;
        } else {
            return false;
        }

    }

}
