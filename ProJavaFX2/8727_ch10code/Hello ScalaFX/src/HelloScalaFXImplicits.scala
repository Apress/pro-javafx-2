import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.stage.Stage
import scalafx.scene.Scene
import javafx.scene.control.Label

object HelloScalaFXImplicits extends JFXApp {
  stage = new Stage {
    scene = new Scene {
      content = new Label("Hello ScalaFX Implicits")
    }
  }
}
