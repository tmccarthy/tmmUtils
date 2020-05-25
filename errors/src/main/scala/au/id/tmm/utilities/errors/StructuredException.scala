package au.id.tmm.utilities.errors

import scala.collection.immutable.ArraySeq

final case class StructuredException(
  name: String,
  fields: ArraySeq[(String, String)],
  cause: Option[Throwable],
) extends ProductException.WithCause(cause) {

  def withCause(cause: Throwable): StructuredException = this.copy(cause = Some(cause))

  override def getMessage: String =
    if (fields.isEmpty) {
      name
    } else {
      val fieldsRendered: String =
        fields
          .map {
            case (key, value) => s"$key=$value"
          }
          .mkString(
            start = "\t\t\t",
            sep = "\n\t\t\t",
            end = "",
          )

      name + "\n" + fieldsRendered
    }

}

object StructuredException {

  def apply(name: String, fields: (String, Any)*): StructuredException =
    StructuredException(
      name,
      fields
        .map {
          case (key, value) => key -> renderValue(value)
        }
        .to(ArraySeq.untagged),
      cause = None,
    )

  private def renderValue(value: Any): String =
    value match {
      case s: CharSequence => s.toString
      case i: Iterable[_] => {
        println(value)
        i.mkString("XXX(\n\t\t\t\t", ",\n\t\t\t\t", ",\n\t\t\t")
      }
      case a: Any => a.toString
    }

  private def indent(string: String): String = ???

}
