package com.github.antcursor;

import processing.core.PApplet;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    Config app_config = new Config(
        800, 600);

    Sketch mainSketch = new Sketch(app_config);

    PApplet.runSketch(
        new String[] { Sketch.class.getName() },
        mainSketch);
  }
}
