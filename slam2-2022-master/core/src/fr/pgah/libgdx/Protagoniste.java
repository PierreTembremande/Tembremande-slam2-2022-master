package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Protagoniste {

    Rectangle rect;

    Texture img;

    int vitesse;
    int coordX;
    int coordY;
    int pas;
    int resistance;

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

    protected abstract void deplacer();

    protected abstract void resterDansLeCadre();

    public void dessiner(SpriteBatch batch) {

    }

    protected void dessinerJoueur(SpriteBatch batch){}

    protected boolean estEncollisionAvec(CliqueSouris souris) {
        return hit;
    }

    protected boolean estEncollisionAvecSprite(ArrayList<Protagoniste> protagonistes) {
        return touch;
    }

    protected int getResistance(){
        return resistance;
    }

}
