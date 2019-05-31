package typeLoader.testJars

case class DummyComplexClass(name: String, age: Int, contacts: List[String]) {

  def catAge: Int = age * 7
  def reverseName: String = name.reverse
  def numberOfContacts: Int = contacts.length

}
