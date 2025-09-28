package tbank.academy.scala.hangman.dictionary

import scala.util.Random

import tbank.academy.scala.hangman.core.{CategoryName, Difficulty}
import tbank.academy.scala.hangman.error.DomainError

case class WordWithHint(word: String, hint: String)

case class Dictionary(
    wordsByCategory: Map[CategoryName, Map[Difficulty, List[WordWithHint]]]
) {
  private def getRandomCategory(): Either[DomainError, CategoryName] = {
    val categories = getCategories()
    if (categories.isEmpty) {
      Left(DomainError.NoCategoriesAvailable)
    } else {
      Right(categories(Random.nextInt(categories.size)))
    }
  }

  def getCategories(): List[CategoryName] =
    CategoryName.values.toList.filter(wordsByCategory.contains)

  def getDifficulties(category: Option[CategoryName] = None): List[Difficulty] = {
    category match {
      case Some(cat) =>
        Difficulty.values.toList.filter(d => wordsByCategory.get(cat).exists(_.contains(d)))
      case None =>
        Difficulty.values.toList.filter(d => wordsByCategory.exists(_._2.contains(d)))
    }
  }

  def getRandomWord(
      category: Option[CategoryName] = None,
      difficulty: Option[Difficulty] = None
  ): Either[DomainError, WordWithHint] = {
    getRandomCategory() match {
      case Left(error)           => Left(error)
      case Right(randomCategory) =>
        val selectedCategory = category.getOrElse(randomCategory)

        val difficulties = getDifficulties(Some(selectedCategory))
        if (difficulties.isEmpty) {
          Left(DomainError.NoDifficultiesAvailable)
        } else {
          val selectedDifficulty = difficulty.getOrElse {
            difficulties(Random.nextInt(difficulties.size))
          }

          wordsByCategory
            .get(selectedCategory)
            .flatMap(_.get(selectedDifficulty))
            .flatMap(words =>
              if (words.isEmpty) then None
              else Some(words(Random.nextInt(words.size)))
            ) match {
            case Some(word) => Right(word)
            case None       => Left(DomainError.NoWordsAvailable)
          }
        }
    }
  }
}

object Dictionary {
  def loadFromWordCategories(): Dictionary = {
    val wordsByCategory = CategoryName.values.flatMap { category =>
      val categoryFileName =
        s"src/main/scala/tbank/academy/scala/hangman/dictionary/wordCategories/${CategoryName.toStringEng(category)}.txt"
      scala.util.Try {
        val source = scala.io.Source.fromFile(categoryFileName, "UTF-8")
        try {
          val content         = source.mkString
          val difficultiesMap = parseCategoryFile(content)
          Some(category -> difficultiesMap)
        } finally {
          source.close()
        }
      }.getOrElse(None)
    }.toMap

    Dictionary(wordsByCategory)
  }

  def parseCategoryFile(content: String): Map[Difficulty, List[WordWithHint]] = {
    val lines       = content.split("\n").map(_.trim).filter(_.nonEmpty)
    val (_, result) = lines.foldLeft((Option.empty[Difficulty], Map.empty[Difficulty, List[WordWithHint]])) {
      case ((currentDifficulty, acc), line) =>
        if (line.endsWith(":")) {
          val difficultyName = line.dropRight(1).trim
          Difficulty.values.find(d => Difficulty.toStringEng(d) == difficultyName) match {
            case Some(difficulty) => (Some(difficulty), acc)
            case None             => (currentDifficulty, acc)
          }
        } else {
          currentDifficulty match {
            case Some(difficulty) =>
              val parts    = line.split(";", 2)
              val wordHint = if (parts.length == 2) {
                WordWithHint(parts(0).trim, parts(1).trim)
              } else {
                WordWithHint(line, "")
              }
              val words = acc.getOrElse(difficulty, Nil)
              (currentDifficulty, acc + (difficulty -> (wordHint :: words)))
            case None =>
              (currentDifficulty, acc)
          }
        }
    }

    result.map { case (difficulty, words) =>
      difficulty -> words.reverse
    }
  }
}
