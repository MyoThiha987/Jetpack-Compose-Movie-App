package com.myothiha.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.myothiha.data.cache.database.MovieDatabase
import com.myothiha.data.cache.database.dao.MovieDao
import com.myothiha.data.cache.entity.MovieEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @Author Liam
 * Created at 01/Aug/2024
 */
@RunWith(JUnit4::class)
class MovieDaoTest {
    private lateinit var movieDao: MovieDao
    private lateinit var database: MovieDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context = context, MovieDatabase::class.java).build()
        movieDao = database.movieDao()
    }

    @Test
    fun test_save_movies() = runBlocking {
        val movieList = listOf(
            TestMovieEntity(movieType = 0, 0, false),
            TestMovieEntity(movieType = 1, 1, false),
            TestMovieEntity(movieType = 2, 2, false)
        )

        movieDao.saverMovies(data = movieList)
        val retrieveMovies = movieDao.retrieveCacheMovies().first()

        assertEquals(movieList.size, retrieveMovies.count())

        movieList.forEachIndexed { index, expected ->
            val actual = retrieveMovies[index]
            assertEquals(expected.copy(autoId = actual.autoId), actual)
        }
    }

    @Test
    fun test_update_movies() = runBlocking {
        val initialMovie = TestMovieEntity(movieType = 0, 0, false)
        movieDao.saverMovies(listOf(initialMovie))

        movieDao.updateSaveMovie(movieId = 0, isLiked = true, movieType = 0)
        val retrieveMovie = movieDao.retrieveCacheMovies().first()
        assertEquals(true, retrieveMovie[0].isLiked)
    }

    @Test
    fun test_delete_movies() = runBlocking {
        val initialMovie = TestMovieEntity(movieType = 0, 0, true)
        movieDao.saverMovies(listOf(initialMovie))

        movieDao.deleteCacheMovies(movieType = 0)
        val retrieveMovie = movieDao.retrieveCacheMovies().first()
        assertEquals(0, retrieveMovie.size)
    }

    @Test
    fun test_retrieve_bookmarked_movies() = runBlocking {
        val movieList = listOf(
            TestMovieEntity(movieType = 0, 0, true),
            TestMovieEntity(movieType = 1, 1, false),
            TestMovieEntity(movieType = 2, 2, true)
        )
        movieDao.saverMovies(movieList)

        val retrieveMovies = movieDao.retrieveCacheMovies().first()

        assertEquals(2,retrieveMovies.filter { it.isLiked }.map { it.id }.size)
    }

    @After
    fun closeDatabase() = database.close()


}

fun TestMovieEntity(movieType: Int, movieId: Int, isLiked: Boolean) = MovieEntity(
    id = movieId,
    originalTitle = "A",
    overview = "This is A",
    popularity = 0.0,
    backdropPath = "",
    posterPath = "",
    releaseDate = "",
    voteAverage = 0.0,
    voteCount = 0,
    movieType = movieType,
    isLiked = isLiked
)