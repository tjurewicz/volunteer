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
    private val jobMap: Map<Job, Boolean>
) : RecyclerView.Adapter<WorkingHoursAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_working_hours, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobMap.keys.elementAt(position)
        if (position > 0) {
            val previousJob = jobMap.keys.elementAt(position - 1)
            if (previousJob.day == job.day) {
                holder.populate("", job.time, jobMap[job])
            }
        }
        holder.populate(job.day, job.time, jobMap[job])
    }

    override fun getItemCount(): Int = jobMap.entries.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val day: TextView = view.findViewById(R.id.working_day)
        private val time: TextView = view.findViewById(R.id.working_time)
        private val join: Button = view.findViewById(R.id.join_button)

        fun populate(workingDay: String, workingHours: String, joined: Boolean? = false) {
            day.text = workingDay
            time.text = workingHours
        }
    }
}
