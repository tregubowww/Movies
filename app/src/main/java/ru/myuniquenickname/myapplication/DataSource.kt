package ru.myuniquenickname.myapplication

object DataSource {

    const val ID_KEY = "ID_movie"

    private val listActorsAvengers = listOf(
        RecyclerViewItemActors(R.drawable.actor_robert_downey, "Rober Downey Jr."),
        RecyclerViewItemActors(R.drawable.actor_chris_evans, "Chris Evans"),
        RecyclerViewItemActors(R.drawable.actor_mark_ruffalo, "Mark Ruffalo"),
        RecyclerViewItemActors(R.drawable.actor_chris_hemsworth, "Chris Hemsworth")
    )


    val listMovies = listOf(
        RecyclerViewItemMovie(
            false,
            1,
            R.drawable.logo_list_avengers,
            R.drawable.mask_list_avengers,
            R.drawable.logo_movie_avengers,
            R.drawable.mask_movie_avengers,
            13,
            4.1.toFloat(),
            "Avengers:   End Game",
            "After the devastating events of Avengers: Infinity War, the universe is in ruins." +
                    " With the help of remaining allies, the Avengers assemble once more in order to reverse " +
                    "Thanos actions and restore balance to the universe.",
            "Action, Adventure, Fantasy",
            125,
            137,
            listActorsAvengers
        ),

        RecyclerViewItemMovie(
            false,
            2,
            R.drawable.logo_list_tenet,
            R.drawable.mask_list_avengers,
            0,
            0,
            16,
            5.0.toFloat(),
            "Tenet",
            "",
            "Action, Sci-Fi, Thriller ",
            98,
            97,
            listOf()
        ),
        RecyclerViewItemMovie(
            false,
            3,
            R.drawable.logo_list_black_widow,
            R.drawable.mask_list_black_widow,
            0,
            0,
            13,
            4.0.toFloat(),
            "Black Widow",
            "",
            "Action, Adventure, Sci-Fi",
            38,
            102,
            listOf()
        ),
        RecyclerViewItemMovie(
            false,
            4,
            R.drawable.logo_list_wonder_woman,
            R.drawable.mask_list_wonder_woman,
            0,
            0,
            13,
            5.0.toFloat(),
            "Wonder Woman 1984",
            "",
            "Action, Adventure, Fantasy",
            74,
            120,
            listOf()
        ),


        )
}