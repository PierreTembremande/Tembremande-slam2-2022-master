package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Protagonistes {

    SpriteBatch batch;

    ArrayList<Protagoniste> protagonistes;
    Protagoniste indexSprite;

    static int fragile;

    int longueurFenetre;
    int hauteurFenetre;
    int duree;
    int compteur;

    boolean invincible;
    boolean gameOver;
    boolean victoire;
    boolean stop;

    Texture imgvictoire;
    Texture imgdefaite;

    public Protagonistes() {

        longueurFenetre = Gdx.graphics.getWidth();
        hauteurFenetre = Gdx.graphics.getHeight();

        protagonistes = new ArrayList<Protagoniste>();
        batch = new SpriteBatch();
        imgvictoire = new Texture("victoire.jpg");
        imgdefaite = new Texture("gameover.png");

        fragile = 0;
        duree = 0;
        compteur = 180;

        invincible = false;
        gameOver = false;
        victoire = false;

    }

    public void initialiserJoueur() {
        protagonistes.add(new Joueur());
    }

    public void initialisationEnnemies() {

        for (int i = 0; i < Intro.NB_ENNEMIES; i++) {
            protagonistes.add(new Ennemis());
        }
    }

    public void majEtat() {

        for (Protagoniste protagoniste : protagonistes) {
            protagoniste.majEtat();
        }
    }

    public void dessiner() {
        for (Protagoniste protagoniste : protagonistes) {
            protagoniste.dessiner(batch);
        }
    }

    public void majEtatJeu(){

        for (Protagoniste protagoniste : protagonistes) {
            if (Intro.souris.clicGauche() && protagoniste.estEncollisionAvec(Intro.souris)) {
                indexSprite = protagoniste;
                fragile = fragile + 1;
            }
        }

        protagonistes.remove(indexSprite);

        if (invincible == false) {
            for (Protagoniste protagoniste : protagonistes) {
                if (protagoniste.estEncollisionAvecSprite(protagonistes)) {
                    duree = 120;
                    invincible = true;
                    Intro.NB_COEUR = Intro.NB_COEUR - 1;
                }
            }
        }

        if (invincible == true) {
            duree = duree - 1;
        }

        if (duree == 0 && invincible == true) {
            invincible = false;
        }

        if (Intro.NB_COEUR == 0) {
            gameOver = true;
            if ((gameOver == true)) {
                Gdx.gl.glClearColor(0, 0, .25f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.draw(imgdefaite, hauteurFenetre / 3, longueurFenetre / 3);
                batch.end();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    Intro.page = Intro.page + 1;
                }

            }
        }

        if (protagonistes.size() == 1) {
            victoire = true;
            if ((victoire == true)) {
                Gdx.gl.glClearColor(0, 0, .25f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.draw(imgvictoire, hauteurFenetre / 3, longueurFenetre / 3);
                batch.end();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    Intro.page = Intro.page + 1;
                }
            }
        }
    }

    public void difficultee(){
        if (compteur != 0 && stop == false)

        {
            compteur = compteur - 1;
        }
        if (compteur <= 0) {
            protagonistes.add(new Ennemis());
            compteur = 180;
        }

        if (protagonistes.size() == 11) {
            stop = true;
        }

        if (protagonistes.size() != 10) {
            stop = false;
        }

    }

  public static int getFragile() {
    return fragile;
  }

}
