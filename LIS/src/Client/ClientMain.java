/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.LibraryInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 *
 * @author alvin 
 */
public class ClientMain {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException  {
        // TODO code application logic here
    
    
    StudentLibrary x = new StudentLibrary();
    x.StudentLibrary();
          
    }
  
    
}
