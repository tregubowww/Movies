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
    @GET("movie/popular?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getMoviesPopular(): MoviesDto

    @GET("movie/top_rated?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getMoviesTopMovies(): MoviesDto

    @GET("movie/upcoming?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getMoviesUpcoming(): MoviesDto

    @GET("search/movie/?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getMoviesSearch(
        @Query("query") movie: String
    ): MoviesDto

    @GET("genre/movie/list?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getGenres(): GenresListDto

    @GET("configuration?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getImage(): ImageDto

    @GET("movie/{id}?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getMovie(
        @Path("id") id: Long
    ): MovieDetailsDto

    @GET("movie/{id}/credits?api_key=3b4970990228d308657e91ae5af780b1")
    suspend fun getActors(
        @Path("id") id: Long,
    ): ActorListDto
}
