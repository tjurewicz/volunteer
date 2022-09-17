package com.example.volunteer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteer.R

class WorkingHoursAdapter(
    private val workingTimes: Map<String, String>
) : RecyclerView.Adapter<WorkingHoursAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_working_hours, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = workingTimes.keys.elementAt(position)
        workingTimes[day]?.let { holder.populate(day, it) }
    }

    override fun getItemCount(): Int = workingTimes.entries.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val day: TextView = view.findViewById(R.id.working_day)
        private val time: TextView = view.findViewById(R.id.working_time)
        private val join: Button = view.findViewById(R.id.join_button)

        fun populate(workingDay: String, workingHours: String) {
            day.text = workingDay
            time.text = workingHours
            var joined = false
            join.setOnClickListener {
                if (joined) {
                    joined = !joined
                    it.setBackgroundResource(R.color.blue_200)
                } else {
                    joined = !joined
                    it.setBackgroundResource(R.color.black)
                }
            }
        }
    }
}
