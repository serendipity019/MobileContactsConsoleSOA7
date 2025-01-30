package gr.aueb.cf.mobile_contacts;

import gr.aueb.cf.mobile_contacts.controler.MobileContactController;
import gr.aueb.cf.mobile_contacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobile_contacts.model.MobileContact;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final MobileContactController controller = new MobileContactController();

    public static void main(String[] args) {
        String choice;

        //because is one program that will run forever
        while (true) {
            printMenu();
            choice = getToken();

            if (choice.equals("q") || choice.equals("Q")) {
                break;
            }

            handleChoice(choice);
        }

        System.out.println("thank you for using Mobile Contact");
    }

    public static void printMenu() {
        System.out.println("Choose one from below:");
        System.out.println("1. Insert contact");
        System.out.println("2. Update contact");
        System.out.println("3. Delete contact");
        System.out.println("4. Search contact");
        System.out.println("5. Show contact");
        System.out.println("Q/q. Exit");
    }

    public static String getToken() {
        return sc.nextLine().trim();
    }

    public static void handleChoice(String choice) {
        String firstname;
        String lastname;
        String phoneNumber;
        String response;
        switch (choice) {
            case "1":
                System.out.println("Please insert Name, Surname, Phone number");
                firstname = getToken();
                lastname = getToken();
                phoneNumber = getToken();
                MobileContactInsertDTO insertDTO = new MobileContactInsertDTO(firstname, lastname, phoneNumber);
                response = controller.insertContact(insertDTO);

                if (response.startsWith("OK")) {
                    System.out.println("Successful insert");
                    System.out.println(response.substring(3));
                } else {
                    System.out.println("Unsuccessful insert");
                    System.out.println(response.substring(7));
                }
                break;
            case "2":
                //
                break;
            case "3":
                //
                break;
            case "4":
                //
                break;
            case "5":
                //
                break;
            default:
                //
                break;
        }
    }
}
