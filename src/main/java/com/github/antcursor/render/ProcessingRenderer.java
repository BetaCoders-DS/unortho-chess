package com.github.antcursor.render;

import java.util.Map;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.types.Position;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

/**
 * ProcessingRenderer
 */
public class ProcessingRenderer implements RenderI {
  private PApplet sketch;

  private final int boardSize;
  private final Position boardPos;
  private Map<Character, PImage> pieceMap;
  public ColorScheme colorScheme;

  public ProcessingRenderer(PApplet sketch, ColorScheme colorScheme, Map<Character, PImage> pieceMap) {
    this.sketch = sketch;
    this.boardSize = sketch.height - (sketch.height / 5);

    int offsety = sketch.height - this.boardSize;
    int offsetx = sketch.width - this.boardSize;
    this.boardPos = new Position(offsetx / 2, offsety / 2);

    this.colorScheme = colorScheme;
    this.pieceMap = pieceMap;
  }

  @Override
  public void render(ChessGame game) {
    drawEmptyBoard(game);
    drawPieces(game);
  }

  private void drawEmptyBoard(ChessGame game) {
    int nFiles = game.board().files();
    int nRanks = game.board().ranks();

    sketch.push();
    sketch.noStroke();

    float squareLen = boardSize / Math.min(nFiles, nRanks);

    for (int r = 0; r < nRanks; ++r)
      for (int f = 0; f < nFiles; ++f) {
        int color = (r + f) % 2 == 0 ? colorScheme.lightSquaresColor : colorScheme.darkSquaresColor;
        sketch.fill(color);
        sketch.square(
            boardPos.x() + f * squareLen,
            boardPos.y() + r * squareLen,
            squareLen);
      }

    sketch.pop();
  }

  private void drawPieces(ChessGame game) {
    int nFiles = game.board().files();
    int nRanks = game.board().ranks();

    char[][] fenBoard = game.board().getFENBoard();

    sketch.push();
    sketch.imageMode(PConstants.CENTER);

    float squareLen = boardSize / Math.min(nFiles, nRanks);
    int offset = (int) squareLen / 2;

    for (int r = 0; r < nRanks; ++r)
      for (int f = 0; f < nFiles; ++f) {
        char p = fenBoard[r][f];

        PImage img = pieceMap.get(p);
        if (img == null)
          continue;

        sketch.image(
            img,
            boardPos.x() + f * squareLen + offset,
            boardPos.y() + r * squareLen + offset,
            squareLen,
            squareLen);
      }

    sketch.pop();
  }

  public static class ColorScheme {
    public int darkSquaresColor = 0xff9C7759;
    public int lightSquaresColor = 0xffCFBB9E;
  }
}
