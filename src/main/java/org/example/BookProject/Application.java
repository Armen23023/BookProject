package org.example.BookProject;

import org.example.BookProject.exceptions.UserAlreadyExistsException;
import org.example.BookProject.exceptions.UserNotFoundException;
import org.example.BookProject.models.Book;
import org.example.BookProject.models.User;
import org.example.BookProject.service.BookService;
import org.example.BookProject.service.UserService;

import java.util.Scanner;

public class Application {

    private Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private UserService userService = new UserService();
    private BookService bookService = new BookService();


    public void start() {
        boolean findNormalCommand = false;
        while (!findNormalCommand) {
            System.out.println("Գրանցման համար սեղմեք։ 1");
            System.out.println("Մուտք գործելու համար սեղմեք։ 2");
            System.out.println("Ծրագիրը փակելու համար սեղմեք։ 3");
            String command = scanner.nextLine();
            switch (command) {
                case "1": {
                    registration();
                    break;
                }
                case "2": {
                    login();
                    break;
                }
                case "3": {
                    System.exit(1);
                }
                case "6":{
                    System.out.println("Չէ դու ձիգ ես ջոգում !!!!");
                }
                default: {
                    System.out.println("Խնդրում ենք օգտագործեք միայն նշված թվերը");
                }
            }

        }
    }

    private void login() {
        System.out.println(" ** Login process **");

        System.out.println("Մուտքագրեք ձեր Էլ․փոստը");
        String email = scanner.nextLine();

        System.out.println("Մուտքագրեք ձեր գաղտնաբառը");
        String password = scanner.nextLine();

        try {
            currentUser = userService.login(email, password);
            System.out.println("** Login success **");
            logService();


        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());

        }
    }



    private void registration() {
        System.out.println(" ** Registration process **");


        System.out.println("Մուտքագրեք ձեր անունը");
        String name = scanner.nextLine();

        System.out.println("Մուտքագրեք ձեր ազգանունը");
        String surname = scanner.nextLine();

        System.out.println("Մուտքագրեք ձեր էլ․փոստը");
        String email = scanner.nextLine();

        System.out.println("Մուտքագրեք ձեր գաղտնաբառը");
        String password = scanner.nextLine();

        try {
            currentUser = userService.registration(User.builder().name(name).surname(surname).email(email)
                    .password(password).build());
            System.out.println("։Գրանցումը հաջողությամբ ավարտվեց։");
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());

        }
    }
    private void logService() {
        System.out.println("** WELCOME TO THE LIBRARY **");
        System.out.println( "\n" +"\t\t" + currentUser.getName()+ "  " +currentUser.getSurname() +"\n" );
        boolean exit = false;
        while (!exit) {
            System.out.println("Ձեր գրքերը տեսնելու համար սեղմեք։ 1");
            System.out.println("Բոլոր գրքերը տեսնելու համար սեղմեք։ 2");
            System.out.println("Գիրք ավելացնելու համար սեղմեք։  3");
            System.out.println("Ձեր հաշվից դուրս գալու համար սեղմեք։  4");
            scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            switch (command) {
                case "1": {
                    currentUserBooks();
                    break;
                }
                case "2": {
                    printAllBooks();
                    break;
                }
                case "3": {
                    addBook();
                    break;
                }
                case "4": {
                    System.out.println("Bye");
                    exit = true;
                    break;
                }
                default: {
                    System.out.println("Please input 1 , 2 , 3 or 4");
                }
            }
        }
    }



    private void printAllBooks() {
        bookService.printAllbooks();
    }

    private void currentUserBooks() {
        bookService.currentUserBook(currentUser);
    }
    private void addBook() {
        System.out.println(" ** Book Adding **");

        System.out.println("Մուտքագրեք վեռնագիրը");
        String name = scanner.nextLine();

        int pageCount = defineNumberValue("Մուտքագրեք էջերի քանակը");

        int year = defineNumberValue("Մուտքագրեք գրքի տարեթիվը");

        bookService.addBook(Book.builder().name(name).year(year).pageCount(pageCount).userid(currentUser.getId()).build());
        System.out.println(" ** Շնորհավոր ձեր գիրքը առկա է համակարգում **");
    }

    private int defineNumberValue(String text) {
        boolean isNumberDefined = false;

        int year = 0;
        while (!isNumberDefined) {
            try {
                scanner = new Scanner(System.in);
                System.out.println(text);
                year = scanner.nextInt();
                isNumberDefined = true;
            } catch (Exception e) {
                System.out.println("Please input only numeric value");
            }
        }
        return year;

    }
}
