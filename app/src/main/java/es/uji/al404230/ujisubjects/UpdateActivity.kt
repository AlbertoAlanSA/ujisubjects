package es.uji.al404230.ujisubjects

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UpdateActivity : AppCompatActivity(), UpdateInterface {

    private lateinit var updateRecyclerView : RecyclerView

    private lateinit var updateButton : Button

    private lateinit var presenter : UpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_activity)



        val subjectCode = intent.getStringExtra("subjectCode").toString()
        val subjectName = intent.getStringExtra("subjectName")
        supportActionBar?.title = subjectCode
        supportActionBar?.subtitle = subjectName

        updateRecyclerView = findViewById(R.id.updateRecyclerView)
        updateRecyclerView.layoutManager = LinearLayoutManager(this)


        updateButton = findViewById(R.id.updateButton)
        updateButton.setOnClickListener { presenter.onUpdateButtonClicked()}


        presenter = UpdatePresenter(MainModel(applicationContext), this, subjectCode)
        if (savedInstanceState != null)
            presenter.setSate(savedInstanceState)
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        presenter.saveState(savedInstanceState)
    }



    override fun showGroups( groups: List<MyGroup>) {
        updateRecyclerView.adapter = UpdateAdapter(groups){
            group -> presenter.clickedUpdateAdapter(group)
        }
    }

    override fun showMessage(error : String) {
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun finishPressed() {
        val intent = Intent(this, MySubjectsActivity::class.java)
        startActivity(intent)
    }
}