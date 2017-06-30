import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
final class TicTacToeClient extends TicTacToeAbstract{

	private TicTacToeClient(){
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

	public void run(){
		try{
			while(true){
				try{
				String str=br.readLine();
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
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this,"Reset!!!");
					reset(false);
				}
			}
		}catch(Exception e ){
				JOptionPane.showMessageDialog(this,e);
		}
	}

	private boolean connect(){
		String ip = JOptionPane.showInputDialog(this,"Enter IP: ");
		if(ip==null || ip.trim().equals("")){
			JOptionPane.showMessageDialog(this,"Enter your friend's IP to play!!!");
			return false;
		}
		try{
			s = new Socket(ip,9786);
			pr = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			enableAll(true);
			int b = (int)(Math.random()*2);
			b=1;
			pr.println(b);
			pr.flush();
			if(b==1){
				turn=true;
				xl.setText("Your Turn!!!");
			}
			else{
				turn = false;
				xl.setText("Not Your Turn!!!");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(this,""+e);
		}
		Thread t = new Thread(this);
		t.start();
		return true;
	}

	public static void main(String args[]){
		Font f = new Font("Bookman Old Style",Font.BOLD,20);
		JFrame frame = new JFrame("Tic that Toe :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final TicTacToeClient p = new TicTacToeClient();
		frame.add(p);

		frame.add(p.xl,BorderLayout.NORTH);

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
				if(p.connect()){
					b1.setEnabled(false);
					b2.setEnabled(true);
				}
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
}
