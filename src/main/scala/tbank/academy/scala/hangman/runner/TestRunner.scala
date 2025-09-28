package tbank.academy.scala.hangman.runner

import tbank.academy.scala.hangman.core.GameEngine
import tbank.academy.scala.hangman.error.DomainError

object TestRunner {
  def runTest(wordToGuess: String, guesses: String): Either[DomainError, String] = {
    val initialState = GameEngine.initGame(wordToGuess, 0, "")

    val finalState = guesses.foldLeft(initialState) { (state, guess) =>
      if (state.isGameOver) state
      else {
        val (newState, _) = GameEngine.makeGuess(state, guess)
        newState
      }
    }

    val maskedWord = finalState.currentMaskedWord
    val result     = if (finalState.isWon) "POS" else "NEG"

    Right(s"$maskedWord;$result")
  }
}
