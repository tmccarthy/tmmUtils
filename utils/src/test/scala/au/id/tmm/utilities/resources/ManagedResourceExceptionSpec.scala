package au.id.tmm.utilities.resources

import au.id.tmm.utilities.testing.ImprovedFlatSpec

class ManagedResourceExceptionSpec extends ImprovedFlatSpec {

  "a ManagedResourceException" should "not equal an object of a different class" in {
    assert(new ManagedResourceException(Vector.empty) !== new Object)
  }

  it should "include the exceptions in its toString" in {
    assert(new ManagedResourceException(Vector(new RuntimeException("Hello world"))).toString ===
      "ManagedResourceException(Vector(java.lang.RuntimeException: Hello world))")
  }

  it should "make the head of the exceptions vector its cause" in {
    val exception = new RuntimeException("Hello world")

    assert(new ManagedResourceException(Vector(exception)).getCause === exception)
  }

}
