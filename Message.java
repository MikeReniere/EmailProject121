public class Message {

    private String to;
    private String from;
    private String message="";

    Message(){

    }

    String getMessage(){
        return message;
    }
    String getTo(){
        return to;
    }
    String getFrom(){
        return from;
    }
    void setMessage(String _message) {
        message = _message;
    }
    void setTo(String _to){
        to = _to;
    }
    void setFrom(String _from){
        from = _from;
    }
}
