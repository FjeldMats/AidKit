package com.example.aidkit

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException


class MainActivity : AppCompatActivity() {


    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */


    private class loadGrupper : AsyncTask<String, Int, Array<Gruppe>>() {
        private val APPLICATION_NAME = "S aid"
        private val JSON_FACTORY: JsonFactory =
            JacksonFactory.getDefaultInstance()
        private val TOKENS_DIRECTORY_PATH = "tokens"
        /**
         * Global instance of the scopes required by this quickstart.
         * If modifying these scopes, delete your previously saved tokens/ folder.
         */
        private val SCOPES =
            listOf(SheetsScopes.SPREADSHEETS_READONLY)
        private val CREDENTIALS_FILE_PATH = "app/src/main/assets/credentials.json"
        /**
         * Creates an authorized Credential object.
         * @param HTTP_TRANSPORT The network HTTP Transport.
         * @return An authorized Credential object.
         * @throws IOException If the credentials.json file cannot be found.
         */
        @Throws(IOException::class)
        private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential { // Load client secrets.
            println(System.getProperty("user.dir"))
            val `in` =
                assets.open("credentials.json")
                    ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
            val clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))
            // Build flow and trigger user authorization request.
            val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
            )
                .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build()
            val receiver = LocalServerReceiver.Builder().setPort(8888).build()
            return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
        }

        fun getGrupper(): MutableList<MutableList<String>> {
            return getData("Grupper!A2:D2")
        }

        @Throws(IOException::class, GeneralSecurityException::class)
        fun getData(range : String): MutableList<MutableList<String>> { // Build a new authorized API client service.
            val HTTP_TRANSPORT = com.google.api.client.http.javanet.NetHttpTransport()
            val spreadsheetId = "1hdsPzkwDFXMoe556PbUq7SkFiYt2_BZeAl9m97Gro-g"
            val service = Sheets.Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                getCredentials(HTTP_TRANSPORT)
            )
                .setApplicationName(APPLICATION_NAME)
                .build()
            val response =
                service.spreadsheets().values()[spreadsheetId, range]
                    .execute()
            val ret = response.getValues() as MutableList<MutableList<String>>
            if (ret == null)
                return mutableListOf(mutableListOf())
            else
                return ret
        }
        override fun doInBackground(vararg params: String?): Array<Gruppe> {
            Looper.prepare()
            val grupper = getGrupper()
            val ret = Array(grupper.size) {Gruppe(grupper[it][0], grupper[it][1], grupper[it][2])}
            return ret
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<Button>(R.id.HelseBtn)
        val btn2 = findViewById<Button>(R.id.SosialtBtn)
        val btn3 = findViewById<Button>(R.id.OkonomiBtn)


        val helseActivity = Intent(this, HelseActivity::class.java)
        val okonomiActivity = Intent(this, OkonomiActivity::class.java)

        btn1.setOnClickListener{
            startActivity(helseActivity)
        }


        btn2.setOnClickListener{
            val task = loadGrupper().execute()
            val sosialtActivity = Intent(this, SosialtActivity::class.java)
            val dat = task.get()
            if (dat.isEmpty())
                Toast.makeText(applicationContext, "Det er ingen grupper", Toast.LENGTH_LONG).show()
            else
                startActivity(sosialtActivity.putExtra("grupper", task.get()))
        }

        btn3.setOnClickListener{
            startActivity(okonomiActivity)
        }

    }

    fun openSettings(v: View){
        startActivity(Intent(this, Settings::class.java))
    }

}
