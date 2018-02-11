package mymessage

import java.io._
import java.net._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ClientThread extends Thread {

  var socket: Socket=_
  //modifica
  var out: ObjectOutputStream= _
  var in: BufferedReader=null
  var stopped= false
  var i: Int = 1

  override def run(): Unit = {
    val serverAddr = InetAddress.getByName("192.168.0.111")
    socket = new Socket(serverAddr,4000)
    out = new ObjectOutputStream(socket.getOutputStream)
    Future {
      while (!stopped) {
        //out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream)), true)
        //out.println("a")

        //in = new BufferedReader(new InputStreamReader(socket.getInputStream))
        //notifyMessage(in.readLine())
        //out.writeObject(new MyMessage(i,"c",0))
        //i+=1
        Thread.sleep(500)
      }
      out.writeObject(new MyMessage(0,"",0))
    }
  }


  def invia(m:MyMessage): Unit = {
    out.writeObject(m)
  }


  def finisci(): Unit ={
    stopped=true
    Thread.sleep(600)
    socket.close()
  }

}

