package org.nzdis.tictactoe;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.nzdis.fragme.ControlCenter;
import org.nzdis.fragme.helpers.StartupWaitForObjects;
import org.nzdis.fragme.util.NetworkUtils;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TTTMainActivity extends Activity implements Observer{
	
	private TextView player;
	private Button restart;
	private Button bn1;
	private Button bn2;
	private Button bn3;
	private Button bn4;
	private Button bn5;
	private Button bn6;
	private Button bn7;
	private Button bn8;
	private Button bn9;
	private TextView messinfo;
	
	private TicTacToeModel tModel;
	private int playerNum;
	private Button[] buttons=new Button[10];
	
	private Handler hdlr=new Handler(); 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tttmain);
        
        //get items of the activity
        player=(TextView)findViewById(R.id.textView1);
        restart=(Button)findViewById(R.id.button0);
        bn1=(Button)findViewById(R.id.button1);
        buttons[1]=bn1;
        bn2=(Button)findViewById(R.id.button2);
        buttons[2]=bn2;
        bn3=(Button)findViewById(R.id.button3);
        buttons[3]=bn3;
        bn4=(Button)findViewById(R.id.button4);
        buttons[4]=bn4;
        bn5=(Button)findViewById(R.id.button5);
        buttons[5]=bn5;
        bn6=(Button)findViewById(R.id.button6);
        buttons[6]=bn6;
        bn7=(Button)findViewById(R.id.button7);
        buttons[7]=bn7;
        bn8=(Button)findViewById(R.id.button8);
        buttons[8]=bn8;
        bn9=(Button)findViewById(R.id.button9);
        buttons[9]=bn9;
        messinfo=(TextView)findViewById(R.id.textView2);
        		
        //set up connection
        String address = NetworkUtils.getNonLoopBackAddressByProtocol(NetworkUtils.IPV4);
		if (address == null) {
			//println("Could not find a local ip address");
			System.out.println("Could not find a local ip address");
			return;
		}
		//println("Using address: " + address);
		System.out.println("Using address: " + address);
		String user = "test" + Build.MODEL;
		
		ControlCenter.setUpConnectionsWithHelper("TicTacToe", user, address, new StartupWaitForObjects(1));
		Vector shareObs = ControlCenter.getAllObjects();

		if (shareObs.size() == 0) {
			tModel=new TicTacToeModel();
			tModel = (TicTacToeModel) ControlCenter.createNewObject(TicTacToeModel.class);
			Log.i("TicTacToe", "Create a new TicTacToe object");
			playerNum=1;
			player.setText("Player1 (O)");

		} else {
			tModel = (TicTacToeModel) shareObs.get(0);
			Log.i("TicTacToe", "join and share the TicTacToe object");
			playerNum=2;
			player.setText("Player2 (X)");
		}
		
		tModel.addObserver(this);
		this.update(tModel, null);
        
    }
    
    //buttons click handlers
	public void onPosition1(View view) {

		buttonClick(1);
	}

	public void onPosition2(View view) {
		
		buttonClick(2);
	}

	public void onPosition3(View view) {
		
		buttonClick(3);
	}

	public void onPosition4(View view) {
		
		buttonClick(4);
	}

	public void onPosition5(View view) {
		
		buttonClick(5);
	}

	public void onPosition6(View view) {
		
		buttonClick(6);
	}

	public void onPosition7(View view) {
		
		buttonClick(7);
	}

	public void onPosition8(View view) {
		
		buttonClick(8);
	}

	public void onPosition9(View view) {
		
		buttonClick(9);
	}
	
	public void onRestart(View view){
		tModel.setNew_game(true);
		tModel.restart();
		tModel.change();
		this.update(tModel, null);
	}
    
    //method for process handler
    public void buttonClick(int position){
    	boolean legal=false;
    	if(playerNum==1){
    		legal=tModel.OPlays(position);
    	}else{
    		legal=tModel.XPlays(position);
    	}
    	if(legal){
    		tModel.change();
    	}else{
    		//print illegal move info
    		Log.e("Tictactoe","Illegal move!");
    		
    	}
    	this.update(tModel, null);
    }
    
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tttmain, menu);
        return true;
    }

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		final TicTacToeModel tttm=(TicTacToeModel)arg0;
		hdlr.post(new Runnable() {
			public void run(){
		updateInterface(tttm);}
		});
	
	}
	public void updateInterface(TicTacToeModel model){
		String[] positions=model.getPositions();
		if(model.isNew_game()){
			messinfo.setText("");
			restart.setText("Restart");
			
		}
		for (int i=1;i<=9;i++){
			if(positions[i].equals("X")){
				buttons[i].setText("X");
				buttons[i].setEnabled(false);
			}else if (positions[i].equals("O")){
				buttons[i].setText("O");
				buttons[i].setEnabled(false);
			}else {
				buttons[i].setText(" ");
				buttons[i].setEnabled(true);
			}
			if (tModel.isGameOver()){
				buttons[i].setEnabled(false);
				if(tModel.hasWon("X")){
					messinfo.setText("Player 2 won!");
				}else if (tModel.hasWon("O")){
					messinfo.setText("Player 1 won!");
				}else {
					messinfo.setText("The game is tied");
				}
				restart.setText("New Game");
			}
		}
	}
    
}
