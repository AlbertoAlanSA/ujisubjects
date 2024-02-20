package es.uji.al404230.ujisubjects

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SubjectDialogFragment : DialogFragment() {
    interface SubjectDialogListener {
        fun subjectUpdateRequested(subject: Subject)
        fun onGroupChosen(s: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val subject  =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments ?.getParcelable("SUBJECT", Subject::class.java)!!
            } else {
                arguments ?.getParcelable("SUBJECT")!!
            }

        val groups =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList("GROUPS", MyGroup::class.java)!!
            } else {
                arguments?.getParcelableArrayList("GROUPS")!!
            }

        val groupsFixed : ArrayList<String> = ArrayList()
        for(group in groups){
            if(group.selected) groupsFixed.add("${group.groupName} (${group.room})")
        }

        val groupsListener = activity as SubjectDialogListener
        return AlertDialog.Builder(requireContext()).run{
            setTitle(subject.code)

            setItems(groupsFixed.toTypedArray(), null)
            setNeutralButton("Update"
            ) { _, _ -> groupsListener.subjectUpdateRequested(subject) }
            setPositiveButton("Ok") { dialog, _ -> dialog.cancel() }
            create()
        }
    }
}

