package es.uji.al404230.ujisubjects

import android.content.Context
import android.util.Log
import com.android.volley.Response
import kotlinx.coroutines.*


class MainModel(context: Context) {

    private val myDatabase  = MyDatabase.getInstance(context)

    private val network  = NetworkAccess.getInstance(context)

    /* getAllSubjects  Receives a listener for a list of subjects and an error listener. The function first reads all the subjects
    from the database. If the list recovered is not empty, the list is passed to the listener and nothing else is done. If the list is
    empty, the corresponding network call is made. The list retrieved from the net is stored in the database and passed to the
    listener */
    fun getAllSubjects (listener: Response.Listener<List<Subject>>, errorListener: Response.ErrorListener) {

        // CONSULT DATABASE
        CoroutineScope(Dispatchers.Main).launch {
            val subjects = withContext(Dispatchers.IO) {
                myDatabase.dao.GetAllSubjects()
            }
            if (subjects.isNotEmpty()) {
                listener.onResponse(subjects)
            }

            // EMPTY DATABASE -> CONSULT NETWORK
            else {
                network.getAllSubjects({  /*suspend*/GlobalScope.launch{//globalscope.launch arregla lo de la corrutina
                    for (subject in it) {
                        myDatabase.dao.InsertSubject(subject)
                    }
                }}, errorListener)
                listener.onResponse(subjects)
            }
        }
    }

    /* filterSubjectsWithPrefix Receives a string with a prefix and a listener for a list of subjects. The function
    recovers from the database all the subjects whose code begins with the given prefix and passes it to the listener */
    fun filterSubjectsWithPrefix (prefix: String, listener: Response.Listener<List<Subject>>) {
        CoroutineScope(Dispatchers.Main).launch {
            val subjects = withContext(Dispatchers.IO) {
                myDatabase.dao.GetAllSubjectByPrefix(prefix)
            }
            listener.onResponse(subjects)
        }
    }

    /* getMySubjects Receives a listener for a list of subjects. The function recovers from the database the list of subjects
    selected by the user (the content of the MySubject table). */
    fun getMySubjects (listener: Response.Listener<List<Subject>>) {
        CoroutineScope(Dispatchers.Main).launch {
            val subjects = withContext(Dispatchers.IO) {
                myDatabase.dao.GetAllMySubjects()
            }
            listener.onResponse(subjects)
        }
    }


    /* getGroups Receives a string with the code of a subject, a listener for a list of MyGroup, and an error listener. If
    the subject is part of the table MySubject, the function recovers the entries in the table MyGroup corresponding to the
    subject. If the subject is not in the table MySubject, the list is retrieved from the network.*/
    fun getGroups (code: String, listener: Response.Listener<List<MyGroup>>, errorListener: Response.ErrorListener) {
        CoroutineScope(Dispatchers.Main).launch {
            val groups: List<MyGroup>
            val isMySubject = withContext(Dispatchers.IO) {
                myDatabase.dao.KnowIfMySubject(code)
            }

            // SUBJECT IN THE DATABASE
            Log.i(isMySubject.toString(), "getGroups: ")
            if (isMySubject>0) {
                groups = withContext(Dispatchers.IO) {
                    myDatabase.dao.GetAllGoups(code)
                }
                listener.onResponse(groups)
            }

            // SUBJECT NOT IN DATABASE
            else {
                network.getSubjectDetails(code, listener, errorListener)
            }
        }
    }


    /* updateGroups Receives a list of MyGroup and a Listener for Unit. If any of the elements of the list is selected,
    the list and the subject are stored in the database. If none of the elements is selected, the list and the subject are deleted.
    Note that as the subject is a foreign key for MyGroup, the order of deletion has to be first the elements of the list and then
    the subject. In order to find the subject, you can look the first element of the list like this:
        val mySubject = MySubject(myGroups.first().subjectCode) */
    fun updateGroups (groups: List<MyGroup>, listener: Response.Listener<Unit>) {
        var selected = false
        for(group in groups) {
            if(group.selected) {
                selected = true
                break
            }
        }
        val subject = MySubject(groups.first().groupSubjectCode)

        // ADD GROUPS AND SUBJECT TO THE DATABASE
        if (selected) {
            CoroutineScope(Dispatchers.Main).launch {GlobalScope.launch{/*suspend{*/

                myDatabase.dao.InsertMySubject(subject)
                for (group in groups)
                    myDatabase.dao.InsertMyGroup(group)
            }}
            listener.onResponse(Unit)
        }

        // NO ITEMS SELECTED -> DELETE LIST AND SUBJECT
        else {
            CoroutineScope(Dispatchers.Main).launch {GlobalScope.launch{/*suspend{*/
                for (group in groups)
                    myDatabase.dao.DeleteMyGroup(group)
                myDatabase.dao.DeleteMySubject(subject)
            }}
            listener.onResponse(Unit)
        }
    }
}
