
package za.ac.tut.encryption;


public class MessageEncryption {

    public String encryptMsg(String plainMsg) {
        
        StringBuilder encryptedMsg = new StringBuilder();
        int shift = 3;
        
         for (int i = 0; i < plainMsg.length(); i++) {
            
               char c = plainMsg.charAt(i);
               if(Character.isLetter(c)) {
                char shifted = (char) (c + shift);

                if(Character.isLowerCase(c) && shifted > 'z'){
                    shifted -= 26;
                }else if(Character.isUpperCase(c) && shifted > 'Z'){
                    shifted -= 26;
                }
                
               encryptedMsg.append(shifted);
            } else {
                encryptedMsg.append(c); // Non-alphabet characters are not encrypted
            }
        }

        return encryptedMsg.toString();
    }
}

    
    
    

