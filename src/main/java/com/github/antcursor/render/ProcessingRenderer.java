package com.github.antcursor.render;

import java.util.Map;

import com.github.antcursor.board.Board;
import com.github.antcursor.game.ChessGame;
import com.github.antcursor.types.Color;
import com.github.antcursor.types.Position;
import com.github.antcursor.pieces.move.MoveType;
import com.github.antcursor.pieces.move.MoveCandidate;

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
  public void render(ChessGame game, Position selected) {
    drawEmptyBoard(game);
    drawPieces(game);
    drawMoveIndicators(game, selected);
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

  private void drawMoveIndicators(ChessGame game, Position selected) {
    if (selected == null)
      return;

    Board board = game.board();
    int nFiles = board.files();
    int nRanks = board.ranks();
    boolean flip = isFlipped(game);
    float len = squareLen(nFiles, nRanks);

    sketch.push();
    sketch.noStroke();

    for (MoveCandidate cand : game.getPossibleMoves(selected)) {
      Position to = cand.to();
      Position sq = fromBoardPosition(to, nFiles, nRanks, flip);

      float cx = boardPos.x() + sq.x() * len + len / 2f;
      float cy = boardPos.y() + sq.y() * len + len / 2f;

      if (isCapture(cand.type())) {
        sketch.fill(colorScheme.captureMoveColor);
        drawTriangle(cx, cy, len);
      } else {
        sketch.fill(colorScheme.normalMoveColor);
        sketch.ellipse(cx, cy, len * 0.35f, len * 0.35f);
      }
    }

    sketch.pop();
  }

  private boolean isCapture(MoveType type) {
    return switch (type) {
      case MoveType.Capture c -> true;
      case MoveType.EnPassant ep -> true;
      case MoveType.Promotion p -> p.captured().isPresent();
      default -> false;
    };
  }

  private void drawTriangle(float cx, float cy, float len) {
    float r = len * 0.3f;
    sketch.triangle(
        cx, cy - r,
        cx - r * 0.87f, cy + r * 0.5f,
        cx + r * 0.87f, cy + r * 0.5f);
  }

  private Position fromBoardPosition(Position boardPos_, int nFiles, int nRanks, boolean flip) {
    int col = flip ? nFiles - 1 - boardPos_.x() : boardPos_.x();
    int row = flip ? nRanks - 1 - boardPos_.y() : boardPos_.y();
    return new Position(col, row);
  }

  public static class ColorScheme {
    public int darkSquaresColor = 0xff9C7759;
    public int lightSquaresColor = 0xffCFBB9E;
    public int normalMoveColor = 0x8055AA55;
    public int captureMoveColor = 0x80CC4444;
  }
}
