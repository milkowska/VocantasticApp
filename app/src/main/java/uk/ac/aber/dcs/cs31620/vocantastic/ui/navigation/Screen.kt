package uk.ac.aber.dcs.cs31620.vocantastic.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object List : Screen("list")
    object AnagramTest : Screen("anagram")
    object FindTest : Screen("findanswer")
    object Settings : Screen("settings")
    object Test : Screen("test")
    object Words : Screen("add")
    object Welcome : Screen("welcome")
    object TestScore : Screen("score")
    object Flashcard : Screen("flashcards")

}

val screens = listOf(
    Screen.Home,
    Screen.Words,
    Screen.List,
    Screen.Test,
    )