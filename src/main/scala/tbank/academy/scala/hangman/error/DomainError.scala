package tbank.academy.scala.hangman.error

enum DomainError:
  case InvalidLetter
  case NoWordsAvailable
  case NoCategoriesAvailable
  case NoDifficultiesAvailable
