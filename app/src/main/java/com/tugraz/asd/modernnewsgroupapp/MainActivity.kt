package com.tugraz.asd.modernnewsgroupapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.tugraz.asd.modernnewsgroupapp.db.NewsgroupDb
import com.tugraz.asd.modernnewsgroupapp.vo.NewsgroupServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ServerObservable
    lateinit var db: NewsgroupDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            onDbFinished(count)
        }



    }

    fun onDbFinished(serverCount: Int) {
        if(serverCount > 0) {

            lifecycleScope.launch {
                var servers: List<NewsgroupServer>? = null
                val controller = NewsgroupController()
                servers = db.newsgroupServerDao().getAll()

                for (server in servers) {
                    controller.addServer(server)
                }
                controller.currentServer = controller.servers.keys.first()

                withContext(Dispatchers.IO) {
                    controller.fetchNewsGroups()
                }

                controller.skipSetup = true


                if( viewModel.controller.value == null)
                {
                    viewModel.controller.value = controller
                }
            }
            setContentView(R.layout.activity_add_newsgroup)
        }
    }

}
