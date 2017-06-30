import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
final class TicTacToeServer extends TicTacToeAbstract{

	private TicTacToeServer(){
		display();
		try{
		ss = new ServerSocket(9786);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,e);
		}
	}

	public void run(){
		try{
			t.join();
			String str=br.readLine();
			int chance = Integer.parseInt(str);
			enableAll(true);
			if(chance==1){
				xl.setText("Not Your Turn!!!");
				turn=false;
			}
			else{
				turn = true;
				xl.setText("Your Turn!!!");
			}
			while(true){
				try{
				str=br.readLine();
				int w=Integer.parseInt(str);

				str=br.readLine();
				int i=Integer.parseInt(str);

				str=br.readLine();
				int j=Integer.parseInt(str);

				btn[i][j].setText("O");
				btn[i][j].setEnabled(false);
				btn[i][j].setBackground(Color.PINK);
				if(w==3){
					JOptionPane.showMessageDialog(this,"You Lose!!!");
					enableAll(false);
				}
				turn=true;
				xl.setText("Your Turn!!!");
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this,"Reset!!!");
					reset(false);
				}
			}
		}catch(Exception e ){
				JOptionPane.showMessageDialog(this,e);
		}
	}

	private void connect(){
		t = new Thread(new Runnable(){
			public void run(){
				try{
					JOptionPane.showMessageDialog(pnl,"Connecting...");
					s = ss.accept();
					JOptionPane.showMessageDialog(pnl,"Connected!!!");
					pr = new PrintWriter(s.getOutputStream());
					br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(pnl,""+e);
				}
			}
		});
		t.start();
		Thread t2 = new Thread(this);
		t2.start();
	}

	public static void main(String args[]){
		Font f = new Font("Bookman Old Style",Font.BOLD,20);
		JFrame frame = new JFrame("Tic that Toe :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final TicTacToeServer p = new TicTacToeServer();
		frame.add(p);

		JPanel pN = new JPanel();
		pN.setLayout(new GridLayout(1,2));
		pN.add(p.xl);
		String str="Your IP: ";
		try{
		str+="You should probably know this!";//InetAddress.getLocalHost().getHostAddress();
		}catch(Exception w){JOptionPane.showMessageDialog(p,w);}
		JLabel temp = new JLabel(str);
		temp.setHorizontalAlignment(JLabel.RIGHT);
		pN.add(temp);
		frame.add(pN,BorderLayout.NORTH);

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,3,5,5));
		JButton b1 = new JButton("Connect!!!");
		JButton b2 = new JButton("Reset");
		b2.setEnabled(false);
		b1.setFont(f);
		b1.setBorder(BorderFactory.createRaisedBevelBorder());
		b1.setBackground(Color.YELLOW);
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				p.connect();
				b1.setEnabled(false);
				b2.setEnabled(true);
			}
		});
		b2.setFont(f);
		b2.setBackground(Color.ORANGE);
		b2.setBorder(BorderFactory.createRaisedBevelBorder());
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				p.reset(true);
			}
		});
		JLabel lbl = new JLabel("You: X     Other: O");
		lbl.setFont(f);
		lbl.setHorizontalAlignment(JLabel.CENTER);
		p1.add(b1);
		p1.add(b2);
		p1.add(lbl);
		frame.add(p1,BorderLayout.SOUTH);

		frame.setSize(650,600);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	private void display(){
		Font f = new Font("BROADWAY",Font.PLAIN,150);
		setLayout(new GridLayout(3,3));
		for(int i=0;i<3;++i)
		for(int j=0;j<3;++j){
			JButton b = new MyButton(i,j);
			b.setFont(f);
			b.setBackground(Color.WHITE);
			b.setForeground(Color.BLACK);
			b.setEnabled(false);
			btn[i][j]=b;
			b.addActionListener(this);
			add(b);
		}
	}
}
