package ru.myuniquenickname.myapplication.presentation.movieList

import ru.myuniquenickname.myapplication.domain.entity.Movie

sealed class MoviesSearchResult
class ValidResult(val result: List<Movie>) : MoviesSearchResult()
object EmptyResult : MoviesSearchResult()
object EmptyQuery : MoviesSearchResult()
class ErrorResult(val e: Throwable) : MoviesSearchResult()
object TerminalError : MoviesSearchResult()
