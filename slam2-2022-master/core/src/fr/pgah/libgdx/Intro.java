package fr.pgah.libgdx;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Intro extends ApplicationAdapter {

  SpriteBatch batch;

  static int NB_COEUR = 3;
  int NB_SPRITES = 3;
  int longueurFenetre;
  int hauteurFenetre;
  int compteur;
  int rajoute;

  boolean gameOver;
  boolean degats;
  boolean invincible;
  Sprite indexSprite;

  ArrayList<Sprite> sprites;

  Joueur joueur;

  Vie[] coeurs;

  Texture imgfin;

  CliqueSouris souris;

  @Override
  public void create() {
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();

    gameOver = false;
    invincible = false;

    rajoute = 1800;

    sprites = new ArrayList<Sprite>();
    imgfin = new Texture("gameover.png");
    batch = new SpriteBatch();

    initialisationSprites();
    initialiserJoueur();
    initialiserVie();
    initialiserSouris();
  }

  public void initialiserJoueur() {
    joueur = new Joueur();
  }

  public void initialisationSprites() {

    for (int i = 0; i < NB_SPRITES; i++) {
      sprites.add(new Sprite());
    }
  }

  public void initialiserVie() {
    coeurs = new Vie[NB_COEUR];

    for (int i = 0; i < NB_COEUR; i++) {
      coeurs[i] = new Vie();
    }
  }

  public void initialiserSouris() {
    souris = new CliqueSouris();
  }

  private void reinitialiserArrierePlan() {
    // Gdx.gl.glClearColor(1, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  public void majEtat() {

    for (Sprite sprite : sprites) {
      sprite.majEtat();
    }

    // joueur.majEtat();

    souris.majEtat();
  }

  public void dessin() {
    batch.begin();

    for (Sprite sprite : sprites) {
      sprite.dessiner(batch);
    }

    // for (int i = 0; i < NB_COEUR; i++) {
    // coeurs[i].dessiner(batch);
    // }

    // joueur.dessinerJoueur(batch);

    batch.end();
  }

  public void majEtatJeu() {

    if (invincible == false) {
      if (souris.estEncollisionAvec(sprites) && souris.clicGauche()) {
        compteur = 120;
        invincible = true;

        for(int i=0; i<NB_SPRITES;i++){
         indexSprite=sprites.get(i);
        }

        sprites.remove(indexSprite);
      }
    }

    if (invincible == true) {
      compteur = compteur - 1;
    }

    if (compteur == 0 && invincible == true) {
      invincible = false;
      NB_SPRITES=NB_SPRITES-1;
    }

    if (sprites.isEmpty()) {
      gameOver = true;
      if ((gameOver == true)) {
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgfin, hauteurFenetre / 3, longueurFenetre / 3);
        batch.end();
      }
    }
  }

  @Override
  public void render() {
    if (gameOver == false) {
      reinitialiserArrierePlan();
      majEtatJeu();
      dessin();
      majEtat();
    } else {
      majEtatJeu();
    }
  }

}
