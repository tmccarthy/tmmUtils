package au.id.tmm.utilities.collection.syntax

import scala.collection.BuildFrom

final class IterableOps[C[_] <: Iterable[_], A] private[syntax] (
  iterable: C[A],
)(implicit
  buildFrom: BuildFrom[C[A], A, C[A]],
) {

  def atMostOneOr[E](error: => E): Either[E, Option[A]] = {
    if (iterable.isEmpty) return Right(None)

    val first2Elements = iterable.take(2)

    if (first2Elements.size == 1)
      Right(Some(first2Elements.head.asInstanceOf[A]))
    else
      Left(error)
  }

  def atMostOne: Either[Exception, Option[A]] =
    atMostOneOr(new Exception(s"Expected at most one element. $describeIterable"))

  def onlyElement: Option[A] = {
    val first2Elements = iterable.take(2)

    if (first2Elements.size == 1)
      Some(first2Elements.head.asInstanceOf[A])
    else
      None
  }

  def onlyElementOr[E](error: => E): Either[E, A] =
    onlyElement.toRight(error)

  def onlyElementOrException: Either[Exception, A] =
    onlyElementOr(new Exception(s"Expected exactly one element. $describeIterable"))

  private def describeIterable: String = s"Iterable was $iterable"

}
