# spark-kotlin-elastic-demo
Skeleton Spark Framework microservice using Elasticsearch's Java Transport API, written in Kotlin

## Get the repo

`git clone https://github.com/scottmmjackson/spark-kotlin-elastic-demo`

## Run the service in dev mode

`gradle run`

## Build the tarball

`gradle build`

creates `./build/distributions/App-1.0-SNAPSHOT.tar`

which contains `/bin` and `/lib` directories

from `App-1.0-SNAPSHOT` run  `sh bin/App` or `bin/App.bat`

## What is this?

- Spark is a micro-web-framework for the JVM
- Kotlin is a functionalized JVM language that reads similar to Swift or Scala
- ElasticSearch is a search-oriented document store
- The Transport API is ElasticSearch's binary TCP communication protocol- the low-overhead alternative to its REST-based protocol.

## What does this do?

This is a proof-of-concept and demonstration of building a single-field search query API that avoids unnecessary HTTP roundtripping.

## What is outside of the scope of this repo? (Read: what might I add / solicit PRs for?)

Concurrency. You could probably break this pretty quickly by adding a second route and slamming the server until it tripped over itself.

Websockets. Spark supports websockets, and there is obvious practicality to using this.
