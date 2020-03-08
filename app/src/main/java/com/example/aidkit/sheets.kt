package com.example.aidkit

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.security.GeneralSecurityException


object SheetsQuickstart : AppCompatActivity()  {
    private const val APPLICATION_NAME = "S aid"
    private val JSON_FACTORY: JsonFactory =
        JacksonFactory.getDefaultInstance()
    private const val TOKENS_DIRECTORY_PATH = "tokens"
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES =
        listOf(SheetsScopes.SPREADSHEETS_READONLY)
    private const val CREDENTIALS_FILE_PATH = "app/src/main/assets/credentials.json"
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
            applicationContext.assets.open("credentials.json")
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

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    fun getGrupper(): MutableList<MutableList<String>> {
        return getData("Grupper!A2:D2")
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    @JvmStatic
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
}