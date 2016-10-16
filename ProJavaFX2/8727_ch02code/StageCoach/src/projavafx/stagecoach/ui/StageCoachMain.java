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
 * StageCoachMain.java - A JavaFX example program that demonstrates
 * how to use the Stage class in JavaFX, and displays many of the properties'
 * values as the Stage is manipulated by the user.  It also
 * demonstrates how to get arguments passed into the program.
 *
 *  Developed 2011 by James L. Weaver jim.weaver [at] javafxpert.com
 *  as a JavaFX SDK 2.0 example for the Pro JavaFX book.
 */
package projavafx.stagecoach.ui;

import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class StageCoachMain extends Application {
  StringProperty title = new SimpleStringProperty();
  
  Text textStageX;
  Text textStageY;
  Text textStageW;
  Text textStageH;
  Text textStageF;
  CheckBox checkBoxResizable;
  CheckBox checkBoxFullScreen;
  
  double dragAnchorX;
  double dragAnchorY;
  
  public static void main(String[] args) {
    Application.launch(args);
  }
  
  @Override
  public void start(Stage stage) {
    StageStyle stageStyle = StageStyle.DECORATED;
    List<String> unnamedParams = getParameters().getUnnamed();
    if (unnamedParams.size() > 0) {
      String stageStyleParam = unnamedParams.get(0);
      if (stageStyleParam.equalsIgnoreCase("transparent")) {
        stageStyle = StageStyle.TRANSPARENT;
      }
      else if (stageStyleParam.equalsIgnoreCase("undecorated")) {
        stageStyle = StageStyle.UNDECORATED;
      }
      else if (stageStyleParam.equalsIgnoreCase("utility")) {
        stageStyle = StageStyle.UTILITY;
      }
    }
    final Stage stageRef = stage;
    Group rootGroup;
    TextField titleTextField;
    Scene scene  = SceneBuilder.create()
      .width(270)
      .height(370)
      .fill(Color.TRANSPARENT)
      .root(
        rootGroup = GroupBuilder.create()
          .children(
            RectangleBuilder.create()
              .width(250)
              .height(350)
              .arcWidth(50)
              .arcHeight(50)
              .fill(Color.SKYBLUE)
              .build(),
            VBoxBuilder.create()
              .layoutX(30)
              .layoutY(20)
              .spacing(10)
              .children(
                textStageX = TextBuilder.create()
                  .textOrigin(VPos.TOP)
                  .build(),
                textStageY = TextBuilder.create()
                  .textOrigin(VPos.TOP)
                  .build(),
                textStageW = TextBuilder.create()
                  .textOrigin(VPos.TOP)
                  .build(),
                textStageH = TextBuilder.create()
                  .textOrigin(VPos.TOP)
                  .build(),
                textStageF = TextBuilder.create()
                  .textOrigin(VPos.TOP)
                  .build(),
                checkBoxResizable = CheckBoxBuilder.create()
                  .text("resizable")
                  .disable(stageStyle == StageStyle.TRANSPARENT ||
                           stageStyle == StageStyle.UNDECORATED)
                  .build(),
                checkBoxFullScreen = CheckBoxBuilder.create()
                  .text("fullScreen")
                  .build(),
                HBoxBuilder.create()
                  .spacing(10)
                  .children(
                    new Label("title:"),
                    titleTextField = TextFieldBuilder.create()
                      .text("Stage Coach")
                      .prefColumnCount(15)
                      .build()
                  )
                  .build(),
                ButtonBuilder.create()
                  .text("toBack()")
                  .onAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override public void handle(javafx.event.ActionEvent e) {
                      stageRef.toBack();
                    }
                  })
                  .build(),
                ButtonBuilder.create()
                  .text("toFront()")
                  .onAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override public void handle(javafx.event.ActionEvent e) {
                      stageRef.toFront();
                    }
                  })
                  .build(),
                ButtonBuilder.create()
                  .text("close()")
                  .onAction(new EventHandler<javafx.event.ActionEvent>() {
                    @Override public void handle(javafx.event.ActionEvent e) {
                      stageRef.close();
                    }
                  })
                  .build()
              )
              .build()
          )
          .build()
      )
      .build();

    //when mouse button is pressed, save the initial position of screen
    rootGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
            dragAnchorX = me.getScreenX() - stageRef.getX();
            dragAnchorY = me.getScreenY() - stageRef.getY();
        }
    });

    //when screen is dragged, translate it accordingly
    rootGroup.setOnMouseDragged(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
            stageRef.setX(me.getScreenX() - dragAnchorX);
            stageRef.setY(me.getScreenY() - dragAnchorY);
        }
    });
    
    textStageX.textProperty().bind(new SimpleStringProperty("x: ")
            .concat(stageRef.xProperty().asString()));
    textStageY.textProperty().bind(new SimpleStringProperty("y: ")
            .concat(stageRef.yProperty().asString()));
    textStageW.textProperty().bind(new SimpleStringProperty("width: ")
            .concat(stageRef.widthProperty().asString()));
    textStageH.textProperty().bind(new SimpleStringProperty("height: ")
            .concat(stageRef.heightProperty().asString()));
    textStageF.textProperty().bind(new SimpleStringProperty("focused: ")
            .concat(stageRef.focusedProperty().asString()));
    stage.setResizable(true);
    checkBoxResizable.selectedProperty()
            .bindBidirectional(stage.resizableProperty());
    checkBoxFullScreen.selectedProperty().addListener(new ChangeListener() {
      public void changed(ObservableValue ov, Object oldValue, Object newValue) {
        stageRef.setFullScreen(checkBoxFullScreen.selectedProperty().getValue());
      }
    });
    title.bind(titleTextField.textProperty());
    
    stage.setScene(scene);
    stage.titleProperty().bind(title);
    stage.initStyle(stageStyle);
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
            System.out.println("Stage is closing");
        }
    });
    stage.show();
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2); 
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);    
  }
}
