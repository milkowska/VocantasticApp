package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

import uk.ac.aber.dcs.cs31620.vocantastic.model.WordPair

// Creates an anagram given a word by sorting the letters in descending order.
fun anagramCreator(originalWord: String): String {
    val stringValueToArray = originalWord.trim().toCharArray()
    return stringValueToArray.sortedDescending().joinToString("")
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
    return if(list.size >= 15) {
        15
    } else {
        list.size
    }
}