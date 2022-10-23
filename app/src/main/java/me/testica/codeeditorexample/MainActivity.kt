package me.testica.codeeditorexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.testica.codeeditor.SyntaxHighlightRule
import me.testica.codeeditorexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set rules
        binding.etFile.setSyntaxHighlightRules(*getSyntaxHighlightRulesFromRaw())

    }

    private fun getSyntaxHighlightRulesFromRaw(): Array<SyntaxHighlightRule> {
        // Nice hack to get json as string from raw resource
        val jsonString = resources.openRawResource(R.raw.html).bufferedReader().use { it.readText() }

        // serialize json string
        return Gson().fromJson(jsonString, object : TypeToken<Array<SyntaxHighlightRule>>(){}.type)
    }
}
