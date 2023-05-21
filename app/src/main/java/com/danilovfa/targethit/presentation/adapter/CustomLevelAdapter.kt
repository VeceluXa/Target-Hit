package com.danilovfa.targethit.presentation.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.danilovfa.targethit.R
import com.danilovfa.targethit.databinding.CustomLevelItemBinding
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.presentation.adapter.CustomLevelAdapter.CoordinateViewHolder

class CustomLevelAdapter(private val context: Context, val targets: List<Coordinate>):
    RecyclerView.Adapter<CoordinateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoordinateViewHolder {
        return CoordinateViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_level_item, parent, false))
    }

    override fun onBindViewHolder(holder: CoordinateViewHolder, position: Int) {
        holder.position = position

        if (position == targets.lastIndex) {
            holder.addListeners()
            holder.setEditable()
            holder.clearText()
        } else {
            holder.removeListeners()
            holder.setNotEditable()
            holder.setText()
        }
    }

    override fun getItemCount() = targets.size

    inner class CoordinateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: CustomLevelItemBinding by viewBinding()
        private var position = 0

        private var xChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                targets[position].x = s.toString().toIntOrNull() ?: -1
            }
        }
        private var yChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                targets[position].y = s.toString().toIntOrNull() ?: -1
            }
        }
        private var tChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                targets[position].t = s.toString().toIntOrNull() ?: -1
            }
        }

        fun setPosition(id: Int) {
            position = id
            binding.itemId.text = context.resources
                .getString(R.string.item_id, position + 1)
        }

        fun addListeners() {
            binding.apply {
                editTextX.addTextChangedListener(xChangedListener)
                editTextY.addTextChangedListener(yChangedListener)
                editTextT.addTextChangedListener(tChangedListener)
            }
        }

        fun removeListeners() {
            binding.apply {
                editTextX.removeTextChangedListener(xChangedListener)
                editTextY.removeTextChangedListener(yChangedListener)
                editTextT.removeTextChangedListener(tChangedListener)
            }
        }

        fun setText() {
            binding.apply {
                targets[position].apply {
                    editTextX.setText(x.toString())
                    editTextY.setText(y.toString())
                    editTextT.setText(t.toString())
                }
            }
        }

        fun clearText() {
            binding.apply {
                editTextX.setText("")
                editTextY.setText("")
                editTextT.setText("")
            }
        }

        fun setNotEditable() {
            binding.apply {
                editTextX.isEnabled = false
                editTextY.isEnabled = false
                editTextT.isEnabled = false
            }
        }

        fun setEditable() {
            binding.apply {
                editTextX.isEnabled = true
                editTextY.isEnabled = true
                editTextT.isEnabled = true
            }
        }
    }
}