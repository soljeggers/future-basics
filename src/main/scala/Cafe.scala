import scala.concurrent.future
import scala.concurrent.Future
import scala.util.{Random, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.control.NonFatal


type CoffeeBeans = String
type GroundCoffee = String
type Milk  = String
type FrothedMilk = String
type Espresso = String
type Cappuccino = String

case class GrindingException(msg: String) extends Exception(msg)

case class FrothingException(msg: String) extends Exception(msg)

case class WaterBoilingException(msg: String) extends Exception(msg)

case class BrewingException(msg: String) extends Exception(msg)

case class Water(temprature: Int)

object Cafe  {


  def grind (beans: CoffeeBeans) : Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water) : Future[Water] = Future {
    println("heating the water now")
    Thread.sleep(Random.nextInt(2000))
    println("hot, it's hot!")
    water.copy(temprature = 85)
  }

  def frothMilk(milk: Milk) : Future[FrothedMilk] = Future {
    println("milk frothing system engaged")
    Thread.sleep(Random.nextInt(2000))
    println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water) : Future[Espresso] = Future{
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    println("It's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk) : Cappuccino = "cappuccino"



  def prepareCappucinnoSequentially() : Future[Cappuccino] = {
    for {
      ground <- grind("arabica beans")
      water <- heatWater(Water(25))
      espresso <- brew(ground, water)
      foam <- frothMilk("milk")
    } yield combine(espresso, foam)
  }
}

Cafe.prepareCappucinnoSequentially() map {
  c =>
    println(c)
} recover {
  case NonFatal(e) => println(s"Didn't make cappucino $e")
}