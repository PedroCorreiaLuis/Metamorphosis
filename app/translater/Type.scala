package main.scala.translater

sealed trait Type

object SimpleType extends Type
object ComposedType extends Type
object UDCType extends Type
object InvalidType extends Type
