package utils

import cats.effect.IO
import cats.effect.kernel.Resource

import scala.io.Source

object FileUtils {

  def file(path: String): Resource[IO,Source] =
    Resource.make(IO(Source.fromFile(path)))(s => IO(s.close()))

  def readFile(path: String): IO[List[String]] =
    file(path).use(s => IO(s.getLines().toList))
}
