package day3

import cats.implicits._

import cats.effect.{ExitCode, IO, IOApp}
import day3.Part1.binToDec
import utils.FileUtils.readFile

object Part2 extends IOApp {

  sealed trait PrefixTree
  case class Head(children: List[Node]) extends PrefixTree
  case class Node(value: Char, children: List[Node]) extends PrefixTree


  override def run(args: List[String]): IO[ExitCode] = for {
    lines <- readFile("data/day3.txt")
  } yield ExitCode.Success
}
