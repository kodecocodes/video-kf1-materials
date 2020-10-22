/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

  exampleOf("zip")

  var characters = characterNames.asFlow()
  var weapons = weaponNames.asFlow()

  characters.zip(weapons) { character, weapon -> "$character: $weapon" }
    .collect { log(it) }

  exampleOf("onEach and zip with delays")

  characters = characterNames.asFlow().onEach { delay(DELAY / 2) }
  weapons = weaponNames.asFlow().onEach { delay(DELAY) }
  var start = System.currentTimeMillis()
  characters.zip(weapons) { character, weapon -> "$character: $weapon" }
    .collect { characterToWeapon ->
      log("$characterToWeapon at ${System.currentTimeMillis() - start} ms")
    }

  exampleOf("combine")

  characters = characterNames.asFlow().onEach { delay(DELAY / 2) }
  weapons = weaponNames.asFlow().onEach { delay(DELAY) }
  start = System.currentTimeMillis()
  characters.combine(weapons) { character, weapon -> "$character: $weapon" }
    .collect { characterToWeapon ->
      log("$characterToWeapon at ${System.currentTimeMillis() - start} ms")
    }

  exampleOf("flatMapConcat")

  fun suitUp(string: String): Flow<String> = flow {
    emit("$string gets dressed for battle")
    delay(DELAY)
    emit("$string dons their helmet")
  }

  characterNames.asFlow().map { suitUp(it) }
    .collect { println(it) }

  start = System.currentTimeMillis()

  characterNames.asFlow().onEach { delay(DELAY / 2) }
    .flatMapConcat { suitUp(it) }
    .collect { value ->
      log("$value at ${System.currentTimeMillis() - start} ms")
    }

  exampleOf("flatMapMerge")

  characterNames.asFlow().onEach { delay(DELAY / 2) }
    .flatMapMerge { suitUp(it) }
    .collect { value ->
      log("$value at ${System.currentTimeMillis() - start} ms")
    }

  exampleOf("flatMapLatest")

  characterNames.asFlow().onEach { delay(DELAY / 2) }
    .flatMapLatest { suitUp(it) }
    .collect { value ->
      log("$value at ${System.currentTimeMillis() - start} ms")
    }
}


