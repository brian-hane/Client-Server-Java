import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.*;
class PostNote{ 
	String note;
	String colour;
	Integer l_x;
	Integer l_y;
	Integer width;
	Integer height;
	Boolean pinnedStatus;
	public PostNote(){
		
	}
	public PostNote(String note, String colour, Integer l_x, Integer l_y, Integer width, Integer height, Boolean pinnedStatus){
		this.note = note;
		this.colour = colour;
		this.l_x = l_x;
		this.l_y= l_y;
		this.width = width;
		this.height = height;
		this.pinnedStatus = pinnedStatus;
	}
}//class note

class Request implements Runnable{
	final static String CRLF = "\r\n";
	Socket socket;
	
	ConcurrentHashMap<String, PostNote> postnote;
	
	public Request(Socket socket, ConcurrentHashMap<String, PostNote> postnote) throws Exception{//1
		this.socket =socket;
		this.postnote = postnote;
	}
	public void run(){
		try{
			processRequest();
		}catch (Exception e){
		//	socket.close();
			System.out.println("Client closed without disconnect");
			System.out.println(e);
		}
	}


	public void processRequest () throws Exception{
		String operator="";
		String [] txt;
		String [] temp = new String[0];
		String getContent;
		int x=-1;
		int y=-1;
		String colour =null;
		System.out.println("Request is being processed...");
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new  PrintWriter(socket.getOutputStream(),true);

		getContent = in.readLine();
		if ((getContent != "Connect") && (getContent!= "Disconnect") && (getContent!= "Clear") && (getContent!= "Get")){
			temp = getContent.split(" ");
			operator = temp[0];
		}
	

		switch (operator){
		case "GET":
			//GET color =<color> contains = <data> refersTo = <string>
			Boolean refersStatus = false;
			Boolean colorStatus = false;
			Boolean containsStatus = false;	
			String refersTo=null;	
			int i;
			temp = getContent.split(" ");
		
			if (getContent == "Get"){
				refersStatus = true;
				colorStatus = true;
				containsStatus = true;
			}else{ 
				i=1;
				
			if (temp[i].contains("color=")){
				colorStatus = true;
				i=i+1;
				colour = temp[i].substring(6,temp[i].length());
	
				
			}else if (temp[i].contains("contains=")){
				containsStatus = true;
				i=i+1;	
				txt = temp[i].split("=");
				txt = txt[1].split(",");
				x = Integer.parseInt(txt[0]);
				y = Integer.parseInt(txt[1]);
			}else if (temp[i].contains("refersTo=")){	
				refersStatus = true;
				i=i+1;
				txt = temp[i].split("refersTo=");
				refersTo = txt[1];
			}

			}

			Boolean result = false;
			String items="";
			int t=0;
			PostNote hold = new PostNote();
			for (PostNote current :postnote.values()){
				result = true;
				if (colorStatus == true){
					if (colour == current.colour){
						hold = current;
					}else{	
						result = false;
					}
				}
				if (refersStatus == true){ 
					if (current.note.contains(refersTo)){
						hold = current;
					}else{
						result = false;
					}
					
				if (containsStatus == true){
					if ((x == current.l_x) && (y== current.l_y)){
						hold = current;
					}else{
						result = false;
					}
				}
				if(result == true){
					items = items + hold.l_x + hold.l_y + hold.colour + hold.width + hold.height + hold.colour+ hold.note + CRLF;
					out.println(items);
				}
			
			
				}
			}
			
		case "POST":
			String inputContent [] = in.readLine().split(" ");
			if (inputContent.length>=6){
				String message="";
				for (i=6; i<inputContent.length; i++){//elements 6 until end of array are the MESSAGE
					message = message + (inputContent[i]);
				}
				PostNote note = new PostNote(message,inputContent[5],Integer.parseInt(inputContent[1]),Integer.parseInt(inputContent[2]), 
						Integer.parseInt(inputContent[3]),Integer.parseInt(inputContent[4]), false);
				postnote.put(message, note);
				out.println("SUCCESS - POST CONFIRMED");
			}else{
				out.println("ERROR: Invalid post content");
				break;
			}
			break;
		case "CLEAR":
			postnote.clear();
			System.out.println("Notes have been cleanred");
			break;
		case "DISCONNECT":
			in.close();
			out.close();
			socket.close();
			System.out.println("Connection has been closed");
			break;
		case "PIN"://PIN 4,4
			temp = in.readLine().split(",");
			y = Integer.parseInt(temp[1]);
			temp = temp[0].split(" ");
			x =  Integer.parseInt(temp[1]);
			for (PostNote current :postnote.values()){
				if((current.l_x == x) && (current.l_y == y)){
					current.pinnedStatus = true;
				}
			}
			break;
		case "UNPIN":
			temp = in.readLine().split(",");
			y = Integer.parseInt(temp[1]);
			temp = temp[0].split(" ");
			x =  Integer.parseInt(temp[1]);
			for (PostNote current :postnote.values()){
				if((current.l_x == x) && (current.l_y == y)){
					current.pinnedStatus = false;
				}
			}
			
			break;
		default:
			out.println("Invalid Operation");
			break;
		}//end case

			
		}
}
	

class Server {
	
	public  ConcurrentHashMap<String, PostNote> postnote;
	
	final class Connection extends Thread{
		private Socket socket;
		private int client_num;
		
		public Connection (Socket socket, int client_num){
			this.socket = socket;
			this.client_num = client_num;
		}
	}
	

	public static void main (String argv[]) throws Exception{
		
		int port;
		int width;
		int height;
		String [] colors;
		if(argv.length >0){
			try{
				port = Integer.parseInt(argv[0]);
				//width = Integer.parseInt(argv[1]);
				//height = Integer.parseInt(argv[2]);
				//colors = argv[3:argv.size()];
			}catch (NumberFormatException e){
				System.out.println("Invalid port entered, defaulting to 8080");
				port = 8080;
			}
		}else{ //no port entered
			System.out.println("No port entered, defaulting to 8080");
			port = 8080;
		}
		
		ConcurrentHashMap<String, PostNote> postnote = new ConcurrentHashMap<String, PostNote>();
		
		ServerSocket socket;	
		try{
			socket = new ServerSocket(port);
		}catch (IllegalArgumentException e){
			System.out.println("Invalid port entered, defaulting to 9999");
			port = 9999;
			socket = new ServerSocket (port);
		}
		
		System.out.println("Launching Server");
		while (true){ //infinite loop to listen to process for HTTP requests
			Socket connection = socket.accept();
			Request httpRequest = new Request (connection, postnote);		
			Thread thread = new Thread (httpRequest);
			thread.start();
			
		}
	}
}


		
