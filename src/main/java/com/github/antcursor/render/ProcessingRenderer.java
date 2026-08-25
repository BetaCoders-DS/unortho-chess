package com.github.antcursor.render;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.types.Position;

import processing.core.PApplet;

/**
 * ProcessingRenderer
 */
public class ProcessingRenderer implements RenderI {
  private PApplet sketch;

  private final int boardSize;
  private final Position boardPos;
  public ColorScheme colorScheme;

  public ProcessingRenderer(PApplet sketch, ColorScheme colorScheme) {
    this.sketch = sketch;
    this.boardSize = sketch.height - (sketch.height / 5);

    int offsety = sketch.height - this.boardSize;
    int offsetx = sketch.width - this.boardSize;
    this.boardPos = new Position(offsetx / 2, offsety / 2);

    this.colorScheme = colorScheme;
  }

  @Override
  public void render(ChessGame game) {
    drawEmptyBoard(game);
  }

  private void drawEmptyBoard(ChessGame game) {
    int nFiles = game.board().files();
    int nRanks = game.board().ranks();

    sketch.push();
    sketch.noStroke();

    float squareLen = boardSize / Math.min(nFiles, nRanks);
    int init_color = colorScheme.lightSquaresColor;

    for (int r = 0; r < nRanks; ++r) {
      int color = init_color;
      for (int f = 0; f < nFiles; ++f) {
        sketch.fill(color);
        sketch.square(
            boardPos.x() + f * squareLen,
            boardPos.y() + r * squareLen,
            squareLen);

        if (color == colorScheme.lightSquaresColor)
          color = colorScheme.darkSquaresColor;
        else
          color = colorScheme.lightSquaresColor;
      }
      if (init_color == colorScheme.lightSquaresColor)
        init_color = colorScheme.darkSquaresColor;
      else
        init_color = colorScheme.lightSquaresColor;
    }

    sketch.pop();
  }

  public static class ColorScheme {
    public int darkSquaresColor = 0xff9C7759;
    public int lightSquaresColor = 0xffCFBB9E;
  }
}
