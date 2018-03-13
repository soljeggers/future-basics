import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random
import scala.util.control.NonFatal

case class GrindingException(msg: String) extends Exception(msg)

case class FrothingException(msg: String) extends Exception(msg)

case class WaterBoilingException(msg: String) extends Exception(msg)

case class BrewingException(msg: String) extends Exception(msg)

case class Water(temprature: Int)

object Cafe extends App {

  type CoffeeBeans = String
  type GroundCoffee = String
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
//        Thread
//        .sleep(Random
//               .nextInt(0))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("heating the water now")
//    Thread
//    .sleep(Random
//           .nextInt(0))
    println("hot, it's hot!")
    water
    .copy(temprature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("milk frothing system engaged")
//    Thread
//    .sleep(Random
//           .nextInt(0))
    println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("happy brewing :)")
//    Thread
//    .sleep(Random
//           .nextInt(0))
    println("It's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  def prepareCappucinoSequentially(): Future[Cappuccino] = {

    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(85))
    val frothedMilk = frothMilk("milk")

    for {
      ground <- groundCoffee
      water <- heatedWater
      espresso <- brew(ground, water)
      foam <- frothedMilk
    } yield {
      combine(espresso, foam)
    }
  }
  Cafe
  .prepareCappucinoSequentially map {
    c =>
      println(c)
  } recover {
    case NonFatal(e) => println(s"Didn't make cappucino $e")
  }
}

