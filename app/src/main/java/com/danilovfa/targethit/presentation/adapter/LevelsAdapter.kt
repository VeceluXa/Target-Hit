package com.danilovfa.targethit.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.LevelsItemBinding
import com.danilovfa.targethit.domain.model.Level

class LevelsAdapter(private val context: Context, private val levels: List<Level>):
    RecyclerView.Adapter<LevelsAdapter.LevelViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)
    private var onItemclickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        return LevelViewHolder(layoutInflater.inflate(R.layout.levels_item, parent, false))
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return levels.size
    }

    inner class LevelViewHolder(item: View): ViewHolder(item) {
        private val binding: LevelsItemBinding by viewBinding()

        fun bind(id: Int) {
            val buttonText = "${context.resources.getString(R.string.levels_item_text)} ${id + 1}"
            binding.button.apply {
                text = buttonText
                setOnClickListener {
                    onItemclickListener?.onItemClick(id)
                }
            }

            if (levels[id].isCompleted) {
                binding.button.backgroundTintList = context.getColorStateList(androidx.appcompat.R.color.material_blue_grey_800)
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemclickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }
}