import java.util.LinkedList;

class Mailbox extends LinkedList<Message> {
    private String name;

    Mailbox(String newName){
        name = newName;
    }
    String getName(){
        return name;
    }
}