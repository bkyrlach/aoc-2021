package day1

import cats.effect.{ExitCode, IO, IOApp}
import utils.FileUtils._

object Part1 extends IOApp {
  def run(args: List[String]): IO[ExitCode] = for {
    lines      <- readFile("data/day1.txt")
    numbers    =  lines.map(_.toInt)
    increments =  numbers.tail.foldLeft((numbers.head, 0)) { (acc, n) =>
      if(n > acc._1) (n, acc._2 + 1) else (n, acc._2)
    }
    _          <- IO.println(increments._2)
  } yield ExitCode.Success
}
