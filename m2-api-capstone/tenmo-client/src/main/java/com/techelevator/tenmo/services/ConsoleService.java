package com.techelevator.tenmo.services;


import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.auth.models.User;
import com.techelevator.tenmo.auth.models.UserCredentials;
import com.techelevator.tenmo.models.Account;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

	private Account account;
	private PrintWriter out;
	private Scanner in;
	private UserCredentials userCredentials;
	private AuthenticatedUser currentUser = new AuthenticatedUser();

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
		out.println("Cannot connect to server.");
		out.flush();
	}

	public void errorClientAcception(int statusCode , String message){
		out.println(statusCode + " " + message);
		out.flush();
	}

	public void showAllUsersExceptCurrentUser(List<User> userList){
		out.println("_________________________________________");
		out.println("Users");
		out.println("ID             Name");
		out.println("_________________________________________");

		for (int i = 0;  i < userList.size(); i ++ ){
//			if (userList.get(i).getId().equals(currentUser.getUser().getId())){
//				continue;
//			}
			out.println(userList.get(i).getId() + "          " +  userList.get(i).getUsername());
		}
	}
}
