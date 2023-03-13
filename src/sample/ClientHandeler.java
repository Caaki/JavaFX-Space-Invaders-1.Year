package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.SocketHandler;

public class ClientHandeler implements Runnable {

    public static ArrayList<ClientHandeler> korisnici = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public ClientHandeler(Socket socket){
        try {
            this.socket = socket;
            bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = bufferedReader.readLine();
            korisnici.add(this);
            brodcastMessage("Server: " + username + " je usao u cet");
        }catch (IOException e){
            zatvoriSve(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
        String porukaOdKlijenta;

        while(socket.isConnected()) {
            try {
                porukaOdKlijenta = bufferedReader.readLine();
                brodcastMessage(porukaOdKlijenta);
            } catch (IOException e) {
                zatvoriSve(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void brodcastMessage(String poruka){

        for (ClientHandeler clientHandeler: korisnici){
            try{
                if (clientHandeler.username!=null) {
                    if (!clientHandeler.username.equals(username)) {
                        clientHandeler.bufferedWriter.write(poruka);
                        clientHandeler.bufferedWriter.newLine();
                        clientHandeler.bufferedWriter.flush();
                    }
                }
            }catch (IOException e){
                zatvoriSve(socket, bufferedReader, bufferedWriter);

            }
        }
    }
    public void ukloniKorisnika(){
        korisnici.remove(this);
        brodcastMessage("Server: " +this.username+ " je napustio chat");
    }
    public void zatvoriSve(Socket socket , BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        ukloniKorisnika();
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }if (bufferedWriter != null){
                bufferedWriter.close();
            }if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
