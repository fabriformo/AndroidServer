package mymessage

object MainClient extends App {
  val a = new ClientThread
  a.start()
  Thread.sleep(5000)
  a.finisci()
}
