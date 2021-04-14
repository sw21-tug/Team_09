package com.tugraz.asd.modernnewsgroupapp

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    private var _customFontTextView: TextView? = null
    private var _typeFace:           Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        //textView = findViewById(R.id.toolbar)
        //textView.typeface = ResourcesCompat.getFont(this, )
        //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50.toFloat())

        this._initializeResources()
        this._initializeGUI()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun _initializeResources() {
        this._customFontTextView = this.findViewById(R.id.headline)
        this._typeFace           = ResourcesCompat.getFont(this.applicationContext, R.font.caveat_bold)
    }

    private fun _initializeGUI() {
        this._customFontTextView!!.setTypeface(this._typeFace, Typeface.NORMAL)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}