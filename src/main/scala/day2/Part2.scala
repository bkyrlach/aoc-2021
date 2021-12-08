package day2

import cats.data.State
import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp}
import day2.Part1.{Command, parseCommand}
import utils.FileUtils.readFile

object Part2 extends IOApp {
  case class SubState(aim: Int, horizontal: Int, depth: Int)

  def evalCommand(command: Command): State[SubState,Unit] = command match {
    case Part1.Forward(magnitude) => for {
      _ <- State.modify[SubState](st => st.copy(horizontal = st.horizontal + magnitude))
      _ <- State.modify[SubState](st => st.copy(depth = st.depth + (st.aim * magnitude)))
    } yield ()
    case Part1.Down(magnitude)    => State.modify[SubState](st => st.copy(aim = st.aim + magnitude))
    case Part1.Up(magnitude)      => State.modify[SubState](st => st.copy(aim = st.aim - magnitude))
  }

  def run(args: List[String]): IO[ExitCode] = for {
    lines    <- readFile("data/day2.txt")
    commands =  lines.map(parseCommand)
    pgm      =  commands.traverse(evalCommand)
    sub      =  pgm.runS(SubState(0,0,0)).value
    _        <- IO.println(sub)
    _        <- IO.println(sub.depth * sub.horizontal)
  } yield ExitCode.Success
}
