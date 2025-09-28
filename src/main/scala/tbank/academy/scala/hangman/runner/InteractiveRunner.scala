package tbank.academy.scala.hangman.runner

import tbank.academy.scala.hangman.core.{GameEngine, GameState, GuessResult, CategoryName, Difficulty}
import tbank.academy.scala.hangman.visual.Visualizer
import tbank.academy.scala.hangman.dictionary.Dictionary

object InteractiveRunner {
  private def isCyrillic(c: Char): Boolean = {
    c >= 'а' && c <= 'я' || c >= 'А' && c <= 'Я' || c == 'ё' || c == 'Ё'
  }

  def run(dictionary: Dictionary): Unit = {
    val categories = dictionary.getCategories()
    println("Выберите категорию:")
    for ((category, index) <- categories.zipWithIndex) {
      println(s"${index + 1}. ${CategoryName.toStringRus(category)}")
    }
    println("Введите номер категории (или Enter для случайной):")
    val categoryInput = scala.io.StdIn.readLine().trim
    val selectedCategory = if (categoryInput.isEmpty) None else {
      categoryInput.toIntOption match {
        case Some(index) if index >= 1 && index <= categories.length => Some(categories(index - 1))
        case _ => None
      }
    }

    val difficulties = Difficulty.values.toList
    println("\nВыберите сложность:")
    for ((difficulty, index) <- difficulties.zipWithIndex) {
      println(s"${index + 1}. ${Difficulty.toStringRus(difficulty)}")
    }
    println("Введите номер сложности (или Enter для случайной):")
    val difficultyInput = scala.io.StdIn.readLine().trim
    val selectedDifficulty = if (difficultyInput.isEmpty) None else {
      difficultyInput.toIntOption match {
        case Some(index) if index >= 1 && index <= difficulties.length => Some(difficulties(index - 1))
        case _ => None
      }
    }
    
    val word = dictionary.getRandomWord(selectedCategory, selectedDifficulty) match {
      case Right(w) => w
      case Left(error) => 
        println(s"Ошибка: не удалось выбрать слово. $error. Используется слово по умолчанию")
        "скала"
    }
    
    println("\nВведите количество попыток больше нуля (по умолчанию 6):")
    val maxAttemptsInput = scala.io.StdIn.readLine()
    val maxAttempts = if (maxAttemptsInput.isEmpty || maxAttemptsInput == "0") 6 else {
      maxAttemptsInput.toIntOption.getOrElse(6)
    }
    
    val visualizer = new Visualizer(maxAttempts)
    val initialState = GameEngine.initGame(word, maxAttempts)
    
    visualizer.drawStartState()
    
    def gameLoop(state: GameState): Unit = {
      if (!state.isGameOver) {
        val input = scala.io.StdIn.readLine("Введите букву: ").trim
        
        if (input.length == 1 && isCyrillic(input.head)) {
          val letter = input.head.toLower
          val (newState, result) = GameEngine.makeGuess(state, letter)
          val resultString = result match {
            case GuessResult.Correct => "Correct"
            case GuessResult.Incorrect => "Incorrect"
            case GuessResult.AlreadyGuessed => "AlreadyGuessed"
            case GuessResult.GameWon => "GameWon"
            case GuessResult.GameLost => "GameLost"
          }
          visualizer.showGuessResult(resultString, newState)
          gameLoop(newState)
        } else {
          println("Пожалуйста, введите одну букву русского алфавита")
          gameLoop(state)
        }
      }
    }
    
    gameLoop(initialState)
  }
}