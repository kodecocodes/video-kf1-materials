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

package com.raywenderlich.android.episodes.di

import android.app.Application
import com.raywenderlich.android.episodes.model.local.EpisodeDatabase
import com.raywenderlich.android.episodes.model.network.EpisodeRemoteDataSource
import com.raywenderlich.android.episodes.model.network.EpisodeService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {

  @Singleton
  @Provides
  fun provideEpisodeService(okhttpClient: OkHttpClient,
                         converterFactory: GsonConverterFactory
  ) = provideService(okhttpClient, converterFactory, EpisodeService::class.java)


  @Singleton
  @Provides
  fun provideEpisodeRemoteDataSource(episodeService: EpisodeService)
      = EpisodeRemoteDataSource(episodeService)

  @Singleton
  @Provides
  fun provideCoroutineDispatcher() = Dispatchers.Default

  @Singleton
  @Provides
  fun provideDb(app: Application) = EpisodeDatabase.getInstance(app)

  @Singleton
  @Provides
  fun provideEpisodeDao(db: EpisodeDatabase) = db.episodeDao()

  @CoroutineScopeIO
  @Provides
  fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

  private fun <T> provideService(okhttpClient: OkHttpClient,
                                 converterFactory: GsonConverterFactory, clazz: Class<T>): T {
    return createRetrofit(okhttpClient, converterFactory).create(clazz)
  }

  private fun createRetrofit(
    okhttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(EpisodeService.BASE_URL)
      .client(okhttpClient)
      .addConverterFactory(converterFactory)
      .build()
  }
}