/*
 * Copyright (c) 2011, Pro JavaFX Authors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of JFXtras nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * ZenPongMain.java - A simple example of the intersects function and 
 * event handlers to create a very basic Pong game.
 *
 *
 *  Developed 2011 by James L. Weaver jim.weaver [at] javafxpert.com
 *  as a JavaFX SDK 2.0 example for the Pro JavaFX book.
 */
package projavafx.zenpong.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  Main class for the ZenPong example
 */
public class ZenPongMain extends Application {
  /**
   * The center points of the moving ball
   */
  DoubleProperty centerX = new SimpleDoubleProperty();
  DoubleProperty centerY = new SimpleDoubleProperty();

  /**
   * The Y coordinate of the left paddle
   */
  DoubleProperty leftPaddleY = new SimpleDoubleProperty();

  /**
   * The Y coordinate of the right paddle
   */
  DoubleProperty rightPaddleY = new SimpleDoubleProperty();

  /**
   * The drag anchor for left and right paddles
   */
  double leftPaddleDragAnchorY;
  double rightPaddleDragAnchorY;

  /**
   * The initial translateY property for the left and right paddles
   */
  double initLeftPaddleTranslateY;
  double initRightPaddleTranslateY;

  /**
   * The moving ball
   */
  Circle ball;

  /**
   * The Group containing all of the walls, paddles, and ball.  This also allows
   * us to requestFocus for KeyEvents on the Group
   */
  Group pongComponents;

  /**
   * The left and right paddles
   */
  Rectangle leftPaddle;
  Rectangle rightPaddle;

  /**
   * The walls
   */
  Rectangle topWall;
  Rectangle rightWall;
  Rectangle leftWall;
  Rectangle bottomWall;

  Button startButton;
  
  /**
   * Controls whether the startButton is visible
   */
  BooleanProperty startVisible = new SimpleBooleanProperty(true);

  /**
   * The animation of the ball
   */
  Timeline pongAnimation = TimelineBuilder.create()
    .keyFrames(
      new KeyFrame(
        new Duration(10.0),
        new EventHandler<ActionEvent>() {
          public void handle(javafx.event.ActionEvent t) {
            checkForCollision();
            int horzPixels = movingRight ? 1 : -1;
            int vertPixels = movingDown ? 1 : -1;
            centerX.setValue(centerX.getValue() + horzPixels);
            centerY.setValue(centerY.getValue() + vertPixels);
          }
        }
      )
    )
    .cycleCount(Timeline.INDEFINITE)
    .build();

  /**
   * Controls whether the ball is moving right
   */
  boolean movingRight = true;

  /**
   * Controls whether the ball is moving down
   */
  boolean movingDown = true;

  /**
   * Sets the initial starting positions of the ball and paddles
   */
  void initialize() {
    centerX.setValue(250);
    centerY.setValue(250);
    leftPaddleY.setValue(235);
    rightPaddleY.setValue(235);
    startVisible.set(true);
    pongComponents.requestFocus();
  }

  /**
   * Checks whether or not the ball has collided with either the paddles,
   * topWall, or bottomWall.  If the ball hits the wall behind the paddles,
   * the game is over.
   */
  void checkForCollision() {
    if (ball.intersects(rightWall.getBoundsInLocal()) ||
        ball.intersects(leftWall.getBoundsInLocal())) {
      pongAnimation.stop();
      initialize();
    }
    else if (ball.intersects(bottomWall.getBoundsInLocal()) ||
             ball.intersects(topWall.getBoundsInLocal())) {
      movingDown = !movingDown;
    }
    else if (ball.intersects(leftPaddle.getBoundsInParent()) && !movingRight) {
      movingRight = !movingRight;
    }
    else if (ball.intersects(rightPaddle.getBoundsInParent()) && movingRight) {
      movingRight = !movingRight;
    }
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(args);
  }
  
  @Override
  public void start(Stage stage) {
    Scene scene  = SceneBuilder.create()
      .width(500)
      .height(500)
      .fill(
        LinearGradientBuilder.create()
          .startX(0.0)
          .startY(0.0)
          .endX(0.0)
          .endY(1.0)
          .stops(
            new Stop(0.0, Color.BLACK),
            new Stop(0.0, Color.GRAY)
           )
          .build()
      )
      .root(
        pongComponents = GroupBuilder.create()
          .focusTraversable(true)
          .children(
            ball = CircleBuilder.create()
              .radius(5.0)
              .fill(Color.WHITE)
              .build(),
            topWall = RectangleBuilder.create()
              .x(0)
              .y(0)
              .width(500)
              .height(1)
              .build(),
            leftWall = RectangleBuilder.create()
              .x(0)
              .y(0)
              .width(1)
              .height(500)
              .build(),
            rightWall = RectangleBuilder.create()
              .x(500)
              .y(0)
              .width(1)
              .height(500)
              .build(),
            bottomWall = RectangleBuilder.create()
              .x(0)
              .y(500)
              .width(500)
              .height(1)
              .build(),
            leftPaddle = RectangleBuilder.create()
              .x(20)
              .width(10)
              .height(30)
              .fill(Color.LIGHTBLUE)
              .cursor(Cursor.HAND)
              .onMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                  initLeftPaddleTranslateY =  leftPaddle.getTranslateY();
                  leftPaddleDragAnchorY = me.getSceneY();
                }
              })
              .onMouseDragged(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                  double dragY = me.getSceneY() - leftPaddleDragAnchorY;
                  leftPaddleY.setValue(initLeftPaddleTranslateY + dragY);
                }
              })
              .build(),
            rightPaddle = RectangleBuilder.create()
              .x(470)
              .width(10)
              .height(30)
              .fill(Color.LIGHTBLUE)
              .cursor(Cursor.HAND)
              .onMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                  initRightPaddleTranslateY =  rightPaddle.getTranslateY();
                  rightPaddleDragAnchorY = me.getSceneY();
                }
              })
              .onMouseDragged(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                  double dragY = me.getSceneY() - rightPaddleDragAnchorY;
                  rightPaddleY.setValue(initRightPaddleTranslateY + dragY);
                }
              })
              .build(),
            startButton = ButtonBuilder.create()
              .layoutX(225)
              .layoutY(470)
              .text("Start!")
              .onAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override public void handle(javafx.event.ActionEvent e) {
                  startVisible.set(false);
                  pongAnimation.playFromStart();
                  pongComponents.requestFocus();
                }
              })
              .build()
          )
          .onKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent k) {
              if (k.getCode() == KeyCode.SPACE &&
                  pongAnimation.statusProperty()
                      .equals(Animation.Status.STOPPED)) {
                rightPaddleY.setValue(rightPaddleY.getValue() - 6);
              }
              else if (k.getCode() == KeyCode.L &&
                  !rightPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())) {
                rightPaddleY.setValue(rightPaddleY.getValue() - 6);
              }
              else if (k.getCode() == KeyCode.COMMA &&
                  !rightPaddle.getBoundsInParent().intersects(bottomWall.getBoundsInLocal())) {
                rightPaddleY.setValue(rightPaddleY.getValue() + 6);
              }
              else if (k.getCode() == KeyCode.A &&
                  !leftPaddle.getBoundsInParent().intersects(topWall.getBoundsInLocal())) {
                leftPaddleY.setValue(leftPaddleY.getValue() - 6);
              }
              else if (k.getCode() == KeyCode.Z &&
                  !leftPaddle.getBoundsInParent().intersects(bottomWall.getBoundsInLocal())) {
                leftPaddleY.setValue(leftPaddleY.getValue() + 6);
              }
            }
          })  
          .build()
      )
      .build();

    ball.centerXProperty().bind(centerX);
    ball.centerYProperty().bind(centerY);
    leftPaddle.translateYProperty().bind(leftPaddleY);
    rightPaddle.translateYProperty().bind(rightPaddleY);
    startButton.visibleProperty().bind(startVisible);
    
    stage.setScene(scene);
    initialize();
    stage.setTitle("ZenPong Example");
    stage.show();
  }
}
