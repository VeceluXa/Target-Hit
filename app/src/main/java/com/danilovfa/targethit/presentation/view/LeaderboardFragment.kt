package com.danilovfa.targethit.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.FragmentLeaderboardBinding
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.adapter.LeaderboardAdapter
import com.danilovfa.targethit.presentation.adapter.LevelsAdapter
import com.danilovfa.targethit.presentation.viewmodel.LeaderboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LeaderboardFragment : Fragment(), LeaderboardAdapter.OnItemClickListener {

    private val binding: FragmentLeaderboardBinding by viewBinding(CreateMethod.INFLATE)
    private val viewModel: LeaderboardViewModel by viewModels()
    private val args: LeaderboardFragmentArgs by navArgs()
    private val leaderboard = mutableListOf<Score>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setError(exception.message)
        }

        // TODO Move coroutine to viewModel
        if (leaderboard.size == 0) {
            lifecycleScope.launch(coroutineExceptionHandler) {
                setLoader()
                withContext(Dispatchers.IO) {
                    leaderboard += viewModel.getLeaderboard(args.level)
                }

                if (leaderboard.size == 0) {
                    setError(resources.getString(R.string.leaderboard_error_no_items))
                } else {
                    setLeaderboardVisibility()
                    setRecyclerView(leaderboard)
                }
            }
        } else {
            setLeaderboardVisibility()
            setRecyclerView(leaderboard)
        }
    }

    private fun setError(message: String?) {
        binding.apply {
            leaderboardLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE
            message?.let { errorTextView.text = it }
        }
    }

    private fun setLoader() {
        binding.apply {
            leaderboardLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun setLeaderboardVisibility() {
        binding.apply {
            leaderboardLayout.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    private fun setRecyclerView(leaderboard: List<Score>) {
        val myAdapter = LeaderboardAdapter(requireContext(), leaderboard)
        myAdapter.setOnItemClickListener(this)
        binding.leaderboardRecyclerView.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClick(id: Int) {
        val action = LeaderboardFragmentDirections.actionLeaderboardFragmentToReplayFragment(
            scoreDate = leaderboard[id].date.toString()
        )
        findNavController().navigate(action)
    }
}