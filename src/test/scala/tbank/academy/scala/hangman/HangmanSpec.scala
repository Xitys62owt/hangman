package tbank.academy.scala.hangman

import org.scalatest.funsuite.AnyFunSuite
import tbank.academy.scala.hangman.Hangman.*

class HangmanSpec extends AnyFunSuite {

  test("Угадана одна буква, остальные не совпадают") {
    assert(hangmanTest("волокно", "толокно") == Right("*олокно;NEG"))
  }

  test("Угаданы все буквы") {
    assert(hangmanTest("привет", "привет") == Right("привет;POS"))
  }

  test("Не угаданы все буквы, кроме последней") {
    assert(hangmanTest("волокно", "барахло") == Right("******о;NEG"))
  }

  test("Слово частично угадано") {
    assert(hangmanTest("тестирование", "теееиировие") == Right("те*еи*ровие;NEG"))
  }

  test("Пустые строки (допустимые по условию)") {
    assert(hangmanTest("", "") == Right(";POS"))
  }

  test("Бросает исключение, если длина слов разная") {
    assert(hangmanTest("короткое", "длинноеслово").isLeft)
  }
}
