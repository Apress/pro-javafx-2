import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.control.Label
import javafx.geometry.Insets

object HelloScalaFXMargin extends JFXApp {
  stage = new Stage {
    scene = new Scene {
      content = new Label {
        text = "Hello ScalaFX Margin"
        margin = new Insets(20)
      }
    }
  }
}
