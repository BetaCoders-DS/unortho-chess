package com.github.antcursor.render;

import com.github.antcursor.board.Board;

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
  public void render(Board board) {
  }
}
