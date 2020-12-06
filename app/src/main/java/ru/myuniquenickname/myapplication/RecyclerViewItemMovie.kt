package ru.myuniquenickname.myapplication

 data class RecyclerViewItemMovie(
    var like: Boolean,
    val id: Int,
    val imageResourceLogoList: Int,
    val imageResourceMaskList: Int,
    val imageResourceLogoDetails: Int,
    val imageResourceMaskDetails: Int,
    val age: Int,
    val rating: Float,
    val name: String,
    val storyLine: String,
    val tag: String,
    val reviews: Int,
    val minutes: Int,
    val listActors: List<RecyclerViewItemActors>
)


