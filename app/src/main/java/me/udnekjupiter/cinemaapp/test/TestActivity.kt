//package com.example.httpapp
//
//import android.R
//import android.os.Bundle
//import android.webkit.WebView
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStream
//import java.io.InputStreamReader
//import java.net.URL
//import javax.net.ssl.HttpsURLConnection
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val contentView = findViewById<TextView>(R.id.content)
//        val webView = findViewById<WebView>(R.id.webView)
//        webView.settings.javaScriptEnabled = true
//        val btnFetch = findViewById<Button>(R.id.downloadBtn)
//        btnFetch.setOnClickListener {
//            contentView.text = "Загрузка..."
//            Thread {
//                try {
//                    val content = getContent("https://stackoverflow.com/")
//                    webView.post {
//                        webView.loadDataWithBaseURL(
//                            "https://stackoverflow.com/",
//                            content,
//                            "text/html",
//                            "UTF-8",
//                            "https://stackoverflow.com/"
//                        )
//                        Toast.makeText(
//                            applicationContext,
//                            "Данные загружены",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    contentView.post { contentView.text = content }
//                } catch (ex: IOException) {
//                    contentView.post {
//                        contentView.text = "Ошибка: " + ex.message
//                        Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//            }.start()
//        }
//    }
//
//    @Throws(IOException::class)
//    private fun getContent(path: String): String {
//        var reader: BufferedReader? = null
//        var stream: InputStream? = null
//        var connection: HttpsURLConnection? = null
//        try {
//            val url = URL(path)
//            connection = url.openConnection() as HttpsURLConnection
//            connection.requestMethod = "GET"
//            connection!!.readTimeout = 10000
//            connection.connect()
//            stream = connection.inputStream
//            reader = BufferedReader(InputStreamReader(stream))
//            val buf = StringBuilder()
//            var line: String?
//            while ((reader.readLine().also { line = it }) != null) {
//                buf.append(line).append("\n")
//            }
//            return (buf.toString())
//        } finally {
//            reader?.close()
//            stream?.close()
//            connection?.disconnect()
//        }
//    }
//}