package day3

import cats.effect.{ExitCode, IO, IOApp}
import utils.FileUtils.readFile

object Part1 extends IOApp {
  case class BitCount(high: Int, low: Int)

  def binToDec(xs: List[Int]): Int =
    xs.reverse.zipWithIndex.foldLeft(0) {
      case (sum, (bit, idx)) => (sum + (bit * math.pow(2, idx))).toInt
    }

  override def run(args: List[String]): IO[ExitCode] = for {
    lines   <- readFile("data/day3.txt")
    init    =  (0 until lines.head.length).toList.map(_ => BitCount(0, 0))
    sums    =  lines.foldLeft(init) { (acc,line) =>
      acc.zipWithIndex.map {
        case (bc, idx) => line.charAt(idx) match {
          case '0' => bc.copy(low = bc.low + 1)
          case '1' => bc.copy(high = bc.high + 1)
        }
      }
    }
    gamma   =  sums.map(bc => if(bc.high > bc.low) 1 else 0)
    epsilon =  sums.map(bc => if(bc.low > bc.high) 1 else 0)
    g_dec   =  binToDec(gamma)
    e_dec   =  binToDec(epsilon)
    _       <- IO.println(s"Gamma: $g_dec")
    _       <- IO.println(s"Epsilon: $e_dec")
    _       <- IO.println(g_dec * e_dec)
  } yield ExitCode.Success
}
