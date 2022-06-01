import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;
import static java.lang.System.out;
public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public Client(Socket socket)
    {
        try{
            this.socket=socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        }
        catch (IOException e)
        {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void writeMessage(){
        try{
            out.println("Podaj wiadomość");
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected())
            {

                String time, mess = scanner.nextLine();
                if(mess.equals("End"))
                {
                    closeEverything(socket, bufferedWriter, bufferedReader);
                    System.exit(0);
                }
                out.println("Podaj godzine wysyłki");
                while(true) {
                    LocalTime t;
                    time = scanner.nextLine();
                    try{
                        t = LocalTime.parse(time);
                    }
                    catch (Exception e)
                    {
                        out.println("Błąd, spróbuj ponownie");
                        continue;
                    }
                    long res = t.toSecondOfDay() - LocalTime.now().toSecondOfDay();
                    if(res<0)
                    {
                        out.println("Błąd, spróbuj ponownie");
                        continue;
                    }
                    break;
                }
                bufferedWriter.write(mess);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.write(time);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }
        }
        catch (IOException e)
        {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void showMessages()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected())
                {
                    try {
                        message=bufferedReader.readLine();
                        if(message==null)
                        {
                            break;
                        }
                        out.println(message);
                    }
                    catch (IOException e)
                    {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                    }

                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try{
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8001);
            Client client = new Client(socket);
            client.showMessages();
            client.writeMessage();
        }catch(Exception e){
            out.println("Server is off");
            System.exit(0);
        }

    }

}
