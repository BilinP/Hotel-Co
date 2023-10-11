package com.hotelco.entities;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class Password{
    private String salt;

    public Password(String saltInput){
        salt=saltInput;
    }

    public Password(){
        SecureRandom rand=new SecureRandom();
        byte[]saltByte= new byte[16];
        rand.nextBytes(saltByte);
        salt = Base64.getEncoder().encodeToString(saltByte);
    }



    public String encrypt(String pass)throws NoSuchAlgorithmException{
    
        byte[]saltPass= genSaltWithPass(pass.getBytes());
      
        MessageDigest md= MessageDigest.getInstance("SHA-256"); 
        byte[] messageDigest= md.digest(saltPass);
        
        BigInteger bigInt= new BigInteger(1, messageDigest);

        return bigInt.toString(16);
    }


    
    public boolean verify(String inputPassword,String hashedPassword)throws NoSuchAlgorithmException{
        BigInteger bigInt= new BigInteger(hashedPassword,16);
        byte[] hashedPasswordBytes = bigInt.toByteArray();
        
        if (hashedPasswordBytes.length > 16 && hashedPasswordBytes[0] == 0) {
           byte[] temp = new byte[hashedPasswordBytes.length - 1];
           System.arraycopy(hashedPasswordBytes, 1, temp, 0, temp.length);
        hashedPasswordBytes = temp;
        }
        

        byte[] saltByte =Base64.getDecoder().decode(salt);
        
        byte[] nonHashedInputPassword = new byte[inputPassword.getBytes().length+16];
        System.arraycopy(saltByte, 0, nonHashedInputPassword, 0, 16);
        System.arraycopy(inputPassword.getBytes(), 0, nonHashedInputPassword, 16, inputPassword.getBytes().length);
       
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPasswordToVerify = md.digest(nonHashedInputPassword);
       
        for (int i = 16; i < hashedPasswordToVerify.length; i++) {
            if (hashedPasswordBytes[i] != hashedPasswordToVerify[i]) {
                return false; 
            }
        }
        return true;
    }

    private byte[] genSaltWithPass(byte[] pass){
        byte[] saltByte = Base64.getDecoder().decode(salt);
        byte[]result = new byte[pass.length+16];
        System.arraycopy(saltByte, 0, result, 0, 16);
        System.arraycopy(pass, 0, result, 16, pass.length);
        return result;
    }

    public String getSalt(){
        return this.salt;
    }

    

    /* 
     *     try{
            Password pass = new Password();
    
            String orignalPassword = "Password";
    
        
            String hashedPassword = pass.encrypt(originalPassword);
    
            String inputPassword = "Password";
    
            
            boolean isPasswordValid = pass.verify(inputPassword, hashedPassword);
    
            if (isPasswordValid) {
                System.out.println("Password is valid!");
            } else {
                System.out.println("Password is not valid!");
            }
            }catch (NoSuchAlgorithmException e) {
                System.out.println("Exception thrown for incorrect algorithm: " + e);
            }
     * 
     * 
    */
    
}
