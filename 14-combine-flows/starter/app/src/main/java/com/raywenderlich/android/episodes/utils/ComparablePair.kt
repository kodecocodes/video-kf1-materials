/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raywenderlich.android.episodes.utils

data class ComparablePair<A: Comparable<A>, B: Comparable<B>>(
  val first: A,
  val second: B
) : Comparable<ComparablePair<A, B>> {
  override fun compareTo(other: ComparablePair<A, B>): Int {
    val firstComp = this.first.compareTo(other.first)
    if (firstComp != 0) { return firstComp }
    return this.second.compareTo(other.second)
  }
}