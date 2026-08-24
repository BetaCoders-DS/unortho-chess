package com.github.antcursor.render;

import com.github.antcursor.game.ChessGame;

import processing.core.PApplet;

/**
 * ProcessingRenderer
 */
public class ProcessingRenderer implements RenderI {
  private PApplet sketch;

  ProcessingRenderer(PApplet sketch) {
    this.sketch = sketch;
  }

  @Override
  public void render(ChessGame game) {
  }
}
