import java.io.*;
import java.net.*;
import java.util.Scanner;


public class server {
	private static Scanner x;
	public static ServerSocket serverSocket;

	 public static void main(String[] args) throws Exception {
		new server();

	}
		public server() throws Exception {

			serverSocket = new ServerSocket(9000);
			System.out.println(" The server is listening on port: 9000");
			
			while(true)
			{
			Socket clientSocket1 = serverSocket.accept();

			System.out.println("New client connected: " + clientSocket1.getInetAddress().getHostAddress());

			ClientHandler clientSock = new ClientHandler(clientSocket1);
 			new Thread(clientSock).start();  
    		}
			//serverSocket.close();
   		}
		public static class ClientHandler implements Runnable {
			public final Socket clientSocket;

		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}
		public static String email, phoneNumber, nameClient,target, targetName;
		public static DataInputStream serverInput;
		public static DataOutputStream serverOutput;
		public void run()
		{
			try {
				serverInput = new DataInputStream(clientSocket.getInputStream());
	    		serverOutput = new DataOutputStream(clientSocket.getOutputStream());
					// cleint selection
					
					int userOption = 0;
				while (userOption != 99){
					serverOutput.writeUTF("*** Following are the availabe options you choose from: ****");
					serverOutput.flush();
					serverOutput.writeUTF("-Enter 1: To display current contact list");
					serverOutput.flush();
					serverOutput.writeUTF("-Enter 2: To add a new contact list");
					serverOutput.flush();
					serverOutput.writeUTF("-Enter 3: To remove a contact form the list");
					serverOutput.flush();
					serverOutput.writeUTF("-Enter 4: TO search for a existing contact");
					serverOutput.flush();
					serverOutput.writeUTF("-Enter 99: Exit");
					userOption = serverInput.readInt();

					
					// based on user selection
					if (userOption == 1){
						System.out.print("Client has selected option 1");

						serverOutput.writeUTF("The currrent phone list");
						serverOutput.flush();
						display();
					}
					else if (userOption == 2){
						System.out.print("Client has selected option 2");

						serverOutput.writeUTF("Enetr your Name");
						serverOutput.flush();
						nameClient = serverInput.readUTF();// read form client

						// stores and send eamil 
						serverOutput.writeUTF("Enetr your Email");
						serverOutput.flush();
						email = serverInput.readUTF();// read form client

						serverOutput.writeUTF("Enetr your Phone number");
						serverOutput.flush();
						phoneNumber = serverInput.readUTF();// read form client
						// calls the new
						ContactNew();
						System.out.println("**New added contact information:"+ nameClient +" "+ email + " " +phoneNumber);
					}
					else if (userOption == 3){
						System.out.print("Client has selected option 3");

						serverOutput.writeUTF("Enetr the name of the client you wish to delete");
						serverOutput.flush();	
						target = serverInput.readUTF();
						deleteContact();
						serverOutput.writeUTF("Contact deleted");
						serverOutput.flush();

					}
					else if (userOption == 4){
						System.out.println("Client has selected option 4");

						serverOutput.writeUTF("Enetr the name of the client you wish to search");
						serverOutput.flush();	
						targetName = serverInput.readUTF();
						search();
						serverOutput.writeUTF("Contact found!!!");
						serverOutput.flush();
					}
				}
			}
			catch( Exception e )
	        {
	            e.printStackTrace();
	    	 }
		}
		
		
		// used to display conatct list 
		public static void display() throws IOException{
			System.out.println("");
			System.out.println("Currently Contact List");
			// File path is passed as parameter
			File file = new File("phoneList.txt");
			// Creating an object of BuffferedReader class
			BufferedReader br = new BufferedReader(new FileReader(file));
			// Declaring a string variable
			String placeholder;
			// Consition holds true till
			// there is character in a string
			while ((placeholder = br.readLine()) != null){
				serverOutput.writeUTF(placeholder);
				serverOutput.flush();
			}	
			
				br.close();
		}

		// used for cread new contact 
		public static void ContactNew () throws IOException{
			try {

				File fileName = new File("phoneList.txt");
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
    			writer.append("\n"+ nameClient + "," + email + ","+ phoneNumber);
				writer.close();
				System.out.println("");
				System.out.println("Successfully added to the file.");
				serverOutput.writeUTF("Successfull added to the server database");

			  } catch (IOException e) {
				System.out.println("An error occurred wile addng a new file plese try again");
				e.printStackTrace();
			  }


		}
		// function for option 3
		public static void deleteContact () throws IOException{
			String eamilAddress =""; String userName =""; String contact ="";
			try {
				//String path = "phoneList.txt";
				//String tempfile ="temp.txt";
				//System.out.println("enter function");
				File file = new File("phoneList.txt");
				File filetemp  = new File("temp.txt");
           		filetemp.createNewFile();
				//System.out.println("File created succssfully");
			
				FileWriter fw = new FileWriter(filetemp, true);  
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				x = new Scanner(file);
				x.useDelimiter("[,\n]");

				while(x.hasNext())
				{
					userName = x.next();
					eamilAddress = x.next();
					contact = x.next();
					
					if (!userName.equals(target))
					{
						pw.println(userName+ "," + eamilAddress +","+ contact);

					}
					
				}
				x.close();
				pw.flush();pw.close();
				file.delete();
				
				File dump = new File ("phoneList.txt");
				filetemp.renameTo(dump);
				System.out.println("");
				System.out.println("The contact got deleted");

			  } catch (IOException e) {
				System.out.println("An error occurred wile addng a new file plese try again");
				e.printStackTrace();
			  }


		}
		public static void search() throws IOException{
			System.out.println("");
			System.out.println("searching......");
			// File path is passed as parameter
			File file = new File("phoneList.txt");
			Scanner br = new Scanner(new FileInputStream(file));
			
			while(br.hasNextLine()) {
				String line = br.nextLine();
				//System.out.println(line);
				if(line.indexOf(targetName)!=-1) {
				   System.out.println("Contact found!!");
				   serverOutput.writeUTF(targetName + ": Contact fount!!!");;
				}
			 }

			br.close();
		}

		
	}	
}