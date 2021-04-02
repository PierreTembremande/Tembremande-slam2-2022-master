package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.ApplicationAdapter;

public class Joueur extends ApplicationAdapter {

    Rectangle rectj;

    SpriteBatch batch;

    Texture img = new Texture("joueur.jpg");

    int coordX = 0;
    int coordY = 0;
    int pas = 8;

    int hauteurImage;
    int longueurImage;

    int longueurFenetre;
    int hauteurFenetre;

    double facteurTaille2;

    public Joueur() {

        initialiser();

    }

    private void initialiser() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        facteurTaille2 = 0.35;

        longueurImage = (int) (img.getWidth() * facteurTaille2);// ne fonctionne pas correctement
        hauteurImage = (int) (img.getHeight() * facteurTaille2);

        rectj = new Rectangle(coordX, coordY, longueurImage, hauteurImage);
    }

    private void deplacer() {

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            coordX = coordX - pas;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            coordY = coordY + pas;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            coordY = coordY - pas;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            coordX = coordX + pas;
        }

        rectj.setPosition(coordX, coordY);
    }

    private void resterDansLeCadre() {

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

        rectj.setPosition(coordX, coordY);
    }

    public void majEtat() {
        initialiser();
        deplacer();
        resterDansLeCadre();
    }

    public void dessinerJoueur(SpriteBatch batch) {

        batch.draw(img, coordX, coordY, longueurImage, hauteurImage);

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
        if (rectj.overlaps(sprite.rect)) {
            return true;
        } else {
            return false;
        }

    }

}
