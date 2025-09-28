package tbank.academy.scala.hangman.core

sealed trait GuessResult
object GuessResult {
  case object Correct        extends GuessResult
  case object Incorrect      extends GuessResult
  case object AlreadyGuessed extends GuessResult
  case object GameWon        extends GuessResult
  case object GameLost       extends GuessResult
}
