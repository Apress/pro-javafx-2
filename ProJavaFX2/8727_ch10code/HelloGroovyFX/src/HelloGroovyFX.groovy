import groovyx.javafx.GroovyFX
import groovyx.javafx.SceneGraphBuilder

GroovyFX.start {
    new SceneGraphBuilder().stage(visible: true) {
        scene {
            stackPane {
                text("Hello GroovyFX")
            }
        }
    }
}
