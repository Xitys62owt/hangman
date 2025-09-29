package tbank.academy.scala.hangman.core

enum Difficulty:
  case Noob
  case Easy
  case Medium
  case Hard
  case Pain
  case Impossible

enum CategoryName:
  case Animals
  case Plants
  case CountriesAndCities
  case GeographicalNames
  case ScienceAndTechnology

object Difficulty:
  def toStringRus(difficulty: Difficulty): String = difficulty match
    case Noob       => "Новичок"
    case Easy       => "Легкий"
    case Medium     => "Средний"
    case Hard       => "Сложный"
    case Pain       => "Болезненный"
    case Impossible => "Невозможный"

  def toStringEng(difficulty: Difficulty): String = difficulty match
    case Noob       => "Noob"
    case Easy       => "Easy"
    case Medium     => "Medium"
    case Hard       => "Hard"
    case Pain       => "Pain"
    case Impossible => "Impossible"

object CategoryName:
  def toStringRus(category: CategoryName): String = category match
    case Animals              => "Животные"
    case Plants               => "Растения"
    case CountriesAndCities   => "Страны и города"
    case GeographicalNames    => "Географические названия"
    case ScienceAndTechnology => "Наука и технологии"

  def toStringEng(category: CategoryName): String = category match
    case Animals              => "Animals"
    case Plants               => "Plants"
    case CountriesAndCities   => "CountriesAndCities"
    case GeographicalNames    => "GeographicalNames"
    case ScienceAndTechnology => "ScienceAndTechnology"
