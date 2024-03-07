package com.utad.pmdmu5.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.pmdmu5.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityHomeBinding
    private val binding: ActivityHomeBinding get() = _binding
    private val adapter = Adapter()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGames.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvGames.adapter = adapter
        observeViewModelResponse()
        val storeId = 1
        viewModel.getAllGames(storeId)



    }

    private fun observeViewModelResponse() {
        viewModel.gamesResponse.observe(this) { uiState ->
            if (uiState != null) {
                adapter.submitList(uiState.response)
            }
            if (uiState.isError) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
            if (uiState.isLoading) {
                binding.pbList.visibility = View.VISIBLE
            } else {
                binding.pbList.visibility = View.GONE
            }
        }
    }
}