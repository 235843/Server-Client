import java.io.*;
import java.util.*;
import java.net.*;
import java.time.*;


public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    ClientHandler(Socket socket)
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

    private void broadcastMessage(String s) {
        try{

            this.bufferedWriter.write(s);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        }
        catch (IOException e)
        {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }

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

    public void waitForDeadline(String time, String mess)
    {
        LocalTime timerer = LocalTime.parse(time);
        long res = timerer.toSecondOfDay() - LocalTime.now().toSecondOfDay();
        System.out.println(res);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                broadcastMessage(mess);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,res*1000);
    }

    @Override
    public void run() {
        String message, time;
        while (socket.isConnected())
        {
            try{
                message = bufferedReader.readLine();
                if(message==null)
                {
                    closeEverything(socket, bufferedWriter, bufferedReader);
                }
                System.out.println("message = " + message);
                time = bufferedReader.readLine();
                System.out.println("time = " + time);
                waitForDeadline(time,message);
            }
            catch (IOException e)
            {
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }
}
