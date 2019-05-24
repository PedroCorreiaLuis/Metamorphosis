package main.scala.translater

sealed trait Type

object SimpleType extends Type
object ComposedType extends Type
object UDCType extends Type
object InvalidType extends Type
/*
case class String() extends Type
case class List(t: Type)
case class Map(k: Type,v: Type)
*/