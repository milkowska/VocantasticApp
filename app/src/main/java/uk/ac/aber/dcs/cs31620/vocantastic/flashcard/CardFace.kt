package uk.ac.aber.dcs.cs31620.vocantastic.flashcard

/**
 * Class for card that can be flipped whe using flashcards.
 */
enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}
