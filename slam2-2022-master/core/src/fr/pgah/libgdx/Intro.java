package fr.pgah.libgdx;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Intro extends ApplicationAdapter {

  SpriteBatch batch;

  static int fragile;
  static int NB_COEUR;

  int NB_SPRITES;
  int compteur;
  int duree;
  int page;
  int ajouts;
  int boucle;

  int longueurFenetre;
  int hauteurFenetre;

  boolean rejouer;
  boolean gameOver;
  boolean victoire;
  boolean stop;
  boolean verif;
  boolean invincible;
  boolean jeu;

  Sprite indexSprite;

  ArrayList<Sprite> sprites;

  Joueur joueur;

  Vie[] coeurs;

  Texture imgvictoire;
  Texture imgdefaite;

  CliqueSouris souris;

  Scenario scenario;

  @Override
  public void create() {

    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();

    NB_COEUR = 3;
    NB_SPRITES = 7;

    sprites = new ArrayList<Sprite>();
    imgvictoire = new Texture("victoire.jpg");
    imgdefaite = new Texture("gameover.png");
    batch = new SpriteBatch();

    initialisationSprites();
    initialiserJoueur();
    initialiserVie();
    initialiserSouris();
    initialiserScenario();

    page = 0;
    compteur = 180;
    fragile = 0;
    boucle = 0;
    duree = 0;

    gameOver = false;
    victoire = false;
    rejouer = false;
    invincible = false;
    jeu = false;

  }

  public void initialiserScenario() {
    scenario = new Scenario();
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

    joueur.majEtat();

    souris.majEtat();
  }

  public void dessin() {
    batch.begin();

    for (Sprite sprite : sprites) {
      sprite.dessiner(batch);
    }

    for (int i = 0; i < NB_COEUR; i++) {
      coeurs[i].dessiner(batch);
    }

    joueur.dessinerJoueur(batch);

    souris.dessiner();

    batch.end();
  }

  public void afficher() {
    scenario.histoire();

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      page = page + 1;
    }

    if (page == 1) {
      scenario.instruction();
    }

  }

  public void majEtatJeu() {

    for (Sprite sprite : sprites) {
      if (souris.clicGauche() && sprite.estEncollisionAvec(souris)) {
        indexSprite = sprite;
        fragile = fragile + 1;
      }
    }
    sprites.remove(indexSprite);

    if (invincible == false) {
      if (joueur.estEncollisionAvec(sprites)) {
        duree = 120;
        invincible = true;
        NB_COEUR = NB_COEUR - 1;
      }
    }

    if (invincible == true) {
      duree = duree - 1;
    }

    if (duree == 0 && invincible == true) {
      invincible = false;
    }

    if (NB_COEUR == 0) {
      gameOver = true;
      if ((gameOver == true)) {
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgdefaite, hauteurFenetre / 3, longueurFenetre / 3);
        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
          page = page + 1;
        }

      }
    }

    if (sprites.isEmpty()) {
      victoire = true;
      if ((victoire == true)) {
        Gdx.gl.glClearColor(0, 0, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgvictoire, hauteurFenetre / 3, longueurFenetre / 3);
        batch.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
          page = page + 1;
        }

      }
    }
  }

  public void difficultee() {
    if (compteur != 0 && stop == false) {
      compteur = compteur - 1;
    }
    if (compteur <= 0) {
      sprites.add(new Sprite());
      compteur = 180;
    }

    if (sprites.size() == 10) {
      stop = true;
    }

    if (sprites.size() != 10) {
      stop = false;
    }

  }

  public static int getFragile() {
    return fragile;
  }

  @Override
  public void render() {
    jeux();
    recommencer();

  }

  public void jeux() {
    if (page <= 1) {
      reinitialiserArrierePlan();
      afficher();
    }

    if (page == 2) {
      if (gameOver == false && victoire == false) {
        reinitialiserArrierePlan();
        majEtatJeu();
        dessin();
        majEtat();
        difficultee();
      } else {
        majEtatJeu();
        scenario.passer();
      }
    }
    if (page == 3) {
      scenario.recommencer();
      batch.begin();
      joueur.dessinerJoueur(batch);
      batch.end();
      joueur.majEtat();

      if (scenario.estEncollisionAvecValider(joueur) && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        rejouer = true;
        boucle = 1;
      }

      if (scenario.estEncollisionAvecRefuser(joueur) && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
        rejouer = false;
        verif = true;
        page = page + 1;
      }

    }

    if (rejouer == false && verif == true && page == 4) {
      scenario.credit();
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
        page = page + 1;
      }
    }

    if (page == 5) {
      scenario.remerciement();
      jeu = true;
    }

    if (jeu == true && Gdx.input.isKeyJustPressed(Input.Keys.F)) {
      System.exit(0);
    }

  }

  public void recommencer() {
    if (rejouer == true) {
      reinitialiserArrierePlan();
      create();
      do {
        jeux();
      } while (boucle == 1);
    }

  }

}
