package tbank.academy.scala.hangman.core

object GameEngine {
  def makeGuess(state: GameState, letter: Char): (GameState, GuessResult) = {
    val normalizedLetter = letter.toLower
    if (state.isLetterGuessed(normalizedLetter)) {
      (state, GuessResult.AlreadyGuessed)
    } else if (state.word.toLowerCase.contains(normalizedLetter)) {
      val newState = state.copy(guessedLetters = state.guessedLetters + normalizedLetter)
      
      if (newState.isWon) (newState, GuessResult.GameWon)
      else (newState, GuessResult.Correct)
    } else {
      val newState = state.copy(
        guessedLetters = state.guessedLetters + normalizedLetter,
        attempts = state.attempts + 1
      )
      
      if (newState.isLost) (newState, GuessResult.GameLost)
      else (newState, GuessResult.Incorrect)
    }
  }
  
  def initGame(word: String, maxAttempts: Int = 6): GameState = 
    GameState(word = word, maxAttempts = maxAttempts)
}