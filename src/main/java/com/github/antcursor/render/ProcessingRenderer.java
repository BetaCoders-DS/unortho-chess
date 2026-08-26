package com.github.antcursor.render;

import java.util.Map;

import com.github.antcursor.board.Board;
import com.github.antcursor.game.ChessGame;
import com.github.antcursor.types.Color;
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

  @Override
  public Position screenToBoard(float screenX, float screenY, ChessGame game) {
    Board board = game.board();
    int nFiles = board.files();
    int nRanks = board.ranks();
    float len = squareLen(nFiles, nRanks);

    float relX = screenX - boardPos.x();
    float relY = screenY - boardPos.y();
    if (relX < 0 || relY < 0)
      return null;

    int screenCol = (int) (relX / len);
    int screenRow = (int) (relY / len);
    if (screenCol >= nFiles || screenRow >= nRanks)
      return null;

    return toBoardPosition(screenRow, screenCol, nFiles, nRanks, isFlipped(game));
  }

  private boolean isFlipped(ChessGame game) {
    return game.turn() == Color.BLACK;
  }

  private float squareLen(int nFiles, int nRanks) {
    return (float) boardSize / Math.min(nFiles, nRanks);
  }

  private Position toBoardPosition(int screenRow, int screenCol, int nFiles, int nRanks, boolean flip) {
    int x = flip ? nFiles - 1 - screenCol : screenCol;
    int y = flip ? nRanks - 1 - screenRow : screenRow;
    return new Position(x, y);
  }

  private void drawEmptyBoard(ChessGame game) {
    Board board = game.board();
    int nFiles = board.files();
    int nRanks = board.ranks();
    boolean flip = isFlipped(game);

    sketch.push();
    sketch.noStroke();

    float len = squareLen(nFiles, nRanks);

    for (int r = 0; r < nRanks; ++r)
      for (int f = 0; f < nFiles; ++f) {
        Position sq = toBoardPosition(r, f, nFiles, nRanks, flip);
        int color = (sq.x() + sq.y()) % 2 == 0 ? colorScheme.lightSquaresColor : colorScheme.darkSquaresColor;
        sketch.fill(color);
        sketch.square(
            boardPos.x() + f * len,
            boardPos.y() + r * len,
            len);
      }

    sketch.pop();
  }

  private void drawPieces(ChessGame game) {
    Board board = game.board();
    int nFiles = board.files();
    int nRanks = board.ranks();
    boolean flip = isFlipped(game);

    char[][] fenBoard = board.getFENBoard();

    sketch.push();
    sketch.imageMode(PConstants.CENTER);

    float len = squareLen(nFiles, nRanks);
    int offset = (int) len / 2;

    for (int r = 0; r < nRanks; ++r)
      for (int f = 0; f < nFiles; ++f) {
        Position sq = toBoardPosition(r, f, nFiles, nRanks, flip);
        char p = fenBoard[sq.y()][sq.x()];

        PImage img = pieceMap.get(p);
        if (img == null)
          continue;

        sketch.image(
            img,
            boardPos.x() + f * len + offset,
            boardPos.y() + r * len + offset,
            len,
            len);
      }

    sketch.pop();
  }

  public static class ColorScheme {
    public int darkSquaresColor = 0xff9C7759;
    public int lightSquaresColor = 0xffCFBB9E;
  }
}
