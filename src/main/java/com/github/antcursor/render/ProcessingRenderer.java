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
  public ColorScheme colorScheme = new ColorScheme();

  public ProcessingRenderer(PApplet sketch) {
    this.sketch = sketch;
    this.boardSize = sketch.height - (sketch.height / 5);

    int offsety = sketch.height - this.boardSize;
    int offsetx = sketch.width - this.boardSize;
    this.boardPos = new Position(offsetx / 2, offsety / 2);
  }

  @Override
  public void render(ChessGame game) {
    drawEmptyBoard(game.board().files(), game.board().ranks());
  }

  private void drawEmptyBoard(int nFiles, int nRanks) {
    sketch.push();
    sketch.noStroke();

    float squareLen = boardSize / Math.min(nFiles, nRanks);
    int init_color = colorScheme.lightSquaresColor;

    for (int r = 0; r < squareLen * nRanks; r += squareLen) {
      int color = init_color;
      for (int f = 0; f < squareLen * nFiles; f += squareLen) {
        sketch.fill(color);
        sketch.square(boardPos.x() + f, boardPos.y() + r, squareLen);

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

  public class ColorScheme {
    public int darkSquaresColor = 0xff9C7759;
    public int lightSquaresColor = 0xffCFBB9E;
  }
}
