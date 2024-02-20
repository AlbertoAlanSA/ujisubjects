package es.uji.al404230.ujisubjects

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NetworkAccess private constructor(context: Context) { // MANAGES THE QUEUE

    companion object: SingletonHolder<NetworkAccess, Context>(::NetworkAccess){
        private const val PAGE: String = "page"
        private const val ROWS: String = "rowCount"
        private const val CONTENT: String = "content"
        private const val NAME: String = "nombreEN"
        private const val GROUP_NAME: String = "subgrupo"
        private const val CODE: String = "_id"
        private const val SUBJECT: String = "asignatura"
        private const val ROOM: String = "aula"
    }

    private val queue = Volley.newRequestQueue(context)

    fun getAllSubjects(listener: Response.Listener<List<Subject>>, errorListener: Response.ErrorListener) {

        val url = "https://ujiapps.uji.es/lod-autorest/api/datasets/asignaturas"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response -> querySubjects(response.getJSONObject(PAGE).getInt(ROWS), listener, errorListener) },
            errorListener)

        queue.add(request)
    }

    private fun querySubjects(count: Int, listener: Response.Listener<List<Subject>>, errorListener: Response.ErrorListener) {

        val url = "https://ujiapps.uji.es/lod-autorest/api/datasets/asignaturas?start=0&limit=$count"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { finalResponse ->  parseSubjects(listener, finalResponse) },
            errorListener)

        queue.add(request)
    }

    private fun parseSubjects(listener: Response.Listener<List<Subject>>, jobject: JSONObject) {

        val subjects = ArrayList<Subject>()
        val content = jobject.getJSONArray(CONTENT)
        for (i in 0 until content.length()) {
            val subjectObject = content[i] as JSONObject
            val code = subjectObject.getString(CODE)
            val name = subjectObject.optString(NAME, "UNKNOWN")
            subjects.add(Subject(code, name))
        }

        listener.onResponse(subjects as List<Subject>)
    }


    fun getSubjectDetails(code: String, listener: Response.Listener<List<MyGroup>>, errorListener: Response.ErrorListener) {

        val url = "https://ujiapps.uji.es/lod-autorest/api/datasets/asignaturas/$code/aulas"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->  parseDetails(listener, response) },
            errorListener )

        queue.add(request)
    }

    private fun parseDetails(listener: Response.Listener<List<MyGroup>>, jobject: JSONObject) {
        // PARSING
        val groups = ArrayList<MyGroup>()
        val content = jobject.getJSONArray(CONTENT) // constant with "content"
        for (i in 0 until content.length()) {
            val subjectObject = content[i] as JSONObject
            val code = subjectObject.getString(SUBJECT)
            val groupName = subjectObject.optString(GROUP_NAME, "UNKNOWN")
            val room = subjectObject.getString(ROOM)
            groups.add(MyGroup(code, groupName, room, false))
        }
        // RETURN THE RESULT USING THE LISTENER
        listener.onResponse(groups as List<MyGroup>)
    }
}