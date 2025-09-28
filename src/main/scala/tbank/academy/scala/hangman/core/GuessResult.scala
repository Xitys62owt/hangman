package tbank.academy.scala.hangman.core

enum GuessResult:
  case Correct
  case Incorrect
  case AlreadyGuessed
  case GameWon
  case GameLost