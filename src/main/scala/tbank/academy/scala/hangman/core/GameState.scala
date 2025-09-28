package tbank.academy.scala.hangman.core

final case class GameState(
  word: String,
  guessedLetters: Set[Char] = Set.empty,
  attempts: Int = 0,
  maxAttempts: Int = 6
) {
  private val normalizedWord = word.toLowerCase
  def isLetterGuessed(letter: Char): Boolean = guessedLetters.contains(letter)
  
  def currentMaskedWord: String = 
    word.map(c => 
      if (guessedLetters.contains(c.toLower)) c 
      else if (c == '-' || c == '_') c
      else '*'
    )
  
  def isWon: Boolean = normalizedWord.forall(c => guessedLetters.contains(c) || c == '-' || c == '_')
  
  def isLost: Boolean = {
    if (maxAttempts == 0) false
    else attempts >= maxAttempts
  }
  
  def isGameOver: Boolean = isWon || isLost
}