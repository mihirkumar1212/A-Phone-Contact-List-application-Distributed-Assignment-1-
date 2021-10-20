import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client{
	public static void main(String argv[]) throws Exception {

		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		//Storing user input to a variale
		String hostName = "localhost";
		int portNumber = 9000; // conerting sting input to a interger


		try{
			// opens a connection to a server
			Socket clientSocket = new Socket(hostName, portNumber);
			// creating inputstreat to read data form the server
			DataInputStream clientInput = new DataInputStream(clientSocket.getInputStream());
			// created outpustret to write data to ther server
			DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
			System.out.println(" Conneted to the server!");
			//Diaplaying available option to guide user 
			int userOption = 0;
			while(userOption != 99){
				System.out.println(clientInput.readUTF());	
				System.out.println(clientInput.readUTF());
				System.out.println(clientInput.readUTF());
				System.out.println(clientInput.readUTF());
				System.out.println(clientInput.readUTF());
				System.out.println(clientInput.readUTF());
				System.out.println("Waiting for user input.......");
    			userOption = myObj.nextInt();  // Read user input
   			
				// sends user selection
				clientOutput.writeInt(userOption);
				clientOutput.flush();
		
				// based on user selection
			
				if (userOption == 1){
					System.out.println( clientInput.readUTF());
					int count = 0;
					while(count < 12)
					System.out.println(clientInput.readUTF());
					count++;
				}

				else if (userOption == 2){
					
					String xwrong = myObj.nextLine();
					// stores and send eamil 
					
					System.out.println(clientInput.readUTF());
					String email = myObj.nextLine();
					clientOutput.writeUTF(email);
					clientOutput.flush();
					// stores and send phoneNumber
			
					System.out.println(clientInput.readUTF());
					String phoneNumber = myObj.nextLine();
					clientOutput.writeUTF(phoneNumber);
					clientOutput.flush();
					// stores and sends name  
					System.out.println(clientInput.readUTF());
					String name = myObj.nextLine();
					clientOutput.writeUTF(name);
					clientOutput.flush();

					System.out.println("Your information:"+ name +" "+ email + " " +phoneNumber);
					System.out.println(clientInput.readUTF());
				}
				else if (userOption == 3){
					String xwrong = myObj.nextLine();
					System.out.println(clientInput.readUTF());
					String target = myObj.nextLine();
					clientOutput.writeUTF(target);
					clientOutput.flush();
					System.out.println(clientInput.readUTF());

				}
				else if (userOption == 4){
					String xwrong = myObj.nextLine();
					System.out.println(clientInput.readUTF());
					String targetname = myObj.nextLine();
					clientOutput.writeUTF(targetname);
					clientOutput.flush();
					System.out.println(clientInput.readUTF());
				}
			}
			userOption = 0;
			clientSocket.close();
		    
			myObj.close();
		   }

		catch( Exception e )
        {
            e.printStackTrace();
			System.exit(1);
        }

	}
}
