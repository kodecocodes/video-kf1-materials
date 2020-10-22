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

package com.raywenderlich.android.episodes.model

import androidx.annotation.AnyThread
import com.raywenderlich.android.episodes.model.local.EpisodeDao
import com.raywenderlich.android.episodes.model.network.EpisodeRemoteDataSource
import com.raywenderlich.android.episodes.utils.ComparablePair
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepository @Inject constructor(
  private val episodeDao: EpisodeDao,
  private val episodeRDS: EpisodeRemoteDataSource,
  private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

  private fun shouldUpdateEpisodesCache(): Boolean {
    return true
  }

  suspend fun tryUpdateRecentEpisodesCache() {
    if (shouldUpdateEpisodesCache()) fetchRecentEpisodes()
  }

  suspend fun tryUpdateRecentEpisodesForTrilogyCache(trilogy: Trilogy) {
    if (shouldUpdateEpisodesCache()) fetchRecentEpisodesForTrilogy(trilogy)
  }

  private suspend fun fetchRecentEpisodes() {
    val episodes = episodeRDS.fetchAllEpisodes()
    episodeDao.saveAll(episodes)
  }

  private suspend fun fetchRecentEpisodesForTrilogy(trilogy: Trilogy) {
    val episodesForTrilogy = episodeRDS.episodesForTrilogy(trilogy)
    episodeDao.saveAll(episodesForTrilogy)
  }

  private fun List<Episode>.applySort(favoritesSortOrder: List<String>): List<Episode> {
    return sortedBy { episode ->
      val positionForItem = favoritesSortOrder.indexOf(episode.episodeId).let { order ->
        if (order > -1) order else Int.MAX_VALUE
      }
      ComparablePair(positionForItem, episode.number)
    }
  }

  @AnyThread
  suspend fun List<Episode>.applyMainSafeSort(favoritesSortOrder: List<String>) =
    withContext(defaultDispatcher) {
      this@applyMainSafeSort.applySort(favoritesSortOrder)
    }
}