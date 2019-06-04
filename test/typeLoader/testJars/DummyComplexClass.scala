package typeLoader.testJars

case class DummyComplexClass(name: String, age: Int, contacts: List[String]) {

  def catAge(multiplier: Int): Int = age * multiplier
  def reverseName: String = name.reverse
  def numberOfContacts: Int = contacts.length

}
