# `tmmUtils`
[![CircleCI](https://circleci.com/gh/tmccarthy/tmmUtils/tree/master.svg?style=shield)](https://circleci.com/gh/tmccarthy/tmmUtils/tree/master)
[![Maven Central](https://img.shields.io/maven-central/v/au.id.tmm.tmm-utils/tmm-utils-syntax_2.13.svg)](https://repo.maven.apache.org/maven2/au/id/tmm/tmm-utils/tmm-utils-syntax_2.13/)

A bunch of Scala utilities I've found myself writing that I haven't (yet) decided to spin out into 
their own projects.

```scala
val tmmUtilsVersion = "0.7.0"

libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-cats"               % tmmUtilsVersion        // Cats utilities
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-circe"              % tmmUtilsVersion        // Circe hashing
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-errors"             % tmmUtilsVersion        // Errors
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-syntax"             % tmmUtilsVersion        // Syntax utils
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-core"       % tmmUtilsVersion % Test // Test utilities
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-cats"       % tmmUtilsVersion % Test // Cats instances for tests
libraryDependencies += "au.id.tmm.tmm-utils" %% "tmm-utils-testing-scalacheck" % tmmUtilsVersion % Test // Scalacheck instances for tests
```

## Cats

Some utilities for interaction with the [`cats-core`](https://github.com/typelevel/cats) library

## Errors

Some type aliases and extension methods for dealing with types like `Either[Throwable, A]`.

## Circe

Some utilities relating to [`circe`](https://github.com/circe/circe)

## Testing

* `JreVersionDependentFlatSpec`, which provides utilities for ignoring tests based on the Java version
* `au.id.tmm.utilities.testing.syntax`, which provides syntax to replace the old `right.get` method on `Either`
* A set of ADTs that might be useful as test data (`Animal`, `Fruit`, `CoinToss`, `Planet` and `TrafficLight`)

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