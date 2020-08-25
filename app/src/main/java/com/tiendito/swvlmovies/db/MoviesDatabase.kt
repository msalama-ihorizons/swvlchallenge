package com.tiendito.swvlmovies.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.tiendito.swvlmovies.di.DatabaseModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream


@Database(
    entities = [Movie::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(DataConverters::class)
abstract class MoviesDatabase: RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {
            return INSTANCE
                ?:  synchronized(this) {
                    val instance =  Room.databaseBuilder(
                        context,
                        MoviesDatabase::class.java,
                        "movies.db"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("MoviesDatabase", "Data base created")

                            GlobalScope.launch {

                                val jsonObj = JsonParser().parse(
                                    readJSONFromAsset(
                                        context
                                    )
                                ).asJsonArray

                                val companyType = object : TypeToken<List<Movie>>() {}.type
                                val moviesList: List<Movie> = Gson().fromJson(jsonObj, companyType)
                                Log.d("MoviesDatabase", moviesList.size.toString())

                                INSTANCE?.let {
                                    populateDatabase(
                                        moviesList,
                                        it
                                    )
                                }
                            }
                        }
                    })
                        .build()
                    INSTANCE = instance
                    instance
                }
        }

        suspend fun populateDatabase(moviesList: List<Movie>, db: MoviesDatabase) {
            val moviesDao = db.moviesDao()

            Log.d("MoviesDatabase", moviesList.size.toString())
            // Empty database on first load
            moviesDao.deleteAll()

          /*  moviesList.forEach {
                moviesDao.insert(it)
            }*/

                moviesDao.insertMovies(moviesList)
        }

        fun readJSONFromAsset(appContext: Context): String {
            val json: String
            try {
                val inputStream: InputStream = appContext.assets.open("movies.json")
                json = inputStream.bufferedReader().use {
                    it.readText()
                }
            } catch (ex: Exception) {
                ex.localizedMessage
                return ""
            }
            return json
        }
    }


}