package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Protagoniste {

    Rectangle rect;
    Rectangle rectj;

    SpriteBatch batch;

    Texture img;

    int vitesse;
    int coordX;
    int coordY;
    int pas;

    int hauteurImage;
    int longueurImage;

    int longueurFenetre;
    int hauteurFenetre;

    boolean hit;
    boolean touch;
    boolean verif;

    public void majEtat() {
        deplacer();
        resterDansLeCadre();
    }

    private void deplacer() {

    }

    private void resterDansLeCadre() {

    }

    public void dessiner(SpriteBatch batch) {

    }

    public void dessinerJoueur(SpriteBatch batch) {

    }

    public boolean estEncollisionAvec(CliqueSouris souris) {
        return hit;
    }

    public boolean estEncollisionAvecSprite(ArrayList<Protagoniste> protagonistes) {

        return touch;

    }

  

}
