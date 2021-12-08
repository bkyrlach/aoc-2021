package day2

import cats.implicits._

import cats.data.State
import cats.effect.{ExitCode, IO, IOApp}
import utils.FileUtils.readFile

object Part1 extends IOApp {
  case class SubState(horizontal: Int, depth: Int)

  sealed trait Command
  case class Forward(magnitude: Int) extends Command
  case class Down(magnitude: Int) extends Command
  case class Up(magnitude: Int) extends Command

  val forward = "forward (\\d+)".r
  val down    = "down (\\d+)".r
  val up      = "up (\\d+)".r

  def parseCommand(commandStr: String): Command = commandStr match {
    case forward(m) => Forward(m.toInt)
    case down(m)    => Down(m.toInt)
    case up(m)      => Up(m.toInt)
  }

  def evalCommand(command: Command): State[SubState,Unit] = command match {
    case Forward(magnitude) => State.modify[SubState](st => st.copy(horizontal = st.horizontal + magnitude))
    case Down(magnitude)    => State.modify[SubState](st => st.copy(depth = st.depth + magnitude))
    case Up(magnitude)      => State.modify[SubState](st => st.copy(depth = st.depth - magnitude))
  }

  def run(args: List[String]): IO[ExitCode] = for {
    lines    <- readFile("data/day2.txt")
    commands =  lines.map(parseCommand)
    pgm      =  commands.traverse(evalCommand)
    sub      =  pgm.runS(SubState(0,0)).value
    _        <- IO.println(sub)
    _        <- IO.println(sub.depth * sub.horizontal)
  } yield ExitCode.Success
}
