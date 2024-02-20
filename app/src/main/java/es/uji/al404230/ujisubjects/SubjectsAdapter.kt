package es.uji.al404230.ujisubjects


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubjectsAdapter (val subjects: List<Subject>, private val onClickListener: (Subject) -> Unit)
    : RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val subjectCodeTextView : TextView = view.findViewById(R.id.SubjectCodeTextView)
        val subjectNameTextView : TextView = view.findViewById(R.id.SubjectNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_content, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(subjects[position]){
            holder.subjectCodeTextView.text = code
            holder.subjectNameTextView.text = name
            holder.itemView.setOnClickListener {
                onClickListener(this)
            }
        }
    }
}
