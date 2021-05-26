package com.tugraz.asd.modernnewsgroupapp

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.tugraz.asd.modernnewsgroupapp.db.NewsgroupDb
import com.tugraz.asd.modernnewsgroupapp.helper.ContextUtils
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ServerObservable
    var skipSetup: Boolean = false;
    lateinit var db: NewsgroupDb
    var start: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        start = System.currentTimeMillis()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen_new)

        viewModel = this.run {
            ViewModelProviders.of(this).get(ServerObservable::class.java)
        } ?: throw Exception("Invalid Activity")

        var count = 0

        lifecycleScope.launch {
            db = Room
                .databaseBuilder(applicationContext, NewsgroupDb::class.java, "newsgroup.db")
                .fallbackToDestructiveMigration()
                .build()

            count = db.newsgroupServerDao().getCount()
            println("Servers in DB: " + count)
            onDbFinished(count)
        }
    }

    fun onDbFinished(serverCount: Int) {
        val controller = NewsgroupController()
        controller.db = db


        if(serverCount > 0) {
            lifecycleScope.launch {
                skipSetup = true;
                controller.loadServersFromDB()
                controller.currentServer = controller.servers.keys.first()
                controller.loadNewsgroupsFromDB();
                viewModel.controller.postValue(controller)
            }
        } else {
            viewModel.controller.value = controller
        }

        if(System.currentTimeMillis() - start < 3000) {
            Thread.sleep(3000 - (System.currentTimeMillis() - start))
        }
        setContentView(R.layout.activity_add_newsgroup)
    }

    override fun attachBaseContext(newBase: Context) {
        // get chosen language from shared preference
        val prefs = PreferenceManager.getDefaultSharedPreferences(newBase);
        val localeString = prefs.getString("language", "en")
        println("Language: $localeString")
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, Locale(localeString))
        super.attachBaseContext(localeUpdatedContext)
    }

}
