package tbank.academy.scala.hangman

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import tbank.academy.scala.hangman.Hangman.hangmanTest

class HangmanSpec extends AnyFlatSpec {

  "HangmanSpec" should "Угаданы все буквы, кроме одной" in {
    hangmanTest("волокно", "толкн") shouldBe Right("*олокно;NEG")
  }

  it should "Угаданы все буквы" in {
    hangmanTest("привет", "привет") shouldBe Right("привет;POS")
  }

  it should "Угаданы все буквы (не обязательно в том же порядке)" in {
    hangmanTest("привет", "ивтепр") shouldBe Right("привет;POS")
  }

  it should "Не угаданы все буквы, кроме последней" in {
    hangmanTest("волокно", "бархло") shouldBe Right("*оло**о;NEG")
  }

  it should "Слово частично угадано" in {
    hangmanTest("тестирование", "теиров") shouldBe Right("те*тиров**ие;NEG")
  }

  it should "Пустые строки (допустимые по условию)" in {
    hangmanTest("", "") shouldBe Right(";POS")
  }

  it should "Бросает исключение, если вводится невалидный символ" in {
    hangmanTest("короткое", "длиное/слово").isLeft shouldBe true
  }
}
