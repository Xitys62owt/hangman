package tbank.academy.scala.hangman

import tbank.academy.scala.hangman.error.DomainError

object Hangman {
  @main def hangmanInteractive(): Unit = ???

  def hangmanTest(wordToGuess: String, guess: String): Either[DomainError, String] = ???
}
