package com.github.antcursor.render;

import com.github.antcursor.game.ChessGame;
import com.github.antcursor.types.Position;

/**
 * RenderI
 */
public interface RenderI {
  public void render(ChessGame game);

  public Position screenToBoard(float screenX, float screenY, ChessGame game);
}
