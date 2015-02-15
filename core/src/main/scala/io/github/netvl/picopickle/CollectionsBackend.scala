package io.github.netvl.picopickle

import shapeless.syntax.typeable._

object CollectionsBackend extends Backend {
  override type BValue = Any
  override type BObject = Map[String, Any]
  override type BArray = Vector[Any]
  override type BString = String
  override type BNumber = Number
  override type BBoolean = Boolean
  override type BNull = Null

  override def fromObject(obj: BObject): Map[String, BValue] = obj
  override def makeObject(m: Map[String, BValue]): BObject = m
  override def getObject(value: BValue): Option[BObject] = value.cast[Map[String, Any]]

  override def getObjectKey(obj: BObject, key: String): Option[BValue] = obj.get(key)
  override def setObjectKey(obj: BObject, key: String, value: BValue): BObject = obj + (key -> value)
  override def removeObjectKey(obj: BObject, key: String): BObject = obj - key

  override def fromArray(arr: BArray): Vector[BValue] = arr
  override def makeArray(v: Vector[BValue]): BArray = v
  override def getArray(value: BValue): Option[BArray] = value.cast[Vector[Any]]

  override def fromString(str: BString): String = str
  override def makeString(s: String): BString = s
  override def getString(value: BValue): Option[BString] = value.cast[String]

  override def fromNumber(num: BNumber): Number = num
  override def makeNumber(n: Number): BNumber = n
  override def getNumber(value: BValue): Option[BNumber] = value.cast[Number]

  override def makeNumberAccurately(n: Number): BValue = n
  override def fromNumberAccurately(value: BValue): Number = value.cast[Number].get

  override def fromBoolean(bool: BBoolean): Boolean = bool
  override def makeBoolean(b: Boolean): BBoolean = b
  override def getBoolean(value: BValue): Option[BBoolean] = value.cast[Boolean]

  override def makeNull: BNull = null
  override def getNull(value: BValue): Option[BNull] = if (value == null) Some(null) else None

  def anyToValue(any: Any): BValue = any
  def valueToAny(value: BValue): Any = value
}

trait CollectionsBackendComponent extends BackendComponent {
  override val backend = CollectionsBackend
}

trait CollectionsPickler extends DefaultPickler with CollectionsBackendComponent {
  override implicit val charWriter: Writer[Char] = Writer[Char] {
    case c: Char => c
  }
  override implicit val charReader: Reader[Char] = Reader[Char] {
    case c: Char => c
  }
}
object CollectionsPickler extends CollectionsPickler
