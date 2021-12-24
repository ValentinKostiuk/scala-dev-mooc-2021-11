import collections.task_collections._

capitalizeIgnoringASCII(List("Lorem", "ipsum", "dolor", "sit", "amet"))

capitalizeIgnoringASCII{
  List(
    "Оказывается",
    ",",
    "ЗвУк",
    "КЛАВИШЬ",
    "печатной",
    "Машинки",
    "не",
    "СТАЛ",
    "ограничивающим",
    "фактором"
  )
}

numbersToNumericString("1 2 3 4 5 6 7 8 9")


val deallerOne = List(Auto("Audi", "Q7"), Auto("BMW", "X5"), Auto("Mazda", "3"), Auto("Mazda", "8"))
val deallerTwo = List(Auto("Lincoln", "Navigator"), Auto("Audi", "Q7"), Auto("Toyota", "Tundra"), Auto("Mazda", "3"), Auto("Lada", "Baklajan"))

intersectionAuto(deallerOne, deallerTwo)
filterAllLeftDealerAutoWithoutRight(deallerOne, deallerTwo)
