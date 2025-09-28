package tbank.academy.scala.hangman.error

sealed trait DomainError
object DomainError {
  case object InvalidLetter           extends DomainError
  case object NoWordsAvailable        extends DomainError
  case object NoCategoriesAvailable   extends DomainError
  case object NoDifficultiesAvailable extends DomainError
}
