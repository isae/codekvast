# README for Codekvast

## Directory structure

The product itself lives under `product/`.

Sample projects to use when testing Codekvast lives under `sample/`.

Development tools live under `tools/`.

## Development environment

### JDK

Java 8 is required. OpenJDK is recommended.

### Build tool

Codekvast uses **Gradle** as build tool. It uses the Gradle Wrapper, `gradlew`, which is checked in at the root of the workspace.
There is the convenience script `tools/src/script/gradle` which simplifies invocation of gradlew. Install that script in your PATH
and use that script instead of `path/to/gradlew`

### Software publishing
The agent part of Codekvast is published to Bintray. To be able to upload to Bintray you need the following lines in your `~/.gradle/gradle
.properties`:

    bintrayUser=my-bintray-user
    bintrayKey=my-bintray-key

You also need to be member of the Crisp organisation in Bintray.

### IDE

**Intellij Ultimate Edition 14+** is the recommended IDE with the following plugins:

1. **Lombok Support** (required)
1. **AspectJ Support** (required)
1. AngularJS (optional)
1. Karma (optional)

_TODO: fill out this section together with Per_

## How to build the product
    cd <root>/product
    ../gradlew build

Or if using the convenience gradle script:

    cd <root>/product
    gradle build

The rest of this README assumes you use the convenience script.

## How to test with Tomcat+Jenkins

### Start Jenkins in terminal 1

    gradle :sample:jenkins:run

This will download Tomcat 7 and then download and deploy Jenkins into Tomcat. Finally, Tomcat is started with Codekvast Collector attached.
Terminate with `Ctrl-C`.

You can access Jenkins at [http://localhost:8080/jenkins](http://localhost:8080/jenkins)

### Start codekvast-agent in terminal 2

    gradle :product:agent:codekvast-agent:run

This will launch **codekvast-agent**, that will process output from all collectors. The agent will try to upload the
data to **http://localhost:8090**, which is the default URL for the **codekvast-server**.
Terminate with `Ctrl-C`.

### Start codekvast-server in terminal 3

    gradle :product:server:codekvast-server:run

This will start **codekvast-server** on [http://localhost:8090](http://localhost:8090).
Terminate with `Ctrl-C`.

### Open a browser at [http://localhost:8090](http://localhost:8090)

Log in with `user` / `0000`

## User Manual

A User Manual located in product/docs/src/asciidoc.

The source is in AsciiDoctor format.

It is built by doing `gradle product:docs:build`.

The result is a self-contained HTML5 file located at [file:product/docs/build/asciidoc/html5/CodekvastUserManual.html]()
