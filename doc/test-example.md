# Пример работы игры "Виселица" в не интерактивном режиме

## Запуск

```shell
scala-cli run Hangman.scala -M hangmanTest -- arg1 arg2
# arg1 - "загаданное" слово
# arg2 - "буквы для отгадывания", имитирует пользовательский ввод
# Слова в arg1 и arg2 будут состоять из одинакового количества знаков.
```

## Вывод

Вывод должен быть направлен в STDOUT в формате: `итог_угадывания;РЕЗУЛЬТАТ`

## Примеры

### Слово не угадано

```shell
scala-cli run Hangman.scala -M hangmanTest -- волокно толкн
# Вывод: '*олокно;NEG'
```

```shell
scala-cli run Hangman.scala -M hangmanTest --  волокно бархло
# Вывод: '*оло**о;NEG'
```
![Слово не угадано](NEG-result.png)

### Слово угадано верно

```shell
scala-cli run Hangman.scala -M hangmanTest --  окно окн
# Вывод: 'окно;POS'
```
![Слово угадано верно](POS-result.png)
