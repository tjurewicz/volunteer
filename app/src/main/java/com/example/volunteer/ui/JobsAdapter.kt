package com.example.volunteer.ui

import Job
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteer.R

class JobsAdapter(
    private val jobsList: List<Job>
) : RecyclerView.Adapter<JobsAdapter.ViewHolder>() {

    var listener: OnClickJoinButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_working_hours, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobsList[position]
        if (position > 0) {
            val previousJob = jobsList[position - 1]
            if (previousJob.day == job.day) {
                holder.bind(job, position, listener)
            } else {
                holder.bind(job, position, listener)
            }
        } else {
            holder.bind(job, position, listener)
        }
    }

    override fun getItemCount(): Int = jobsList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val dayView: TextView = view.findViewById(R.id.day)
        private val timeView: TextView = view.findViewById(R.id.time)
        private val joinButton: Button = view.findViewById(R.id.join_button)

        @SuppressLint("SetTextI18n")
        fun bind(
            job: Job,
            position: Int,
            listener: OnClickJoinButton?
        ) {
            dayView.text = job.day
            timeView.text = job.time
            joinButton.setOnClickListener {
                listener?.setUserAttending(job, position)
            }
            if (job.isUserAttending) {
                joinButton.text = "Leave"
            } else {
                joinButton.text = "Join"
            }
        }
    }
}
