package ru.myuniquenickname.myapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.myuniquenickname.myapplication.data.dataMapping.ActorListDto
import ru.myuniquenickname.myapplication.data.dataMapping.GenresListDto
import ru.myuniquenickname.myapplication.data.dataMapping.ImageDto
import ru.myuniquenickname.myapplication.data.dataMapping.MovieDetailsDto
import ru.myuniquenickname.myapplication.data.dataMapping.MoviesDto

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getMoviesPopular(): MoviesDto

    @GET("movie/top_rated")
    suspend fun getMoviesTopMovies(): MoviesDto

    @GET("movie/upcoming")
    suspend fun getMoviesUpcoming(): MoviesDto

    @GET("search/movie")
    suspend fun getMoviesSearch(
        @Query("query") movie: String
    ): MoviesDto

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresListDto

    @GET("configuration")
    suspend fun getImage(): ImageDto

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Long
    ): MovieDetailsDto

    @GET("movie/{id}/credits")
    suspend fun getActors(
        @Path("id") id: Long,
    ): ActorListDto
}
