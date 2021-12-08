package day1

import cats.effect.{ExitCode, IO, IOApp}
import utils.FileUtils.readFile

object Part2 extends IOApp {
  def run(args: List[String]): IO[ExitCode] = for {
    lines      <- readFile("data/day1.txt")
    numbers    =  lines.map(_.toInt)
    slices     =  slide(numbers)
    sums       =  slices.map(_.sum)
    increments =  sums.tail.foldLeft(sums.head -> 0){
      case ((prev, count), n) => if(n > prev) (n, count + 1) else (n, count)
    }
    _          <- IO.println(increments._2)
  } yield ExitCode.Success

  def slide(xs: List[Int]): List[List[Int]] = xs match {
    case a::b::c::t => (a::b::c::Nil)::slide(b::c::t)
    case _          => Nil
  }
}
