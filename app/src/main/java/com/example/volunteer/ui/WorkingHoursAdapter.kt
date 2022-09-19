package com.example.volunteer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteer.R
import com.example.volunteer.data.Job

class WorkingHoursAdapter(
    private val jobsList: List<Job>
) : RecyclerView.Adapter<WorkingHoursAdapter.ViewHolder>() {

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
                holder.populate("", job.time, job.isUserAttending)
            }
        } else {
            holder.populate(job.day, job.time, job.isUserAttending)
        }
    }

    override fun getItemCount(): Int = jobsList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val day: TextView = view.findViewById(R.id.working_day)
        private val time: TextView = view.findViewById(R.id.working_time)
        private val join: Button = view.findViewById(R.id.join_button)

        fun populate(workingDay: String, workingHours: String, isUserAttending: Boolean = false) {
            day.text = workingDay
            time.text = workingHours
            if (isUserAttending) {
                join.text = "Leave"
            } else {
                join.text = "Join"
            }
        }
    }
}
