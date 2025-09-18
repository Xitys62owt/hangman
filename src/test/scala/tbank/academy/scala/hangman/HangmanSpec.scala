package tbank.academy.scala.hangman

import org.scalatest.funsuite.AnyFunSuite
import tbank.academy.scala.hangman.Hangman.*

class HangmanSpec extends AnyFunSuite {

  test("Угаданы все буквы, кроме одной") {
    assert(hangmanTest("волокно", "толкн") == Right("*олокно;NEG"))
  }

  test("Угаданы все буквы") {
    assert(hangmanTest("привет", "привет") == Right("привет;POS"))
  }

    test("Угаданы все буквы (не обязательно в том же порядке)") {
        assert(hangmanTest("привет", "ивтепр") == Right("привет;POS"))
    }

  test("Не угаданы все буквы, кроме последней") {
    assert(hangmanTest("волокно", "бархло") == Right("*оло**о;NEG"))
  }

  test("Слово частично угадано") {
    assert(hangmanTest("тестирование", "теиров") == Right("те*тиров**ие;NEG"))
  }

  test("Пустые строки (допустимые по условию)") {
    assert(hangmanTest("", "") == Right(";POS"))
  }

  test("Бросает исключение, если вводится невалидный символ") {
    assert(hangmanTest("короткое", "длиное/слово").isLeft)
  }
}
