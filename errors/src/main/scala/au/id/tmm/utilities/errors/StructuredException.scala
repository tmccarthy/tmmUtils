package au.id.tmm.utilities.errors

import au.id.tmm.utilities.errors.StructuredException.indent

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
            case (key, value) => s"$key=${indent(value, 0)}"
          }
          .mkString(
            sep = "\n",
          )

      name + indent("\n" + fieldsRendered, 3)
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
        if (i.isEmpty)
          i.toString()
        else
          i.mkString(s"${iterableClassName(i)}(\n\t", ",\n\t", ",\n)")
      }
      case a: Any => a.toString
    }

  private val emptyIterableStringPattern = """(\w+)\(\)""".r

  private def iterableClassName(iterable: Iterable[_]): String =
    iterable.iterableFactory.empty.toString match {
      case emptyIterableStringPattern(className) => className
      case _                                     => iterable.getClass.getName
    }

  private def indent(string: String, numIndents: Int): String = {
    val indent = "\t" * numIndents
    string.replace("\n", s"\n$indent")
  }

}
