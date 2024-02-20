package es.uji.al404230.ujisubjects

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MySubjectsActivity : AppCompatActivity(), MySubjectsInterface,
    SubjectDialogFragment.SubjectDialogListener {

    private lateinit var mySubjectsRecyclerView : RecyclerView

    private lateinit var addSubjectsButton : Button
    private lateinit var noSubjectsTextView: TextView

    private lateinit var presenter : MySubjectsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mysubjects_activity)
        supportActionBar?.title = "My Subjects"

        mySubjectsRecyclerView = findViewById(R.id.MySubjectsRecyclerView)
        mySubjectsRecyclerView.layoutManager = LinearLayoutManager(this)

        addSubjectsButton = findViewById(R.id.AddSubjectButton)
        addSubjectsButton.setOnClickListener{ presenter.addSubjectPressed() }

        noSubjectsTextView = findViewById(R.id.NoSubjectsTextView)

        presenter = MySubjectsPresenter(MainModel(applicationContext), this)
    }

    override fun showSubjects (subjects: List<Subject>) {

        if (subjects.isEmpty()) {
            noSubjectsTextView.visibility = View.VISIBLE
            mySubjectsRecyclerView.visibility = View.GONE
        } else {
            noSubjectsTextView.visibility = View.GONE
            mySubjectsRecyclerView.adapter =
                SubjectsAdapter(subjects) { presenter.clickedSubjectsAdapter(it) }
        }
    }
    override  fun showMessage(error: String) {
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun showSubjectDetails(subject: Subject, groups : List<MyGroup>) = SubjectDialogFragment().run {
        arguments = Bundle().apply {
            putParcelable("SUBJECT", subject)
            putParcelableArrayList("GROUPS", ArrayList(groups))
        }
        show(supportFragmentManager, "GradeDetails")
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun toAllSubjects () {
        val intent = Intent(this, AllSubjectsActivity::class.java)
        startActivity(intent)
    }

    override fun toUpdate () {

    }

    override fun subjectUpdateRequested(subject: Subject) {
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("subjectCode", subject.code)
        intent.putExtra("subjectName", subject.name)
        startActivity(intent)
    }

    override fun onGroupChosen(s: String) {

    }
}