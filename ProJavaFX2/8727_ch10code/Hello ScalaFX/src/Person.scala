import scalafx.beans.property.StringProperty

class Person {
  lazy val name = new StringProperty(this, "name")
  def name_=(v: String) {
    name() = v
  }
}
