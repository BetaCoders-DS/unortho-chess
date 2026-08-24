package com.github.antcursor;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.render.ProcessingRenderer;
import com.github.antcursor.render.RenderI;

import processing.core.PApplet;

/**
 * Sketch
 */
class Sketch extends PApplet {
  private Config config;
  private RenderI renderer;
  private ChessGame game = new ChessGame();

  Sketch(Config cfg) {
    config = cfg;
  }

  @Override
  public void settings() {
    size(config.width(), config.height());
  }

  @Override
  public void setup() {
    renderer = new ProcessingRenderer(this);

  }

  @Override
  public void draw() {
    renderer.render(game);
  }
}
