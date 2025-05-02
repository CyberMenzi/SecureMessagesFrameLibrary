
package za.ac.tut.message;


public class Message {
    
    private String plainMsg;

    public Message(String plainMsg) {
        this.plainMsg = plainMsg;
    }

    public String getPlainMsg() {
        return plainMsg;
    }

    public void setPlainMsg(String plainMsg) {
        this.plainMsg = plainMsg;
    }

    @Override
    public String toString() {
        return "Message{" + "plainMsg=" + plainMsg + '}';
    }
    
  
}
