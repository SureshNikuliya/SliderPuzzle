package com.game.sliderpuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SliderPuzzle extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JFrame SliderPuzzle;
	JButton[] button;
	String [] originalList = {"1","2","3","4","5","6","7","8","X"};
	Image [] iniImgPos;
	Map<JButton,List<Integer>> rowCol = new LinkedHashMap<JButton, List<Integer>>();
	List<Integer> ijVal;
	int min,sec;
	Timer t;
	int counter;
	
	JLabel Timerlabel;
	JButton help = new JButton("Help");
	JButton start = new JButton("Start");
	JButton sampleImg;
	public SliderPuzzle() {
	
		SliderPuzzle = new JFrame("SliderPuzzle");
		button = new JButton[9];
		iniImgPos = new Image[9];
			
		SliderPuzzle.setSize(400, 300);
		SliderPuzzle.setLocation(200, 100);
		SliderPuzzle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		File file = new File("img/img1.jpg");
		BufferedImage bimg;
		try {
			bimg = ImageIO.read(file);
			Image img = bimg.getScaledInstance(150, 150, Image.SCALE_AREA_AVERAGING);
			sampleImg = new JButton(new ImageIcon(img));
			sampleImg.setBounds(210, 50, 150, 150);
			SliderPuzzle.add(sampleImg);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		counter = 0;
		min=0;
		sec=0;
		Timerlabel = new JLabel("Timer : "+"00"+":"+"00", JLabel.LEFT);
		Timerlabel.setBounds(20, 0, 200, 50);
		SliderPuzzle.add(Timerlabel);
		
		help.addActionListener(this);
		help.setBounds(300, 5, 60, 40);
		SliderPuzzle.add(help);
		
		start.addActionListener(this);
		start.setBounds(150, 210, 80, 40);
		SliderPuzzle.add(start);
		
		int[] x = { 20, 70, 120, 20, 70, 120, 20, 70, 120 };
		int[] y = { 50, 50, 50, 100, 100, 100, 150, 150, 150 };
		
		
		try {
			setImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 9; i++) 
		{
			
				button[i] = new JButton(new ImageIcon(iniImgPos[i]));
				button[i].setText(originalList[i]);
				button[i].setHorizontalTextPosition(SwingConstants.CENTER);
			
			
			button[i].addActionListener(this);
			button[i].setBounds(x[i], y[i], 50, 50);
			button[i].setForeground(Color.BLACK);
			button[i].setFont(new Font("Arial", Font.PLAIN, 1));
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
		new SliderPuzzle();

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
				
					button[i].setIcon(new ImageIcon(iniImgPos[pos]));
					button[i].setText(originalList[pos]);
					button[i].setHorizontalTextPosition(SwingConstants.CENTER);

				i++;
			}
							
		}
		
		
	}
	
	public void reSetStatus()
	{	int i=0;
		for(JButton btn : button)
		{

				btn.setIcon(new ImageIcon(iniImgPos[i]));
				btn.setText(originalList[i]);
				btn.setHorizontalTextPosition(SwingConstants.CENTER);
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
					if(entry.getKey().getText().equals("X"))
					{
						String str = entry.getKey().getText();
						entry.getKey().setText(btn.getText());
						btn.setText(str);
						
						Icon icon = entry.getKey().getIcon();
						entry.getKey().setIcon(btn.getIcon());
						btn.setIcon(icon);
						
						if(isComplete())
							{
								System.out.println("Completed!!!!!!");
								reSetStatus();
							}
						break;
					}
				}
				
			}
		}
	}
	
	public boolean isComplete()
	{
		int i=0;
		counter = 0;
    	for(JButton btn1 : button)
    	{
    		if(btn1.getText().equals(originalList[i]))
    		{
    			counter++;
    		}
    		i++;
    	}
    	
    	if(counter==9) return true;
    	else return false;
		
	}
	
	public void setImage() throws IOException{
		File file = new File("img/img1.jpg");
        BufferedImage bimg=ImageIO.read(file);
        int w=bimg.getWidth();
        int h=bimg.getHeight();
        int count=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                BufferedImage wim=bimg.getSubimage(j*h/3,i*w/3, w/3, h/3);
                Image sc=wim.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
                if(count<8) iniImgPos[count]=sc;
                count++;
                
            }
        }
        File file1 = new File("img/blank.png");
        BufferedImage bimg1=ImageIO.read(file1);
        BufferedImage wim=bimg1.getSubimage(0,0, 50, 50);
        Image sc=wim.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        iniImgPos[8]=sc;
        
    }


	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		Object source  = evt.getSource();
		JButton targetBtn = (JButton) source; 
		
		
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
			if (!start.getText().equals("Start"))
			{
				moveObj(targetBtn);
			}
			
		}
		
	}
	

}
