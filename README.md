# ta4j2  ![Build Status develop](https://github.com/ta4j/ta4j/workflows/Test/badge.svg?branch=develop) ![Build Status master](https://github.com/ta4j/ta4j/workflows/Test/badge.svg?branch=master) [![Discord](https://img.shields.io/discord/745552125769023488.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/HX9MbWZ) [![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) ![Maven Central](https://img.shields.io/maven-central/v/org.ta4j/ta4j-parent?color=blue&label=Version) ![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/org.ta4j/ta4j-parent?label=Snapshot&server=https%3A%2F%2Foss.sonatype.org%2F)


***Technical Analysis For Java (2)***

![Ta4j main chart](https://raw.githubusercontent.com/ta4j/ta4j-wiki/master/img/ta4j_main_chart.png)

Ta4j2 is an open source Java library for [technical analysis](http://en.wikipedia.org/wiki/Technical_analysis). It provides the basic components for creation, evaluation and execution of trading strategies.

---

This is a simplification of the original [ta4j](https://github.com/ta4j/ta4j) repository

Changes to _**origin**_:
- Removed `Num`: This library is using the `Double` class for indicators, bars and criterions
  - `double` primitives are used as often as possible
- Removed `CombineIndicator`
- Removed `BarSeriesUtils` and `BarAggregator`
- Removed `TransformIndicator`

### Features

 * [x] 100% Pure Java - works on any Java Platform version 8 or later
 * [x] More than 130 technical indicators (Aroon, ATR, moving averages, parabolic SAR, RSI, etc.)
 * [x] A powerful engine for building custom trading strategies
 * [x] Utilities to run and compare strategies
 * [x] Minimal 3rd party dependencies
 * [x] Simple integration
 * [x] One more thing: it's MIT licensed
 

### Getting Help
The [wiki](https://ta4j.github.io/ta4j-wiki/) is the best place to start learning about ta4j. For more detailed questions, please use the [issues tracker](https://github.com/ta4j/ta4j/issues).
