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

package com.raywenderlich.android.episodes.ui.episodes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.android.episodes.R
import com.raywenderlich.android.episodes.databinding.EpisodesFragmentBinding
import com.raywenderlich.android.episodes.di.Injectable
import com.raywenderlich.android.episodes.ui.injectViewModel
import javax.inject.Inject


class EpisodesFragment : Fragment(), Injectable {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: EpisodesViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    viewModel = injectViewModel(viewModelFactory)

    val binding = EpisodesFragmentBinding.inflate(inflater, container, false)
    context ?: return binding.root

    viewModel.spinner.observe(viewLifecycleOwner) { show ->
      binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
    }

    viewModel.snackbar.observe(viewLifecycleOwner) { text ->
      text?.let {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
        viewModel.onSnackbarShown()
      }
    }

    val adapter = EpisodeAdapter()
    binding.episodeList.adapter = adapter

    setHasOptionsMenu(true)
    return binding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.filter_prequels -> {
        filterData(1)
        true
      }
      R.id.filter_original -> {
        filterData(2)
        true
      }
      R.id.filter_sequels -> {
        filterData(3)
        true
      }
      R.id.filter_all -> {
        filterData(-1)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun filterData(num: Int) {
    viewModel.setTrilogyNumber(num)
  }

}
