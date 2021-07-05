package com.techelevator.tenmo.services;


import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.auth.models.User;
import com.techelevator.tenmo.auth.models.UserCredentials;
import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import io.cucumber.java.sl.In;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {


	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
			}
		} while(result == null);
		return result;
	}

	public void showUserBalance(Account account){
		out.println("Your current account balance is: $ " + account.getBalance());
		out.flush();
	}

	public void errorCannotConnect(){
		out.println("Cannot connect to server. Connection refused");
		out.flush();
	}

	public void errorClientException(int statusCode , String message){
		out.println(statusCode + " " + message);
		out.flush();
	}

	//Passed the currentUser as an argument, before it was coming back null.
	//Remembered that a console should only have the scanner in/out as instance variables
	public void showAllUsersExceptCurrentUser(List<User> userList, AuthenticatedUser currentUser){
		out.println("_________________________________________");
		out.println("Users");
		out.println("ID             Name");
		out.println("_________________________________________");

		for (int i = 0;  i < userList.size(); i ++ ){
			if (userList.get(i).getId().equals(currentUser.getUser().getId())){
				continue;
			}
			out.println(userList.get(i).getId() + "          " +  userList.get(i).getUsername());
		}
	}

	//if we wan to return an int we need to parse this string into that
	public Integer getUserIdOfToAccount(){
		Integer userChoice = null;
		while(true){
			out.flush();
			try {
				out.println("Enter ID of user you are sending to (0 to cancel): "  );
				String userInput = in.nextLine();

				try {
					userChoice = Integer.parseInt(userInput);
				} catch(NumberFormatException e) {
					out.println(System.lineSeparator() + "*** " + userInput + " is not valid ***" + System.lineSeparator());
				}
				break;
			} catch (ResourceAccessException e) {
				errorCannotConnect();
			} catch (RestClientResponseException e) {
				errorClientException(e.getRawStatusCode() , e.getStatusText());
			}
		}
		return userChoice;
	}

	public void printViewTransferScreen() {
		out.println("-------------------------------------------");
		out.println("Transfers");
		out.println("ID          From/To                 Amount");
		out.println("-------------------------------------------");
		out.flush();
	}


		public void printuserFromDetails(AuthenticatedUser currentUser, Transfer transfer){
			out.println( transfer.getTransferId() + "        From: " + transfer.getUserFrom() + " " + transfer.getAmount() );
		}




		//out.println(transfer.getAccountTo() + "          To: " + transfer.getUserTo() + " " + account.getBalance() );


	public Double getAmountToTransfer(){
		Double userChoice = null;
		while(true){
			out.flush();
			try {
				out.println("Enter Amount: "  );
				String userInput = in.nextLine();
				userChoice = Double.parseDouble(userInput);
				break;
			} catch (ResourceAccessException e) {
				errorCannotConnect();
			} catch (RestClientResponseException e) {
				errorClientException(e.getRawStatusCode() , e.getStatusText());
			}
		}
		return userChoice;
	}




}
