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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

  exampleOf("Sequence (blocks main thread)")

  fun prequels(): Sequence<String> = sequence {
    for (movie in listOf(episodeI, episodeII, episodeIII)) {
      Thread.sleep(DELAY) // a long-computation
      yield(movie)
    }
  }
  prequels().forEach { movie -> log(movie) }

  log("Something else to be done here.")

  exampleOf("Suspending function (asynchronous)")

  suspend fun originals(): List<String> {
    delay(DELAY) // a long-computation
    return listOf(episodeIV, episodeV, episodeVI)
  }
  launch {
    originals().forEach { movie -> log(movie) }
  }

  log("Something else to be done here.")

  delay(2 * DELAY)
  exampleOf("Simple flow")

  fun sequels(): Flow<String> = flow {
    for (movie in listOf(episodeVII, episodeVIII, episodeIX)) {
      delay(DELAY) // a long-computation
      emit(movie)
    }
  }

  launch {
    sequels().collect { movie -> log(movie) }
  }

  launch {
    for (i in 1..3) {
      log("Not blocked $i")
      delay(DELAY)
    }
  }

  log("Something else to be done here.")

  delay(4 * DELAY)
  exampleOf("Cold stream")

  val sequelFilms = sequels()

  sequelFilms.collect { movie -> log(movie) }

  delay(3 * DELAY)
  exampleOf("Collecting again")

  sequelFilms.collect {  movie -> log(movie) }
}