package com.github.antcursor;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.render.ProcessingRenderer;
import com.github.antcursor.render.RenderI;
import com.github.antcursor.render.ProcessingRenderer.ColorScheme;

import processing.core.PApplet;

/**
 * Sketch
 */
class Sketch extends PApplet {
  private Config config;
  private RenderI renderer;
  private ChessGame game;
  private ColorScheme colorScheme;

  Sketch(Config cfg) {
    config = cfg;
  }

  @Override
  public void settings() {
    size(config.width(), config.height());
  }

  @Override
  public void setup() {
    game = new ChessGame();
    colorScheme = new ColorScheme();
    renderer = new ProcessingRenderer(this, colorScheme);
  }

  @Override
  public void draw() {
    renderer.render(game);
  }
}
