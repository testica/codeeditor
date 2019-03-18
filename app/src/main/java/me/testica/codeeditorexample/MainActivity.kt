package me.testica.codeeditorexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import me.testica.codeeditor.SyntaxHighlightRule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set rules
        et_file.setSyntaxHighlightRules(*getSyntaxHighlightRulesFromRaw())

    }

    private fun getSyntaxHighlightRulesFromRaw(): Array<SyntaxHighlightRule> {
        // Nice hack to get json as string from raw resource
        val jsonString = resources.openRawResource(R.raw.html).bufferedReader().use { it.readText() }

        // serialize json string
        return Gson().fromJson(jsonString, object : TypeToken<Array<SyntaxHighlightRule>>(){}.type)
    }
}
