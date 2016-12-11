package edu.upasna.cs478.prj4_atmpt1;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    private String th;
    int count =0;
    // X and Y is used to keep track of whether the first or second player wins
    static  int X =0;
    static int Y=0;

    // win_x and win_y are arrays that store the textviews/ positions that the players choose to play the game
    ArrayList<TextView> win_x = new ArrayList<TextView>();
    ArrayList<TextView> win_y = new ArrayList<TextView>();


    // Handler is created by UI Thread and associated with it
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (count <= 10) {

                // UI handler handles two messages one from each thread to perform the display
                switch (what) {

                    // Thread 1's Display Case
                    case BEGIN_1:
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Random rand1 = new Random();

                        // Choose a random textview and update display to X
                        TextView randomtext = tex.get(rand1.nextInt(tex.size()));
                        while (randomtext.getText() == "X" || randomtext.getText() == "O") {
                            randomtext = tex.get(rand1.nextInt(tex.size()));

                        }
                        randomtext.setText("X");

                        win_x.add(randomtext);
                        count++;

                        // Calling the method which checks for Winner of the game
                        Choose_result(win_x,win_y,"X");
                        Log.d("TAG","win_x ="+win_x);
                        break;

                    // Thread 2's Display Case
                    case BEGIN_2:

                        // Calling Sleep to create some interval between the moves of the threads
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Random rand2 = new Random();
                        // Choose a random textview and update display to Y
                        TextView randomtext2 = tex.get(rand2.nextInt(tex.size()));
                        while (randomtext2.getText() == "O" || randomtext2.getText() == "X") {
                            randomtext2 = tex.get(rand2.nextInt(tex.size()));

                        }
                        randomtext2.setText("O");
                        win_y.add(randomtext2);

                        count++;
                        // Calling the method which checks for Winner of the game
                        Choose_result(win_x,win_y,"O");
                        break;

                }
            }
        }
    };

    // Textviews are used and are updated to show the move made by each thread
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private Button btn1;

    public static final int BEGIN_1 = 1 ;
    public static final int BEGIN_2 = 2 ;


    ArrayList<TextView> tex = new ArrayList<TextView>();

    // Declaring two threads and their respective handlers
    Thread t1= new Thread(new MakeMoveRunnable1());
    Thread t2=new Thread(new MakeMoveRunnable2());
    private Handler workerHandler1;
    private Handler workerHandler2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView)findViewById(R.id.cell11);
        tv2 = (TextView)findViewById(R.id.cell12);
        tv3 = (TextView)findViewById(R.id.cell13);
        tv4 = (TextView)findViewById(R.id.cell21);
        tv5 = (TextView)findViewById(R.id.cell22);
        tv6 = (TextView)findViewById(R.id.cell23);
        tv7 = (TextView)findViewById(R.id.cell31);
        tv8 = (TextView)findViewById(R.id.cell32);
        tv9 = (TextView)findViewById(R.id.cell33);
        btn1 =(Button)findViewById(R.id.button);

        tex.add(tv1);
        tex.add(tv2);
        tex.add(tv3);
        tex.add(tv4);
        tex.add(tv5);
        tex.add(tv6);
        tex.add(tv7);
        tex.add(tv8);
        tex.add(tv9);


        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // If thread is already alive and has finished all moves , reset the board and start the game again when button is clicked
                    if(t1.isAlive() && count >=8)
                    {
                        tv1.setText(" ");
                        tv2.setText(" ");
                        tv3.setText(" ");
                        tv4.setText(" ");
                        tv5.setText(" ");
                        tv6.setText(" ");
                        tv7.setText(" ");
                        tv8.setText(" ");
                        tv9.setText(" ");
                        count =0;
                        win_x.clear();
                        win_y.clear();
                        X=0;
                        Y=0;
                        // Calling one of the handlers to start off the game again
                        Message msg4 = workerHandler1.obtainMessage(BEGIN_1);
                        workerHandler1.sendMessage(msg4);
                    }

                //If thread is already alive and button is clicked in the middle of the game , reset the board and start game again
                    else if(t1.isAlive()) {
                        tv1.setText(" ");
                        tv2.setText(" ");
                        tv3.setText(" ");
                        tv4.setText(" ");
                        tv5.setText(" ");
                        tv6.setText(" ");
                        tv7.setText(" ");
                        tv8.setText(" ");
                        tv9.setText(" ");
                        count =0;
                        win_x.clear();
                        win_y.clear();
                        X=0;
                        Y=0;
                } else    // Initial case of starting the threads (when button is clicked first)
                    {
                        t1.start();
                        t2.start();
                    }


            }
        });

    }
// Thread 1 runnable
    public class MakeMoveRunnable1 implements Runnable {

        public void run() {
            // Creating a looper for the message queue of thread 1
            Looper.prepare();

            // Sending message to UI handler
            Message msg1 = mHandler.obtainMessage(MainActivity.BEGIN_1) ;
            mHandler.sendMessage(msg1) ;

            // Handler of thread1
            workerHandler1 = new Handler() {
                public void handleMessage(Message msg) {
                    int what = msg.what;
                    switch (what) {
                        case BEGIN_1:
                            Message msg1 = mHandler.obtainMessage(MainActivity.BEGIN_1) ;
                            mHandler.sendMessage(msg1) ;

                            break;

                    }
                }
            };

            Looper.loop();
        }

        }
    // Thread 2 runnableHamed Rezaei ‎[uiccs587@gmail.com]‎
    public class MakeMoveRunnable2 implements Runnable {

        public void run() {
            // Creating a looper for the message queue of thread 1
            Looper.prepare();

            // Sending message to UI handler
            workerHandler2 = new Handler() {
                public void handleMessage(Message msg) {
                    int what = msg.what;
                    switch (what) {
                        case BEGIN_2:
                            Message msg1 = mHandler.obtainMessage(MainActivity.BEGIN_2) ;
                            mHandler.sendMessage(msg1) ;

                            break;

                    }
                }
            };
            Looper.loop();
        }
    }

    // Method that checks for conditions to declare winner of the game
    public void Choose_result(ArrayList<TextView> win_x , ArrayList<TextView> win_y ,String person)
    {
      //Check if X is winner
      if(win_x.contains(tv1) && win_x.contains(tv2) && win_x.contains(tv3)) X =1;
      if(win_x.contains(tv4) && win_x.contains(tv5) && win_x.contains(tv6)) X =1;
      if(win_x.contains(tv7) && win_x.contains(tv8) && win_x.contains(tv9)) X=1;

      if(win_x.contains(tv1) && win_x.contains(tv4) && win_x.contains(tv7)) X=1;
      if(win_x.contains(tv2) && win_x.contains(tv5) && win_x.contains(tv8)) X=1;
      if(win_x.contains(tv3) && win_x.contains(tv6) && win_x.contains(tv9)) X=1;

      if(win_x.contains(tv1) && win_x.contains(tv5) && win_x.contains(tv9)) X=1;
      if(win_x.contains(tv3) && win_x.contains(tv5) && win_x.contains(tv7)) X=1;

      // Check if Y is winner
        if(win_y.contains(tv1) && win_y.contains(tv2) && win_y.contains(tv3)) Y =1;
        if(win_y.contains(tv4) && win_y.contains(tv5) && win_y.contains(tv6)) Y =1;
        if(win_y.contains(tv7) && win_y.contains(tv8) && win_y.contains(tv9)) Y=1;

        if(win_y.contains(tv1) && win_y.contains(tv4) && win_y.contains(tv7)) Y=1;
        if(win_y.contains(tv2) && win_y.contains(tv5) && win_y.contains(tv8)) Y=1;
        if(win_y.contains(tv3) && win_y.contains(tv6) && win_y.contains(tv9)) Y=1;

        if(win_y.contains(tv1) && win_y.contains(tv5) && win_y.contains(tv9)) Y=1;
        if(win_y.contains(tv3) && win_y.contains(tv5) && win_y.contains(tv7)) Y=1;

        if(X==1) {
            Toast.makeText(MainActivity.this, "Player 1 wins! ", Toast.LENGTH_SHORT).show(); count = 15;
        }
        else if(Y==1) {
            Toast.makeText(MainActivity.this, "Player 2 wins! ", Toast.LENGTH_SHORT).show();count = 15;
        }
        else
        {
            // If 9 moves have not yet been made.. then game should continue
            if(count <= 8) {

                // If the move was by Thread1 , then send message to thread 2
                if(person == "X") {
                    Message msg2 = workerHandler2.obtainMessage(BEGIN_2);
                    workerHandler2.sendMessage(msg2);

                }
                // If the move was by Thread2 , then send message to thread 1
               else if(person =="O"){
                    Message msg3 = workerHandler1.obtainMessage(BEGIN_1);
                    workerHandler1.sendMessage(msg3);
                }
            }
            else  // If game ends and no winner has been found
                Toast.makeText(MainActivity.this, "Tie!Game Over!! ", Toast.LENGTH_SHORT).show() ;

        }
    }
}
