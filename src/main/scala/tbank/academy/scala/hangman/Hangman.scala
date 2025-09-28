package tbank.academy.scala.hangman

import tbank.academy.scala.hangman.error.DomainError
import tbank.academy.scala.hangman.dictionary.Dictionary
import tbank.academy.scala.hangman.runner.{InteractiveRunner, TestRunner}

object Hangman {

  // main для scala 2.13, если вы используете scala 3, то перепишите этот main на main с парсингом аргументов консоли как аргументов функции (нововведение scala 3)
  def main(args: Array[String]): Unit = {
    args.length match {
      case 0 =>
        val dictionary = Dictionary.loadFromWordCategories()
        InteractiveRunner.run(dictionary)
      case 2 =>
        val wordToGuess = args(0)
        val guesses     = args(1)
        val result      = hangmanTest(wordToGuess, guesses)
        result match {
          case Right(output) => println(output)
          case Left(error)   => println(s"Error: $error")
        }
      case _ =>
        println("Введите 2 параметра для неинтерактивного режима и 0 для интерактивного")
    }
  }

  private def isCyrillicChar(c: Char): Boolean = {
    val lower = c.toLower
    (lower >= 'а' && lower <= 'я') || lower == 'ё'
  }

  private def isValidChar(c: Char): Boolean =
    isCyrillicChar(c) || c == '-' || c == '_'

  /** NonInteractive mode. Это функция которую вам необходимо реализовать, она необходима для наших примитивных тестов
    * не интерактивного режима вашего приложения. Реализуйте эту функцию через вашу "универсальную" функцию с движком
    * игры (которую вы будете использовать в main)
    *
    * @param wordToGuess
    *   загаданное слово
    * @param guess
    *   буквы, которые вводит пользователь
    * @return
    *   - если угадано слово полностью, то возвращается "угаданное слово;POS", иначе "частично у***анное слово;NEG"
    *   - если произошла ошибка, то возвращается Left(DomainError)
    */
  def hangmanTest(wordToGuess: String, guess: String): Either[DomainError, String] = {
    val wordValid  = wordToGuess.forall(isValidChar)
    val guessValid = guess.forall(isCyrillicChar)
    if (!wordValid || !guessValid) {
      Left(DomainError.InvalidLetter)
    } else {
      TestRunner.runTest(wordToGuess, guess)
    }
  }
}
