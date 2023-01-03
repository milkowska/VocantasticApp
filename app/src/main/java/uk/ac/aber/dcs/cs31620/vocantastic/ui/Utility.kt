package uk.ac.aber.dcs.cs31620.vocantastic.ui

import uk.ac.aber.dcs.cs31620.vocantastic.model.PreferencesViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair
import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPairViewModel
import uk.ac.aber.dcs.cs31620.vocantastic.storage.FOREIGN_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.storage.NATIVE_LANGUAGE_KEY
import uk.ac.aber.dcs.cs31620.vocantastic.storage.WELCOME_SCREEN

// Creates an anagram given a word by sorting the letters in descending order.
fun anagramCreator(originalWord: String): String {
    val stringToArray = originalWord.trim().toCharArray()
    return stringToArray.sortedDescending().joinToString("")
}

// This function will generate an index value that is not present in the given list, for uniqueness purpose.
fun randomIndexGenerator(lastIndex: Int, idValues: List<Int>): Int {
    while (true) {
        val index = (0 until lastIndex + 1).random()
        if (index !in idValues) {
            return index
        }
    }
}

// Defines the number of questions given the size of vocabulary list.
fun getNumberOfQuestions(list: List<WordPair>): Int {
    return if (list.size >= 15) {
        15
    } else {
        list.size
    }
}

fun deleteData(dataViewModel: PreferencesViewModel, wordPairViewModel: WordPairViewModel) {
    dataViewModel.saveBoolean(false, WELCOME_SCREEN)
    wordPairViewModel.clearWordList()
    dataViewModel.saveString("", NATIVE_LANGUAGE_KEY)
    dataViewModel.saveString("", FOREIGN_LANGUAGE_KEY)
}