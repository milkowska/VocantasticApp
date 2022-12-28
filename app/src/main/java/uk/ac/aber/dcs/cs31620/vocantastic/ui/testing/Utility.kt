package uk.ac.aber.dcs.cs31620.vocantastic.ui.testing

 fun anagramCreator(originalWord: String): String {
    val stringValueToArray = originalWord.trim().toCharArray()
   return stringValueToArray.sortedDescending().joinToString("")
}

fun randomIndexGenerator(lastIndex: Int, idValues: List<Int>): Int {
    while (true) {
        // inclusive or exclusive?
        val index = (0 until lastIndex + 1).random()
        if (index !in idValues) {
            return index
        }
    }

}