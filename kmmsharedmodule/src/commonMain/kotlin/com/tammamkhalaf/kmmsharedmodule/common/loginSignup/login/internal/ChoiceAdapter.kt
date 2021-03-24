package com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal

import androidx.recyclerview.widget.RecyclerView

class ChoiceAdapter(activity: android.app.Activity, choices: List<com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.Choice>) : RecyclerView.Adapter<ChoiceAdapter.ViewHolder?>() {
    private val activity: android.app.Activity
    private val choices: List<com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.Choice>
    @NonNull
    fun onCreateViewHolder(@NonNull parent: android.view.ViewGroup, viewType: Int): ViewHolder {
        val view: android.view.View = android.view.LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choice, parent, false)
        return ViewHolder(view)
    }

    fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val choice: com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.Choice = choices[position]
        holder.bind(choice)
    }

    fun getItemCount(): Int {
        return choices.size
    }

    inner class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: android.widget.TextView
        private val descText: android.widget.TextView
        private val launchButton: android.widget.Button
        fun bind(choice: com.tammamkhalaf.myuaeguide.common.loginSignup.login.internal.Choice) {
            titleText.setText(choice.title)
            descText.setText(choice.description)
            launchButton.setOnClickListener(object : android.view.View.OnClickListener() {
                override fun onClick(v: android.view.View) {
                    activity.startActivity(choice.launchIntent)
                }
            })
        }

        init {
            titleText = itemView.findViewById<android.widget.TextView>(R.id.item_title)
            descText = itemView.findViewById<android.widget.TextView>(R.id.item_description)
            launchButton = itemView.findViewById<android.widget.Button>(R.id.item_launch_button)
        }
    }

    init {
        this.activity = activity
        this.choices = choices
    }
}