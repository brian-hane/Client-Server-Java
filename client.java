import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument.Content;

//import Client.Protocol;

import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class client extends JPanel implements ActionListener{

	private JFrame frame;
	private JTextField address;
	private JTextField post_x;
	private JTextField port;
	private JTextField pin_x;
	private JTextField post_y;
	private JTextField width;
	private JTextField height;
	private JTextField pst_note;
	private JTextField gt_x;
	private JTextField gt_y;
	private JTextField pin_y;
	private JTextField gt_note;
	private JTextField gt_color;
	private JTextArea content;
	private boolean connected;
	private JButton Connect;
	private JButton Post;
	private JButton Pin;
	private JButton Unpin;
	private JButton Get;
	private JButton Disconnect;
	private JButton Clear;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					client window = new client();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 676, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Connection");
		lblNewLabel.setBounds(33, 31, 71, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Get");
		lblNewLabel_1.setBounds(33, 75, 71, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Post");
		lblNewLabel_2.setBounds(33, 123, 71, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		address = new JTextField();
		address.setBounds(158, 28, 116, 22);
		frame.getContentPane().add(address);
		address.setColumns(10);
		try {
			address.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gt_color = new JTextField();
		gt_color.setToolTipText("color");
		gt_color.setBounds(158, 72, 58, 22);
		frame.getContentPane().add(gt_color);
		gt_color.setColumns(10);
		
		post_x = new JTextField();
		post_x.setBounds(158, 120, 23, 22);
		frame.getContentPane().add(post_x);
		post_x.setColumns(10);
		
		port = new JTextField();
		port.setBounds(353, 28, 116, 22);
		frame.getContentPane().add(port);
		port.setColumns(10);
		
		Connect = new JButton("Connect");
		Connect.setBounds(504, 27, 97, 25);
		frame.getContentPane().add(Connect);
		
		Post = new JButton("Post");
		Post.setBounds(504, 119, 97, 25);
		frame.getContentPane().add(Post);
		
		Get = new JButton("Get");
		Get.setBounds(504, 71, 97, 25);
		frame.getContentPane().add(Get);
		
		Unpin = new JButton("Unpin");
		Unpin.setBounds(504, 174, 97, 25);
		frame.getContentPane().add(Unpin);
		
		Disconnect = new JButton("Disconnect");
		Disconnect.setBounds(504, 235, 97, 25);
		frame.getContentPane().add(Disconnect);
		
		Pin = new JButton("Pin");
		Pin.setBounds(372, 174, 97, 25);
		frame.getContentPane().add(Pin);
		
		Clear = new JButton("Clear");
		Clear.setBounds(372, 235, 97, 25);
		frame.getContentPane().add(Clear);
		
		JLabel lblNewLabel_3 = new JLabel("Pin/Unpin");
		lblNewLabel_3.setBounds(33, 178, 71, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		pin_x = new JTextField();
		pin_x.setBounds(158, 175, 23, 22);
		frame.getContentPane().add(pin_x);
		pin_x.setColumns(10);
		
		post_y = new JTextField();
		post_y.setBounds(193, 120, 23, 22);
		frame.getContentPane().add(post_y);
		post_y.setColumns(10);
		
		width = new JTextField();
		width.setBounds(263, 120, 23, 22);
		frame.getContentPane().add(width);
		width.setColumns(10);
		
		height = new JTextField();
		height.setBounds(298, 120, 23, 22);
		frame.getContentPane().add(height);
		height.setColumns(10);
		
		pst_note = new JTextField();
		pst_note.setBounds(413, 120, 56, 22);
		frame.getContentPane().add(pst_note);
		pst_note.setColumns(10);
		
		JLabel pst_lable1 = new JLabel("coordinate");
		pst_lable1.setBounds(156, 107, 60, 16);
		frame.getContentPane().add(pst_lable1);
		
		JLabel pst_lable2 = new JLabel("size(width/hight)");
		pst_lable2.setBounds(237, 107, 97, 16);
		frame.getContentPane().add(pst_lable2);
		
		JLabel gt_lable1 = new JLabel("color");
		gt_lable1.setBounds(158, 55, 56, 16);
		frame.getContentPane().add(gt_lable1);
		
		gt_x = new JTextField();
		gt_x.setBounds(263, 72, 23, 22);
		frame.getContentPane().add(gt_x);
		gt_x.setColumns(10);
		
		gt_y = new JTextField();
		gt_y.setBounds(298, 72, 23, 22);
		frame.getContentPane().add(gt_y);
		gt_y.setColumns(10);
		
		pin_y = new JTextField();
		pin_y.setBounds(193, 175, 23, 22);
		frame.getContentPane().add(pin_y);
		pin_y.setColumns(10);
		
		JLabel pin_lable = new JLabel("coordinate");
		pin_lable.setBounds(158, 155, 71, 16);
		frame.getContentPane().add(pin_lable);
		
		JLabel ge_lable2 = new JLabel("coordinate");
		ge_lable2.setBounds(261, 55, 73, 16);
		frame.getContentPane().add(ge_lable2);
		
		gt_note = new JTextField();
		gt_note.setBounds(353, 72, 116, 22);
		frame.getContentPane().add(gt_note);
		gt_note.setColumns(10);
		
		JLabel conn_lable1 = new JLabel("address");
		conn_lable1.setBounds(158, 13, 56, 16);
		frame.getContentPane().add(conn_lable1);
		
		JLabel conn_lable2 = new JLabel("port_number");
		conn_lable2.setBounds(353, 13, 109, 16);
		frame.getContentPane().add(conn_lable2);
		
		JLabel gt_lable3 = new JLabel("refers to");
		gt_lable3.setBounds(353, 55, 56, 16);
		frame.getContentPane().add(gt_lable3);
		
		JLabel pst_lable = new JLabel("note");
		pst_lable.setHorizontalAlignment(SwingConstants.LEADING);
		pst_lable.setBounds(413, 107, 56, 16);
		frame.getContentPane().add(pst_lable);
		
		content = new JTextArea();
		content.setBounds(33, 236, 241, 92);
		frame.getContentPane().add(content);
		
		pst_color = new JTextField();
		pst_color.setBounds(345, 120, 56, 22);
		frame.getContentPane().add(pst_color);
		pst_color.setColumns(10);
		
		pst_lable4 = new JLabel("color");
		pst_lable4.setBounds(345, 107, 56, 16);
		frame.getContentPane().add(pst_lable4);
		
		list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"", "white", "red", "green", "yellow"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(320, 291, 116, 80);
		frame.getContentPane().add(list);
		sb();
	}
	
	
	
		private Socket conn;
		private PrintWriter out;
		private BufferedReader in;
		private JTextField pst_color;
		private JLabel pst_lable4;
		private JList list;
		
		/**
		 * set buttonlistenor
		 */
		private void sb() {
			Post.addActionListener(this);
			Pin.addActionListener(this);
			Unpin.addActionListener(this);
			Connect.addActionListener(this);
			Disconnect.addActionListener(this);
			Clear.addActionListener(this);
			Get.addActionListener(this);
		}


		/**
		 * dissconnect from server
		 */
		void disconnect() {
			if(connected) {
				try {
					out.println("END");
					content.append("END");
					conn.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this,"Did not disconnect properly");
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "not connected, can not disconnect");
			}
		}
		
		/**
		 * attempt to connect to server.
		 * @param host
		 * @param port
		 * @return
		 */
		boolean connected(String host, int port) {
			try {
				conn = new Socket(host,port);
				out = new PrintWriter(conn.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} catch (UnknownHostException e0) {
				showErrorMsgBox("Unknow Host", "Connection Error");
				return false;
			} catch (ConnectException e1) {
				showErrorMsgBox("Connection Time Out", "Connection Error");
				return false;
			} catch (IllegalArgumentException e2) {
				showErrorMsgBox("Port number out of range", "Connection Error");
				return false;
			}

			catch (Exception e) {
				showErrorMsgBox(e.toString(), "Unknown Error");
				return false;
			}
			connected = true;
			return true;
			
		}
		
		/**
		 * Bottom pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			String str = "";
			String response = "";
			if(!connected&& e.getActionCommand()!="Connect") {
				showErrorMsgBox("Connect to server first before any other actions","Connection Error");
				
			}else {
				switch(e.getActionCommand()) {
				case"Connect":{
					if(connected(address.getText(),Integer.parseInt(port.getText()))) {
						content.append("SUCESS\r\n");
					}
					break;}
				case"Post":{
					boolean temp1 = true;
					try {
						int i = Integer.parseInt(post_x.getText());
						i = Integer.parseInt(post_y.getText());
						i = Integer.parseInt(width.getText());
						i = Integer.parseInt(height.getText());
					}catch(NumberFormatException | NullPointerException nfe) {
				        temp1  = false;}
					if(!temp1) {
						showErrorMsgBox("Please enter integer for coordinate and size","Invalid input");
						break;
					}else if(list.getSelectedIndex()==-1||list.getSelectedIndex()==0) {
						showErrorMsgBox("choose a color from input","Invalid input");
						break;
					}
					else if(pst_note.getText().equals("")) {
						showErrorMsgBox("Note can not be empty","Invalid input");
						break;
					}
					else {
						pst_color.setText((String) list.getSelectedValue());
						str = "POST "+ post_x.getText() +" " +post_y.getText() + " " + width.getText() +" " + height.getText() + " " + list.getSelectedValue()+ " "+pst_note.getText()+"\r\n";
						response = conncetion(str);
						if(response != null) {
							content.append("SUCESS\r\n");
						}
						else {
							content.append("failed\r\n");
						}
					}
						
					
					break;}
				case"Get":{
					str = "GET";
					if(!(list.getSelectedIndex()==-1||list.getSelectedIndex()==0)) {
						gt_color.setText((String) list.getSelectedValue());
						str = str + " " +"color="+ list.getSelectedValue();
					}
					if(!(gt_x.getText().equals("")||gt_y.getText().equals(""))) {
						boolean temp1 = true;
						try {
							int i = Integer.parseInt(gt_x.getText());
							i = Integer.parseInt(gt_y.getText());
							
						}catch(NumberFormatException | NullPointerException nfe) {
					        temp1  = false;}
						if(!temp1) {
							showErrorMsgBox("Please enter integer for coordinate","Invalid input");
							break;}
							
						else {
							str = str +" "+"contains="   + gt_x.getText() +"," +gt_y.getText();
						}
					}
					if(!gt_note.getText().equals("")) {
						str = str + " " + "refersTo=" + gt_note.getText();					}
					str = str + "\r\n";
					response = conncetion(str);
					if(response != null) {
						content.append("SUCESS\r\n");
					}
					else {
						content.append("failed\r\n");
					}
				
					break;}
				case"Pin":{
					str = "PIN";
					if(!(pin_x.getText().equals("")||pin_y.getText().equals(""))) {
						boolean temp1 = true;
						try {
							int i = Integer.parseInt(pin_x.getText());
							i = Integer.parseInt(pin_y.getText());
							
						}catch(NumberFormatException | NullPointerException nfe) {
					        temp1  = false;}
						if(!temp1) {
							showErrorMsgBox("Please enter integer for coordinate","Invalid input");
							break;}
						else {
							str = str+ " " + pin_x.getText() + "," + pin_y.getText() +"\r\n";
						}
					}
					response = conncetion(str);
					if(response != null) {
						content.append("SUCESS\r\n");
					}
					else {
						content.append("failed\r\n");
					}
					break;}
				case"Unpin":{
					str = "UNPIN";
					if(!(pin_x.getText().equals("")||pin_y.getText().equals(""))) {
						boolean temp1 = true;
						try {
							int i = Integer.parseInt(pin_x.getText());
							i = Integer.parseInt(pin_y.getText());
							
						}catch(NumberFormatException | NullPointerException nfe) {
					        temp1  = false;}
						if(!temp1) {
							showErrorMsgBox("Please enter integer for coordinate","Invalid input");
							break;}
						else {
							str = str+ " " + pin_x.getText() + "," + pin_y.getText() +"\r\n";
						}
					}
					response = conncetion(str);
					if(response != null) {
						content.append("SUCESS\r\n");
					}
					else {
						content.append("failed\r\n");
					}
					break;}
				case"Clear":{
					str = "CLEAR";
					response = conncetion(str);
					if(response != null) {
						content.append("SUCESS\r\n");
					}
					else {
						content.append("failed\r\n");
					}
					
					break;}
				case"Disconnect":{
					disconnect();
					str = "Disconnect";
					response = connection(str);
					if(response != null) {
						content.append("SUCESS\r\n");
					}
					else {
						content.append("failed\r\n");
					}
					break;}
				}
					
					
			}
			
		}
		

		
		
		/**
		 * show error message
		 * @param err
		 * @param title
		 */
		private void showErrorMsgBox(String err, String title) {
			JOptionPane.showMessageDialog(this, err, title, JOptionPane.ERROR_MESSAGE);
		}
		
		
		/**
		 * Communicate to server, send string as request and take back response
		 * @param value
		 * @return
		 */
		private String conncetion(String value) {
			String result = null;
			String line = "";
			out.println(value);
			try {
				line = in.readLine();
				while(line!= "") {
					content.append(line+ "\r\n");
				}
				result = "success";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				showErrorMsgBox("Can not read from server", "Error");
				//disconnect();
			}
			
			return result;
		}
}