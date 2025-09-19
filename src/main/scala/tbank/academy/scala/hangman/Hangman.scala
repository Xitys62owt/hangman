package tbank.academy.scala.hangman

import tbank.academy.scala.hangman.error.DomainError

object Hangman {

  // main для scala 2.13, если вы используете scala 3, то перепишите этот main на main с парсингом аргументов консоли как аргументов функции (нововведение scala 3)
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
  }

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
  def hangmanTest(wordToGuess: String, guess: String): Either[DomainError, String] = ???

}
