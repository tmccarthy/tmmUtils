package au.id.tmm.utilities.collection

package object syntax {

  implicit def iteratorOps[A](iterator: Iterator[A]): IteratorOps[A] = new IteratorOps[A](iterator)

}
