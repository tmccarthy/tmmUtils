[![Build Status](https://travis-ci.org/tmccarthy/tmmUtils.svg?branch=master)](https://travis-ci.org/tmccarthy/tmmUtils)
[![codecov](https://codecov.io/gh/tmccarthy/tmmUtils/branch/master/graph/badge.svg)](https://codecov.io/gh/tmccarthy/tmmUtils)

# My Scala Utilities

Just some utilities I've found myself writing more than once for different projects.

## Usage

At some point I'll probably add this to the central repository, but for the moment I'm just hosting
it on my own server. To use:

```
resolvers in ThisBuild +=
  "Ambitious Tools Artifactory" at "http://artifactory.ambitious.tools/artifactory/sbt-libs-release-local/"

// Production utilities
libraryDependencies += "au.id.tmm" %% "tmmutils" % "0.1"

// Test utilities
libraryDependencies += "au.id.tmm" %% "tmmtestutils" % "0.1" % "test"

```