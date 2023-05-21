package com.danilovfa.targethit.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.LeaderboardItemBinding
import com.danilovfa.targethit.domain.model.Score
import com.danilovfa.targethit.presentation.adapter.LeaderboardAdapter.ScoreViewHolder
import java.time.format.DateTimeFormatter

class LeaderboardAdapter(private val context: Context, private val leaderboard: List<Score>):
    RecyclerView.Adapter<ScoreViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(layoutInflater.inflate(R.layout.leaderboard_item, parent, false))
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = leaderboard.size

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: LeaderboardItemBinding by viewBinding()

        fun bind(id: Int) {
            binding.apply {
                scoreIndex.text = context.resources.getString(R.string.item_id, id + 1)
                textLeaderboardScore.text = leaderboard[id].score.toString()
                textLeaderboardDate.text = leaderboard[id].date
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            }
        }
    }
}