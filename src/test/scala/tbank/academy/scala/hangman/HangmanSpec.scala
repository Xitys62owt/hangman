package tbank.academy.scala.hangman

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import tbank.academy.scala.hangman.Hangman.hangmanTest
import tbank.academy.scala.hangman.core.{Difficulty, GameEngine, GameState, GuessResult}
import tbank.academy.scala.hangman.dictionary.{Dictionary, WordWithHint}
import tbank.academy.scala.hangman.visual.Visualizer
import tbank.academy.scala.hangman.error.DomainError
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class HangmanSpec extends AnyFlatSpec with Matchers {

  // Неинтерактивный
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

  it should "Обрабатывать слова с дефисами и подчеркиваниями" in {
    hangmanTest("Шри-Ланка_", "шриланка") shouldBe Right("Шри-Ланка_;POS")
  }

  it should "Возвращать ошибку для невалидных символов" in {
    hangmanTest("слово", "а1б") shouldBe Left(DomainError.InvalidLetter)
  }

  // Интерактивный

  // Тесты для GameEngine
  "GameEngine" should "Корректно обрабатывать правильные буквы" in {
    val state              = GameEngine.initGame("тест", 6, "подсказка")
    val (newState, result) = GameEngine.makeGuess(state, 'т')

    val _ = result shouldBe GuessResult.Correct
    val _ = newState.guessedLetters should contain('т')
    val _ = newState.attempts shouldBe 0
  }

  it should "Корректно обрабатывать неправильные буквы" in {
    val state              = GameEngine.initGame("тест", 6, "подсказка")
    val (newState, result) = GameEngine.makeGuess(state, 'а')

    val _ = result shouldBe GuessResult.Incorrect
    val _ = newState.guessedLetters should contain('а')
    val _ = newState.attempts shouldBe 1
  }

  it should "Не засчитывать повторные попытки неправильных букв" in {
    val state            = GameEngine.initGame("тест", 6, "подсказка")
    val (state1, _)      = GameEngine.makeGuess(state, 'а')
    val (state2, result) = GameEngine.makeGuess(state1, 'а')

    val _ = result shouldBe GuessResult.AlreadyGuessed
    val _ = state2.attempts shouldBe 1
  }

  it should "Не засчитывать повторные попытки правильных букв" in {
    val state            = GameEngine.initGame("тест", 6, "подсказка")
    val (state1, _)      = GameEngine.makeGuess(state, 'т')
    val (state2, result) = GameEngine.makeGuess(state1, 'т')

    val _ = result shouldBe GuessResult.AlreadyGuessed
    val _ = state2.attempts shouldBe 0
  }

  it should "Быть нечувствительным к регистру" in {
    val state              = GameEngine.initGame("Тест", 6, "подсказка")
    val (newState, result) = GameEngine.makeGuess(state, 'Т')

    val _ = result shouldBe GuessResult.Correct
    val _ = newState.guessedLetters should contain('т')
  }

  it should "Определять победу" in {
    val state                = GameEngine.initGame("тест", 6, "подсказка")
    val (state1, _)          = GameEngine.makeGuess(state, 'т')
    val (state2, _)          = GameEngine.makeGuess(state1, 'е')
    val (finalState, result) = GameEngine.makeGuess(state2, 'с')

    val _ = result shouldBe GuessResult.GameWon
    val _ = finalState.isWon shouldBe true
  }

  it should "Определять поражение" in {
    val state                = GameEngine.initGame("тест", 2, "подсказка")
    val (state1, _)          = GameEngine.makeGuess(state, 'а')
    val (finalState, result) = GameEngine.makeGuess(state1, 'б')

    val _ = result shouldBe GuessResult.GameLost
    val _ = finalState.isLost shouldBe true
  }

  // Тесты для GameState
  "GameState" should "Корректно маскировать слово" in {
    val state = GameState("тест", Set('т', 'е'), 0, 6)
    state.currentMaskedWord shouldBe "те*т"
  }

  it should "Показывать дефисы и подчеркивания" in {
    val state = GameState("те-с_т", Set('т', 'е'), 0, 6)
    state.currentMaskedWord shouldBe "те-*_т"
  }

  it should "Корректно определять победу" in {
    val state = GameState("те-ст", Set('т', 'е', 'с'), 0, 6)
    state.isWon shouldBe true
  }

  it should "Корректно определять поражение" in {
    val state = GameState("тест", Set.empty, 6, 6)
    state.isLost shouldBe true
  }

  it should "Корректно хранить подсказку" in {
    val state = GameState("тест", Set('т'), 1, 6, "подсказка")
    state.hint shouldBe "подсказка"
  }

  // Тесты для Dictionary
  "Dictionary" should "Загружать слова из файлов" in {
    val dictionary = Dictionary.loadFromWordCategories()
    dictionary.getCategories().nonEmpty shouldBe true
  }

  it should "Возвращать случайное слово" in {
    val dictionary = Dictionary.loadFromWordCategories()
    val result     = dictionary.getRandomWord()
    result.isRight shouldBe true
  }

  it should "Обрабатывать ошибки при загрузке" in {
    val dictionary = Dictionary(Map.empty)
    val result     = dictionary.getRandomWord()
    result.isLeft shouldBe true
  }

  it should "Правильно парсить файлы со словами" in {
    val content =
      """Noob:
        |слово1;подсказка1
        |слово2;подсказка2
        |
        |Medium:
        |слово_3;подсказка3
        |""".stripMargin

    val map = Dictionary.parseCategoryFile(content)
    val _   = map.keySet should contain(Difficulty.Noob)
    val _   = map.keySet should contain(Difficulty.Medium)
    val _   = map(Difficulty.Noob) should contain(WordWithHint("слово1", "подсказка1"))
    val _   = map(Difficulty.Noob) should contain(WordWithHint("слово2", "подсказка2"))
    val _   = map(Difficulty.Medium) should contain(WordWithHint("слово_3", "подсказка3"))
  }

  // Тесты для Visualizer
  "Visualizer" should "Рисовать картинку и показывать количество ошибок" in {
    val visualizer = new Visualizer(maxAttempts = 6)
    val outStream  = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outStream)) {
      visualizer.drawHangman(2)
    }
    val output = outStream.toString("UTF-8")
    val _      = output should include("Ошибок: 2 из 6")
    val _      = output should include("O")
  }

  it should "Рисовать начальное состояние" in {
    val visualizer = new Visualizer(maxAttempts = 6)
    val outStream  = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outStream)) {
      visualizer.drawStartState()
    }
    val output = outStream.toString("UTF-8")
    output should include("Ошибок: 0 из 6")
  }

  it should "Показывать замаскированное слово" in {
    val visualizer = new Visualizer(maxAttempts = 6)
    val outStream  = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outStream)) {
      visualizer.drawWordState("т*ст".toSeq)
    }
    val output = outStream.toString("UTF-8")
    output should include("Слово: т * с т")
  }
}
