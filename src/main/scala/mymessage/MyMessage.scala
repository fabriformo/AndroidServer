package mymessage

class MyMessage(_tipo:Int, _message:String, _topic:Int) extends Serializable {
  def tipo: Int = _tipo //0 disconnessione 1 iscrizione 2 aggiornamento 3 disiscrizione
  def message: String = _message
  def topic: Int = _topic //0 nessuno 1 temperatura 2 pressione 3 entrambi
}
