package es.uji.al404230.ujisubjects

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllSubjectsActivity : AppCompatActivity(), AllSubjectsInterface {
    private lateinit var allSubjectsRecyclerView : RecyclerView

    private lateinit var searchTextView: TextView
    private lateinit var prefixEditText: EditText
    lateinit var prefix :String

    private lateinit var presenter : AllSubjectsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.allsubjects_activity)

        if (savedInstanceState != null) {
            prefix= savedInstanceState.getString(PREFIX).toString()
        }else  prefix = ""



        allSubjectsRecyclerView = findViewById(R.id.allSubjectsRecyclerView)
        allSubjectsRecyclerView.layoutManager = LinearLayoutManager(this)

        searchTextView = findViewById(R.id.searchTextView)


        prefixEditText = findViewById(R.id.prefixEditText)
        prefixEditText.addTextChangedListener{ presenter.onPrefixTextChanged(it.toString())}

        presenter = AllSubjectsPresenter(MainModel(applicationContext), this, prefix)


    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString(PREFIX, prefixEditText.toString())

    }


    override fun showSubjects(subjects : List<Subject>){

        allSubjectsRecyclerView.adapter = SubjectsAdapter(subjects) {
                subject -> presenter.clickedSubjectsAdapter(subject)
        }
    }

    override fun showMessage(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }

    /*override fun showSubjects(subjects: List<Subject>) {

    }*/


    override fun toUpdate(subject : Subject) {
        //se pasa a update porque antes se ha clickado una asignatura
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("subjectCode", subject.code)
        intent.putExtra("subjectName", subject.name)

        startActivity(intent)
    }

    companion object{
        private const val PREFIX = "PREFIX"
    }
}
