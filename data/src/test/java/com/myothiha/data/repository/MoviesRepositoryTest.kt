package com.myothiha.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.myothiha.data.cache.entity.MovieEntity
import com.myothiha.domain.model.Credit
import com.myothiha.domain.model.Movie
import com.myothiha.domain.model.MovieDetail
import com.myothiha.domain.model.MovieFullDetail
import com.myothiha.domain.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals

/**
 * @Author Liam
 * Created at 02/Aug/2024
 */

@RunWith(JUnit4::class)
class MoviesRepositoryTest {

    var repository = mockk<MoviesRepository>()

    @Before
    fun create() {
        MockKAnnotations.init(this)
    }

    @Test
    fun test_fetch_movies(): Unit = runBlocking {
        val expectedMovies = listOf(
            Movie(
                id = 0,
                originalTitle = "A",
                overview = "This is A",
                popularity = 0.0,
                backdropPath = "",
                posterPath = "",
                releaseDate = "",
                voteAverage = 0.0,
                voteCount = 0,
                movieType = 0,
                isLiked = false
            )
        )

        coEvery { repository.retrieveMovies() } returns flowOf(expectedMovies)
        val actualMovies = repository.retrieveMovies().first()
        assertEquals(
            expectedMovies, actualMovies
        )
    }

    @Test
    fun test_retrieve_movie_detail(): Unit = runBlocking {
        val movie = MovieDetail(
            id = 0,
            originalTitle = "A",
            overview = "This is A",
            popularity = 0.0,
            backdropPath = "",
            posterPath = "",
            releaseDate = "",
            voteAverage = 0.0,
            voteCount = 0,
            revenue = 0,
            runtime = 0,
            genres = listOf(),
            productionCompanies = listOf()
        )

        val expectedfullDetailMovie = MovieFullDetail(
            movieDetail = movie,
            credit = Credit(cast = listOf(), crew = listOf(), id = 0)
        )

        coEvery { repository.retrieveMovieDetail(0) } returns expectedfullDetailMovie
        val actualMovieFullDetail = repository.retrieveMovieDetail(0)
        assertEquals(expectedfullDetailMovie, actualMovieFullDetail)
    }

    @Test
    fun test_update_fav_movie() = runBlocking {
        val expected = listOf(TestMovie(movieId = 0, isLiked = true, movieType = 0))
        coEvery { repository.updateSavedMovie(movieId = 0, isLiked = true, movieType = 0) }
        coEvery { repository.retrieveSavedMovies() } returns flowOf(expected)
        val actual = repository.retrieveSavedMovies().first()

        assertEquals(expected, actual)
    }

    @Test
    fun test_retrieve_fav_movie() = runBlocking {
        val expected = listOf(TestMovie(movieId = 0, isLiked = true, movieType = 0))
        coEvery { repository.retrieveSavedMovies() } returns flowOf(expected)
        val actual = repository.retrieveSavedMovies().first()

        assertEquals(expected, actual)
    }

    @Test
    fun test_fetch_paging_movie() = runBlocking {
        val items = listOf(
            TestMovie(movieId = 0, isLiked = true, movieType = 0),
            TestMovie(movieId = 1, isLiked = false, movieType = 0)
        )

        /*val pagingSourceFactory = items.asPagingSourceFactory()

        val pagingSource = pagingSourceFactory()
        val pager = Pager(
            config = PagingConfig,
            initialKey = null,
            pagingSourceFactory = { myRepository.pagingSource() }
        )
            .flow*/
    }
}

fun TestMovie(movieType: Int, movieId: Int, isLiked: Boolean) = Movie(
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