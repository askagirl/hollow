![Hollow Logo](logo.png)

# Hollow

[![Build Status](https://travis-ci.org/Netflix/hollow.svg?branch=master)](https://travis-ci.org/Netflix/hollow)
[![Join the chat at https://gitter.im/Netflix/hollow](https://badges.gitter.im/Netflix/hollow.svg)](https://gitter.im/Netflix/hollow?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Hollow is a java library and comprehensive toolset for harnessing small to moderately sized in-memory datasets which are disseminated from a single producer to many consumers for read-only access.  [Read more](http://techblog.netflix.com/2016/12/netflixoss-announcing-hollow.html).

Documentation is available at [http://hollow.how](http://hollow.how).  

## Getting Started

We recommend jumping into the [quick start guide](http://hollow.how/quick-start) — you'll have a demo up and running in minutes, and a fully production-scalable implementation of Hollow at your fingertips in about an hour.  From there, you can plug in your data model and it's off to the races.

## Get Hollow

Binaries are available from Maven Central and jcenter.

|GroupID/Org|ArtifactID/Name|Latest Stable Version|
|-----------|---------------|---------------------|
|com.netflix.hollow|hollow|2.2.1|

In a Maven .pom file:

        ...
        <dependency>
                <groupId>com.netflix.hollow</groupId>
                <artifactId>hollow</artifactId>
                <version>2.2.1</version>
        </dependency>
        ...

## Get Support

Hollow is maintained by the Platform Data Technologies team at Netflix.  Support can be obtained directly from us or from fellow users through [Gitter](https://gitter.im/Netflix/hollow) or by posting a question tagged with _hollow_ on Stack Overflow.

## LICENSE

Copyright (c) 2016 Netflix, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
