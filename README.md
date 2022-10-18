# Kotlin Multiplatform HTTP-Client using Ktor

Simple demonstration of a [Ktor](https://ktor.io/)-based multiplatform HTTP-client in Kotlin

## Overview

- `base` submodule: common classes and interface (externalized in a library)
- `client` submodule: multiplatform HTTP-client implementation for jvm & js
- `server` submodule: Ktor-server providing an HTTP-API for testing the clients

## How to run

1. run the server with: `./gradlew run`
2. start the test-cases for jvm and js platform with: `./gradlew check`
3. all tests should be succeeded
4. play around with it