import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
abstract class TicTacToeAbstract extends JPanel implements ActionListener, Runnable {

	protected ServerSocket ss;
	protected JLabel xl=new JLabel("Who's Turn???");
	protected JButton btn[][]=new JButton[3][3];
	protected boolean turn;
	protected BufferedReader br=null;
	protected PrintWriter pr;
	protected Socket s;
	protected String msg[]={"Not your turn!!!", "Wait for your turn!!!", "Have patience!!!","U r not THE only one!!!"};
	protected Thread  t;
	protected JPanel pnl=this;

	public void actionPerformed(ActionEvent e){
		click(e);
	}

	protected void enableAll(boolean b){
	  for(int i=0;i<3;++i)for(int j=0;j<3;++j)btn[i][j].setEnabled(b);
	}

	protected void reset(boolean call){
	  for(int i=0;i<3;++i)
	  for(int j=0;j<3;++j)
	  {
	    btn[i][j].setText("");
	    btn[i][j].setBackground(Color.WHITE);
	    btn[i][j].setEnabled(true);
	  }
	  if(call){
	    pr.println("reset");pr.flush();
	  }
	}

	protected void click(ActionEvent e){
		if(!turn){
			JOptionPane.showMessageDialog(this,msg[(int)(Math.random()*msg.length)]);
			return;
		}
		turn=false;
		MyButton b = (MyButton)e.getSource();
		b.setEnabled(false);
		xl.setText("Not Your Turn!!!");
		String x = "X";
		b.setText(x);
		b.setBackground(Color.LIGHT_GRAY);
		//down
		int w=0;
		for(int j=0;j<3;++j){
			w=0;
			for(int i=0;i<3;++i){
				if(btn[i][j].getText().equals(x))w++;
			}
			if(w==3)break;
		}

		//horrible
		if(w!=3)
		for(int j=0;j<3;++j){
			w=0;
			for(int i=0;i<3;++i){
				if(btn[j][i].getText().equals(x))w++;
			}
			if(w==3)break;
		}

		// \
		if(w!=3){
		w=0;
		for(int j=0;j<3;++j){
			if(btn[j][j].getText().equals(x))w++;
		}
		}
		// /
		if(w!=3){
		w=0;
		for(int j=0;j<3;++j){
			if(btn[2-j][j].getText().equals(x))w++;
		}
		}

		pr.println(w);
		pr.println(b.getI());
		pr.println(b.getJ());
		pr.flush();

		if(w==3){
			JOptionPane.showMessageDialog(this,"You Win!!!");
			enableAll(false);
		}

	}
}
