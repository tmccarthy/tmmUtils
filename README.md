# `tmmUtils`
[![CircleCI](https://circleci.com/gh/tmccarthy/tmmUtils/tree/master.svg?style=svg)](https://circleci.com/gh/tmccarthy/tmmUtils/tree/master)
[![Maven Central](https://img.shields.io/maven-central/v/au.id.tmm.tmm-utils/tmm-utils-codec_2.13.svg)](https://repo.maven.apache.org/maven2/au/id/tmm/tmm-utils/tmm-utils-codec_2.13/)

A bunch of Scala utilities I've found myself writing that I haven't (yet) decided to spin out into 
their own projects.

```scala
val tmmUtilsVersion = "0.3.1"

libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-codec"      % tmmUtilsVersion          // Codecs and hashing
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-collection" % tmmUtilsVersion          // Collections
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing"    % tmmUtilsVersion % "test" // Test utilities
```

<br/>

## Codec

`tmm-utils-codec` is a set of extension methods and string interpolaters over the top of the Apache
[`commons-codec`](https://commons.apache.org/proper/commons-codec/) library.

#### Binary codecs

```scala
import au.id.tmm.utilities.codec.binarycodecs._

import scala.collection.immutable.ArraySeq

// String interpolators
val bin1: ArraySeq[Byte]     = binary"11011110101000011110000110101101"
val hex1: ArraySeq[Byte]     = hex"ade1a1de"
val base32_1: ArraySeq[Byte] = base32"VXQ2DXQ="
val base64_1: ArraySeq[Byte] = base64"reGh3g=="

// Extension methods for parsing
val bin2: ArraySeq[Byte]     = "11011110101000011110000110101101".parseBinaryUnsafe
val hex2: ArraySeq[Byte]     = "ade1a1de".parseHexUnsafe
val base32_2: ArraySeq[Byte] = "VXQ2DXQ=".parseHexUnsafe
val base64_2: ArraySeq[Byte] = "reGh3g==".parseBase64Unsafe
```

#### Message digests

```scala
import au.id.tmm.utilities.codec.binarycodecs._
import au.id.tmm.utilities.codec.digest._

import scala.collection.immutable.ArraySeq

val bytes: ArraySeq[Byte] = hex"ade1a1de"

val md5 = bytes.md5
val sha256 = bytes.sha256
val sha512 = bytes.sha512

// SHA 3 only works with Java 9 or later
val sha3_256 = bytes.sha3_256
val sha3_512 = bytes.sha3_512
```

<br/>

## Collections

A couple of utilities and collections:

* `DupelessSeq`, which is an ordered sequence with a constant-time contains check, but (unlike [`ListSet`](https://www.scala-lang.org/api/current/scala/collection/immutable/ListSet.html))
  considers order for equality checking.
* `Flyweight`, which is a very simple in-memory cache
* `IteratorUtils`, which adds a couple of extension methods to `Iterator`

<br/>

## Errors

Some type aliases and extension methods for dealing with types like `Either[Throwable, A]`.

## Value classes

Some utilities for deriving typeclass (`Numeric` and `Ordering`) instances for value classes.

## Testing

* `JreVersionDependentFlatSpec`, which provides utilities for ignoring tests based on the Java version
* `NeedsCleanDirectory`, which provides an empty directory for every test
* `au.id.tmm.utilities.testing.syntax`, which provides syntax to replace the old `right.get` method on `Either`

## Syntax

Some general syntax extensions.

#### Tuple syntax

```scala

import au.id.tmm.utilities.syntax.tuples._

val tuple: String -> Int = "hello" -> 5

val tuples: List[String -> Int] = List(
  "hello" -> 1,
  "world" -> 2,
)

```