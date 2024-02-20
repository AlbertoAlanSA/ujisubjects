package es.uji.al404230.ujisubjects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UpdateAdapter(private val groups: List<MyGroup>/*no tengo clara la clase*/, private val onClickListener: (MyGroup)->Unit)
    :RecyclerView.Adapter<UpdateAdapter.ViewHolder>(){

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val checkBoxUpdate : CheckBox = view.findViewById(R.id.checkBoxSubject)
        val groupTextView : TextView = view.findViewById(R.id.groupTextView)
        val roomTextView : TextView = view.findViewById(R.id.roomTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.update_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groups.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(groups[position]){
            holder.groupTextView.text = groupName
            holder.roomTextView.text = "($room)" //no tengo claro la clase asi que esto pues fallara
            holder.checkBoxUpdate.isChecked = groups[position].selected
            holder.checkBoxUpdate.setOnClickListener{ onClickListener(this) }
        }
    }
}