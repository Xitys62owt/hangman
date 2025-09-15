package tbank.academy.scala.hangman

import org.scalatest.funsuite.AnyFunSuite
import tbank.academy.scala.hangman.Hangman.*

class HangmanSpec extends AnyFunSuite {

  test("Угадана одна буква, остальные не совпадают") {
    assert(hangmanTest("волокно", "толокно") == "*олокно;NEG")
  }

  test("Угаданы все буквы") {
    assert(hangmanTest("привет", "привет") == "привет;POS")
  }

  test("Не угаданы все буквы, кроме последней") {
    assert(hangmanTest("волокно", "барахло") == "******о;NEG")
  }

  test("Слово частично угадано") {
    assert(hangmanTest("тестирование", "теееиировие") == "те*еи*ровие;NEG")
  }

  test("Пустые строки (допустимые по условию)") {
    assert(hangmanTest("", "") == ";POS")
  }

  test("Бросает исключение, если длина слов разная") {
    intercept[IllegalArgumentException] {
      hangmanTest("короткое", "длинноеслово")
    }
  }
}
