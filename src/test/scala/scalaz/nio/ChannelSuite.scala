package scalaz.nio

import scalaz.nio.channels.AsynchronousSocketChannel
import scalaz.zio.IO
import scalaz.zio.RTS
import testz.{ Harness, assert }

object ChannelSuite extends RTS {

  def tests[T](harness: Harness[T]): T = {
    import harness._
    section(
      test("read/write") { () =>
        val testProgram: IO[Exception, Boolean] = for {
          src          <- Buffer.byte(3)
          sink         <- Buffer.byte(3)
          channel      <- AsynchronousSocketChannel()
          nSrc         <- channel.write(src)
          nSink        <- channel.read(sink)
        } yield nSrc == nSink

        assert(unsafeRun(testProgram))
      }
    )
  }
}
