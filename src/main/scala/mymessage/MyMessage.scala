package mymessage

class MyMessage(_tipo:Int, _message:String, _topic:Int) extends Serializable {
  def tipo: Int = _tipo //0 disconnessione 1 iscrizione 2 aggiornamento
  def message: String = _message
  def topic: Int = _topic
}
