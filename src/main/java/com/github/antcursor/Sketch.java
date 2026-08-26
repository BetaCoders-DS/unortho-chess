package com.github.antcursor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.pieces.Piece;
import com.github.antcursor.pieces.PieceType;
import com.github.antcursor.pieces.move.MoveRequest;
import com.github.antcursor.render.ProcessingRenderer;
import com.github.antcursor.render.ProcessingRenderer.ColorScheme;
import com.github.antcursor.render.RenderI;
import com.github.antcursor.types.Position;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Sketch
 */
class Sketch extends PApplet {
  private Config config;
  private RenderI renderer;
  private ChessGame game;
  private ColorScheme colorScheme;
  private Map<Character, PImage> pieceMap = new HashMap<>();
  private Position selected;

  Sketch(Config cfg) {
    config = cfg;
  }

  @Override
  public void settings() {
    size(config.width(), config.height());
    this.noSmooth();
  }

  @Override
  public void setup() {
    game = new ChessGame();
    colorScheme = new ColorScheme();

    loadSprites();
    renderer = new ProcessingRenderer(this, colorScheme, pieceMap);
  }

  private void loadSprites() {
    final char[] pieces = { 'P', 'N', 'B', 'R', 'Q', 'K', 'p', 'n', 'b', 'r', 'q', 'k' };

    for (char p : pieces) {
      StringBuilder fileName = new StringBuilder(Character.isUpperCase(p) ? "w" : "b");
      fileName.append(Character.toUpperCase(p)).append(".png");

      Path filepath = Paths.get("src/main/resources/pieces", fileName.toString());

      PImage sprite = this.loadImage(filepath.toString());

      pieceMap.put(p, sprite);
    }
  }

  @Override
  public void draw() {
    switch (game.getState()) {
      case CHECKMATE:
        this.exit();

      case DRAW:
      case BLACK_TURN:
      case WHITE_TURN:
        renderer.render(game, selected);
        break;

      case NONE:
    }
  }

  @Override
  public void mousePressed() {
    Position clicked = renderer.screenToBoard(mouseX, mouseY, game);

    if (clicked == null) {
      selected = null;
      return;
    }

    if (clicked.equals(selected)) {
      selected = null;
      return;
    }

    if (selected != null) {
      MoveRequest request = new MoveRequest(selected, clicked, Optional.of(PieceType.QUEEN));
      if (game.tryMove(request)) {
        selected = null;
        return;
      }
    }

    Piece piece = game.board().getPiece(clicked);
    selected = (piece != null && piece.color() == game.turn()) ? clicked : null;
  }
}
