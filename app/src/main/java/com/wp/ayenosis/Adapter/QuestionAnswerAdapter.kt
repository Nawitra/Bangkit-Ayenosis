package com.wp.ayenosis.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wp.ayenosis.R
import com.wp.ayenosis.model.QuestionAnswer

class QuestionAnswerAdapter (private val questionItem: MutableList<QuestionAnswer>) : RecyclerView.Adapter<QuestionAnswerAdapter.ItemHolder>() {
    class ItemHolder (v: View) : RecyclerView.ViewHolder(v) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tv_question)
        val tvAnswer: TextView = itemView.findViewById(R.id.tv_answer)
        val llQuestion: LinearLayout = itemView.findViewById(R.id.ll_question)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionAnswerAdapter.ItemHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.list_question_item,
            parent,
            false
        )
        return ItemHolder(v)
    }

    override fun onBindViewHolder(holder: QuestionAnswerAdapter.ItemHolder, position: Int) {
        var questionAnswer = questionItem[position]
        holder.tvQuestion.text = questionAnswer.question
        holder.tvAnswer.text = questionAnswer.answer
        holder.tvAnswer.visibility = if (questionAnswer.expendable == true) View.VISIBLE else View.GONE


        holder.llQuestion.setOnClickListener {
            questionAnswer.expendable = !questionAnswer.expendable!!
            notifyItemChanged(position)
        }

    }

    override fun getItemCount() = questionItem.size

}