import java.io.{BufferedReader, InputStreamReader, PrintStream}
import java.net.{ServerSocket, Socket}
import java.util.concurrent.ConcurrentHashMap
import java.io.ObjectInputStream

import mymessage.MyMessage

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object AndroidServer extends App {
  case class User(name: String, sock: Socket, in: ObjectInputStream, out: PrintStream)
  val users = new ConcurrentHashMap[String, User]().asScala
  var count= 0
  var j = 0
  var obj:Object = _
  println("HI")


  Future { checkConnections() }
  while(true){
    for((name, user) <- users){
      doChat(user)
    }
    Thread.sleep(200)
  }

  def checkConnections(): Unit = {
    val ss = new ServerSocket(4000)
    while(true){
      val sock = ss.accept()
      //val in = new BufferedReader(new InputStreamReader(sock.getInputStream))
      val out = new PrintStream(sock.getOutputStream)
      val in = new ObjectInputStream(sock.getInputStream)
      Future{
        //out.println("What is your name")
        val name=  sock.getInetAddress.toString
        println("Si e' connesso l'utente "+ name)
        val user= User(name, sock, in, out)
        users += name-> user
        count= count +1
      }
    }
  }

  /*def nonblockingRead(in: ObjectInputStream, sock: Socket): Option[Int] = {
    if(sock.getInputStream.available() >0) {
      obj = in.readObject()
      println(obj)
      //Thread.sleep(1000)
    }
    if(obj != null)
      Some(obj.asInstanceOf[MyMessage].tipo)
    else None
  }*/

  def nonblockingRead(in: ObjectInputStream, sock: Socket): Option[MyMessage] = {
    if(sock.getInputStream.available() >0) {
      obj = in.readObject()
      println(obj)
      //Thread.sleep(1000)
    }
    if(obj != null){
      Some(obj.asInstanceOf[MyMessage])
    }
    else None
  }


  def doChat(user: User): Unit = {
    nonblockingRead(user.in, user.sock).foreach { input =>
      if(input.tipo == 0){
        user.sock.close()
        users -= user.name
        println("GONE :(")
        //obj = null
      } else {
        //for((n, u) <- users){
        //u.out.println(user.name+" : "+input)
        //u.out.println(j)
        //}
        println(user.name+" ha aggiornato il valore in: "+input.message)

        /*if(input == 1){

        }*/
        //j +=1
        //obj = null
      }
      obj = null
    }
  }
}

