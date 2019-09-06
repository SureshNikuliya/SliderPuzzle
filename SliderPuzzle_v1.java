package com.game.sliderpuzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SliderPuzzle_v1 extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JFrame SliderPuzzle;
	JButton[] button;
	String [] originalList = {"1","2","3","4","5","6","7","8",""};
	Map<JButton,List<Integer>> rowCol = new LinkedHashMap<JButton, List<Integer>>();
	List<Integer> ijVal;
	int min,sec;
	Timer t;
	int counter;
	
	JLabel Timerlabel;
	JButton help = new JButton("Help");
	JButton start = new JButton("Start");
	
	public SliderPuzzle_v1() {
	
		SliderPuzzle = new JFrame("SliderPuzzle");
		button = new JButton[9];
		
			
		SliderPuzzle.setSize(500, 300);
		SliderPuzzle.setLocation(200, 100);
		SliderPuzzle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		counter = 0;
		min=0;
		sec=0;
		Timerlabel = new JLabel("Timer : "+"00"+":"+"00", JLabel.LEFT);
		Timerlabel.setBounds(20, 0, 200, 50);
		SliderPuzzle.add(Timerlabel);
		
		help.addActionListener(this);
		help.setBounds(110, 5, 60, 40);
		SliderPuzzle.add(help);
		
		start.addActionListener(this);
		start.setBounds(55, 210, 80, 50);
		SliderPuzzle.add(start);
		
		int[] x = { 20, 70, 120, 20, 70, 120, 20, 70, 120 };
		int[] y = { 50, 50, 50, 100, 100, 100, 150, 150, 150 };
		for (int i = 0; i < 9; i++) 
		{
			
			if(i<8) button[i] = new JButton(String.valueOf(i+1));
			else  button[i] = new JButton();
			button[i].addActionListener(this);
			button[i].setBounds(x[i], y[i], 50, 50);
			SliderPuzzle.add(button[i]);
			
		}
		int pos=0;
		for(int i=0;i<3;i++)
		{
			 for(int j=0;j<3;j++)
			 {
				 ijVal = new ArrayList<Integer>();
				 ijVal.add(i);
				 ijVal.add(j);
				 rowCol.put(button[pos], ijVal);
				 pos++;
			 }
		}
		
		SliderPuzzle.setLayout(null);
		SliderPuzzle.setVisible(true);			
			
	}

	public static void main(String[] args) {
		new SliderPuzzle_v1();

	}
	
	public void startGame(JButton targetBtn)
	{
		
		Random rdm = new Random();
		List<Integer> posList = new ArrayList<Integer>();
		
		int i=0;
		
		
		while(i!=9)
		{
			int pos = rdm.nextInt(9);		
			if(!posList.contains(pos))
			{			
				posList.add(pos);
				button[i].setText(originalList[pos]);
				i++;
			}
							
		}		
		
	}
	
	public void reSetStatus()
	{	int i=0;
		for(JButton btn : button)
		{
			btn.setText(originalList[i]);
			i++;
		}
		min=0;
		sec=0;	

		Timerlabel.setText("Timer : "+"00"+":"+"00");
		
		t.cancel();
	}
	
	public void moveObj(JButton btn)
	{
		int [] ij = new int[2];
		int temp=0;
		for(int x:rowCol.get(btn))
		{
			ij[temp]=x;
			temp++;
		}
		Integer []  up = {ij[0]-1,ij[1]},
				down = {ij[0]+1,ij[1]},
				left = {ij[0],ij[1]-1},
				right = {ij[0],ij[1]+1};
		List<Integer[]> list = new ArrayList<Integer[]>();
		
		list.add(up);
		list.add(down);
		list.add(left);
		list.add(right);
		for(Integer [] cords : list)
		{
			for(Map.Entry<JButton,List<Integer>> entry :  rowCol.entrySet())
			{
				if(cords[0]==entry.getValue().get(0) && cords[1]==entry.getValue().get(1))
				{
					if(entry.getKey().getText().equals(""))
					{
						String obj = entry.getKey().getText();
						entry.getKey().setText(btn.getText()); 
						btn.setText(obj);
						break;
					}
				}
				
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		Object source  = evt.getSource();
		JButton targetBtn = (JButton) source; 
		
		/*int i=0;
    	for(JButton btn : button)
    	{
    		if(btn.getText().equals(originalList[i]))
    		{
    			counter++;
    		}
    		i++;
    	}
    	
    	if(counter==9)
    	{
    		System.out.println("test");
    	}
		
		*/
		if(targetBtn.getText().equals("Start"))
		{
			t = new Timer();
			t.schedule(new TimerTask() {
			    @Override
			    public void run() {
			    	sec++;
			    	if(sec==60)
			    	{	sec=0;
			    		min++;
			    	}
			    	Timerlabel.setText("Timer : "+min+":"+sec);
			    	
			    }
			}, 0, 1000);
			startGame(targetBtn);
			targetBtn.setText("ReStart");
		}
		
		else if(targetBtn.getText().equals("ReStart"))
		{
			reSetStatus();
			targetBtn.setText("Start");
		}
		
		else
		{
			if(!targetBtn.getText().equals("") && !start.getText().equals("Start"))
			{
				moveObj(targetBtn);
			}
			
		}
		
	}
	

}
