package tbank.academy.scala.hangman.visual

import tbank.academy.scala.hangman.core.GameState

class Visualizer(maxAttempts: Int = 6) {
  private val hangmanParts = List(
    """   
       ||===========
       ||/         
       ||          
       ||           
       ||
       ||          
       ||\
       ||[][][][][][]""",
    """   
       ||===========
       ||/     |   
       ||      O   
       ||           
       ||
       ||          
       ||\
       ||[][][][][][]""",
    """   
       ||===========
       ||/     |   
       ||      O   
       ||     ( )     
       ||
       ||          
       ||\
       ||[][][][][][]""",
    """   
       ||===========
       ||/     |   
       ||      O   
       ||    /( )     
       ||
       ||          
       ||\
       ||[][][][][][]""",
    """   
       ||===========
       ||/     |   
       ||      O   
       ||    /( )\     
       ||
       ||          
       ||\
       ||[][][][][][]""",
    """   
       ||===========
       ||/     |   
       ||      O   
       ||    /( )\     
       ||     /
       ||          
       ||\
       ||[][][][][][]""",
    """   
       ||===========
       ||/     |   
       ||      O   
       ||    /( )\     
       ||     / \
       ||          
       ||\
       ||[][][][][][]""",
  )

  private def getScaledAttempts(attempts: Int): String = {
    val scaledAttempts = 
      if (maxAttempts == 6) {
        attempts
      } else {
        val ratio = attempts.toDouble / maxAttempts
        (ratio * 7).toInt.min(7)
      }
    
    hangmanParts(scaledAttempts.min(hangmanParts.size - 1))
  }

  def drawHangman(attempts: Int): Unit = {
    println("\n" + getScaledAttempts(attempts))
    println(s"Ошибок: $attempts из $maxAttempts")
  }

  def drawWordState(state: Seq[Char]): Unit = {
    println("\nСлово: " + state.mkString(" "))
  }

  def drawStartState(): Unit = {
    println("\n" + getScaledAttempts(0))
    println(s"Ошибок: 0 из $maxAttempts")
  }

  def showGuessResult(result: String, state: GameState): Unit = {
    drawHangman(state.attempts)
    drawWordState(state.currentMaskedWord)
    result match {
      case "Correct" => 
        println("\nПравильно!")
      case "Incorrect" => 
        println("\nНеправильно!")
      case "AlreadyGuessed" => 
        println("\nВы уже вводили эту букву")
      case "GameWon" => 
        println("\nПоздравляем! Вы выиграли!")
        println(s"Загаданное слово: ${state.word}")
      case "GameLost" => 
        println("\nК сожалению, вы проиграли")
        println(s"Загаданное слово: ${state.word}")
    }
  }
}