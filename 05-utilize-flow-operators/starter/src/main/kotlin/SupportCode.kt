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

fun exampleOf(description: String, action: () -> Unit = {}) {
  println("\n--- Example of: $description ---")
  action()
}

fun log(message: String) = println("[${Thread.currentThread().name}] $message")

const val DELAY = 500L

const val episodeI = "The Phantom Menace"
const val episodeII = "Attack of the Clones"
const val theCloneWars = "The Clone Wars"
const val episodeIII = "Revenge of the Sith"
const val solo = "Solo: A Star Wars Story"
const val rogueOne = "Rogue One: A Star Wars Story"
const val episodeIV = "A New Hope"
const val episodeV = "The Empire Strikes Back"
const val episodeVI = "Return of the Jedi"
const val episodeVII = "The Force Awakens"
const val episodeVIII = "The Last Jedi"
const val episodeIX = "The Rise of Skywalker"

const val chewieSound = "Arwerwrw"
const val r2d2Sound = "Beep-bee-bee-boop-bee-doo-weep"
const val blasterSound = "Pew pew, pew pew"
const val saberSound = "Schvrmmmmmmm, schvrmmmmmmm!"

val characterNames = listOf("Luke Skywalker", "Han Solo", "Chewbacca")
val weaponNames = listOf("Light saber", "Heavy Blaster Pistol", "Bowcaster")

val midichlorianCounts = mapOf("Baby Yoda" to 15000, "Qui-Gon Jinn" to 10000, "Anakin Skywalker" to 20000)
const val CHOSEN_ONE_THRESHOLD = 19000

sealed class ForceUser {
  abstract var name: String
}
data class Padawan(override var name: String): ForceUser()
data class Jedi(override var name: String): ForceUser()
data class Sith(override var name: String): ForceUser()
data class Ren(override var name: String): ForceUser()

val forceUsers: List<ForceUser> = listOf(
  Padawan("Ahsoka Tano"),
  Padawan("Anakin Skywalker"),
  Jedi("Luke Skywalker"),
  Sith("Sidious"),
  Sith("Vader"),
  Padawan("Ezra Bridger"),
  Jedi("Obi-Wan Kenobi"),
  Jedi("Yoda"),
  Ren("Kylo"),
  Sith("Maul")
)
