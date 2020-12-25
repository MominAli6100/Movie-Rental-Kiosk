// Name:Ali Momin
// NetID:ASM180014
//package com.company;

// imports for the program
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    // main method
    public static void main(String[] args) throws IOException {

        // declaring and initalizing variables to be used in the method
        BSTree Kiosk = new BSTree();
        Scanner fileScanner = null;
        Scanner fileTester = null;
        Scanner inputScanner = new Scanner(System.in);
        File errorLog = new File("error.log");
        errorLog.createNewFile();
        FileWriter errorWriter = new FileWriter(errorLog);
        String inventoryFileName = "";
        String transactionFileName = "";
        boolean notFile = false;

        // while loop checks to see if the file that the user enters exists and keeps looping and asking the user until
        // they enter a valid file
        while(!notFile)
        {
            // try/catch block used for file validation
            try
            {
                // prompting the user for a file name and collecting it
                System.out.println("What is the name of the inventory file?");
                inventoryFileName = inputScanner.nextLine();
                // fileScanner created to test and see if the file exists, if it does then set notFIle to true
                fileTester = new Scanner(new File(inventoryFileName));
                notFile = true;
            }
            catch(Throwable exception)
            {
                // if the file does not exist an error will be thrown and the program will enter this catch block where
                // the prompt below will be printed
                System.out.println("Error: Could not Find file, please enter a valid file name");
            }
        }

        // setting notFIle, the control varaible for the while loop to false
        notFile = false;
        // checking to see if the transaction file that the user entered exists, if it doesn't then the while loop will
        // loop and ask the user until they find a valid file
        while(!notFile)
        {
            // try/catch block used for file validation
            try
            {
                // prompting the user to enter a transaction log file and collecting the file name
                System.out.println("What is the name of the transaction log?");
                transactionFileName = inputScanner.nextLine();
                // fileScanner created to test and see if the file exists, if it does then set notFIle to true
                fileTester = new Scanner(new File(transactionFileName));
                notFile = true;
            }
            catch(Throwable exception)
            {
                // if the file doesn't exist an exception will be thrown and the code will enter the catch which will
                // prompt them with this error
                System.out.println("Error: Could not Find file, please enter a valid file name");
            }
        }

        // String variable created to hold the lines from the transaction log file
        String transactionLineHolder = "";

        // creating a BST by passing it the inventory file along with a scanner object and the kiosk
        createBST(Kiosk, inventoryFileName, fileScanner);

        // creating a file scanner to iterate through the transaction log file
        fileScanner = new Scanner(new File(transactionFileName));
        // while the scanner has another line
        while(fileScanner.hasNextLine())
        {
            // take that line and store it inside of transactionLineHolder
            transactionLineHolder = fileScanner.nextLine();
            // and call the processOrder method while passing it that line, the kiosk, the errorLog file and a writer
            // to write to that error log file
            processOrder(Kiosk, transactionLineHolder, errorLog, errorWriter);
        }
        // create a BST traverser node and set it to the first title in the kiosk
        Node BSTraversor = Kiosk.getRoot();
        // call the write report function and pass it the root
        writeReport(BSTraversor);
        // close the fileWriter
        errorWriter.close();

    }

    // createBST function that will take the titles and data from the inventory and create a BST out of them, takes
    // 3 parameters: the kiosk, the fileName, and the fileScanner
    public static void createBST(BSTree kiosk, String fileName, Scanner fileScanner) throws FileNotFoundException {

        // creation of variables and objects that will be used in the method
        fileScanner = new Scanner(new File(fileName));
        DVD currentDVD;
        Node currentNode;
        Node printerNode;
        String tempHolder = " ";
        String title = " ";
        int qAvailable = 0;
        int qRented = 0;
        int counter = 0;

        // while the inventory file has a line
        while(fileScanner.hasNextLine())
        {
            // take that line and store it inside of tempHolder
            tempHolder = fileScanner.nextLine();
            // and find the index of the first comma and store that inside of a variable
            int firstQuote = tempHolder.indexOf(',');

            // use the firstComma index and find a substring inside of tempHolder that has the title and store it inside
            // of title
            title = tempHolder.substring(1, firstQuote - 1);
            // use the firstComma index and the length of the string to find the quantity available and store it inside
            // of qAvailable
            qAvailable = Integer.parseInt(tempHolder.substring(firstQuote + 1, tempHolder.length()-2));
            // use the length to find the quantity rented and store that inside of qRented
            qRented = Integer.parseInt(String.valueOf(tempHolder.charAt(tempHolder.length()-1)));

            // using variable above create a DVD object
            currentDVD = new DVD(title, qAvailable, qRented);
            // then create a node object and pass it the DVD object
            currentNode = new Node(currentDVD);

            // if the counter equals 0
            if(counter == 0)
            {
                // then set the node as the root
                kiosk.setRoot(currentNode);
            }
            else
            {
                // otherwise insert the node in the BST
                kiosk.BSTInsert(currentNode);
            }
            // increment counter
            counter++;
        }
    }

    // processOrder method takes 4 parameters: the kiosk, transactionLine, errorLog file, and errorWriter that writes to
    // the errorLog and processes the orders based on what the transaction file says, or writes to the errorlog
    public static void processOrder(BSTree Kiosk, String transactionLineHolder, File errorLog, FileWriter errorWriter) throws IOException {
        // creation and initialization of variables that will be used in the method
        String doWhat = transactionLineHolder.substring(0, transactionLineHolder.indexOf(" "));
        String filmTitle = "";
        int removalOrAddAmt = 0;
        int firstCommaIndex;
        int secondCommaIndex;
        Node BSTTraversor = null;

        // check to see if the first word in each line is one of the ones below
        if(doWhat.equals("rent") || doWhat.equals("add") || doWhat.equals("return") || doWhat.equals("remove"))
        {
            // if it is then check to see if the line ends with a quotation mark
            if(transactionLineHolder.charAt(transactionLineHolder.length()-1) == '"')
            {
                // if it does then use this algorithm to find and store the filmTitle
                filmTitle = transactionLineHolder.substring(transactionLineHolder.indexOf('"') + 1, transactionLineHolder.length()-1);
            }
            else
            {
                // if it does not then use the following algorithm to find and store the filmTitle
                filmTitle = transactionLineHolder.substring(transactionLineHolder.indexOf('"') + 1, transactionLineHolder.length()-3);
            }

            // check to see if the line ends with a quatation mark
            if(transactionLineHolder.charAt(transactionLineHolder.length()-1) == '"')
            {
                // if it does then set the add or removal amount to 1
                removalOrAddAmt = 1;
            }
            else
            {
                // if it does not then find the index position of the first comma and the second comma
                firstCommaIndex = transactionLineHolder.indexOf(',');
                secondCommaIndex = transactionLineHolder.indexOf(',',firstCommaIndex+1);

                // if there is no first comma
                if(firstCommaIndex == -1)
                {
                    // write to the errorLog and leave the function
                    writeErrorLog(transactionLineHolder, errorLog, errorWriter);
                    return;
                }
                // if there's no second comma
                else if(secondCommaIndex == -1)
                {
                    // check to see if there is a value after the first comma
                    if(transactionLineHolder.substring(firstCommaIndex+1, transactionLineHolder.length()).equals(""))
                    {
                        // if not then write to the errorLog
                        writeErrorLog(transactionLineHolder, errorLog, errorWriter);
                    }
                    else
                    {
                        // if there is then store that value in removal or add amount
                        removalOrAddAmt = Integer.parseInt(transactionLineHolder.substring(firstCommaIndex+1, transactionLineHolder.length()));
                    }

                }
                //otherwise
                else
                {
                    // take the value between the first and second comma and store it inside of removal or add amount
                    removalOrAddAmt = Integer.parseInt((transactionLineHolder.substring(firstCommaIndex + 1,secondCommaIndex)));
                }
            }

            // using the title create a new node that has the DVD object passed to it
            BSTTraversor = new Node(new DVD(filmTitle, 0, 0));
        }

        // if the line begins with "rent"
        if(doWhat.equals("rent"))
        {
            // search the kiosk using BSTSearch for the fileName
            BSTTraversor = Kiosk.BSTSearch(BSTTraversor);

            // if the movie name is not in the kiosk
            if(BSTTraversor == null)
            {
                // write to the error log
                writeErrorLog(transactionLineHolder, errorLog, errorWriter);
            }
            else
            {
                // if it is then check to see if it ends with a quatation mark
                if(transactionLineHolder.charAt(transactionLineHolder.length()-1) != '"')
                {
                    // if it does then write to the error log
                    writeErrorLog(transactionLineHolder, errorLog, errorWriter);
                }
                else
                {
                    // if it doesn't, check to see if the add or removal amount is 0
                    if(removalOrAddAmt == 0)
                    {
                        // if it is then do the rental transaction with a value of 1
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()-1);
                        ((DVD)BSTTraversor.getPayload()).setRented(((DVD)BSTTraversor.getPayload()).getRented()+1);
                    }
                    else
                    {
                        // otherwise do the rental transaction with the value inside of add or removeal amount
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()-removalOrAddAmt);
                        ((DVD)BSTTraversor.getPayload()).setRented(((DVD)BSTTraversor.getPayload()).getRented()+removalOrAddAmt);
                    }
                }
            }
        }
        // otherwise, if the first word is "add"
        else if(doWhat.equals("add"))
        {
            // create a temporary node and pass it the values inside of BSTTraversor
            Node tempHolder = BSTTraversor;
            // and search the kiosk for the movie
            BSTTraversor = Kiosk.BSTSearch(BSTTraversor);

            // find the index positions of the first comma and the second comma
            firstCommaIndex = transactionLineHolder.indexOf(',');
            secondCommaIndex = transactionLineHolder.indexOf(',',firstCommaIndex+1);

            // if the second comma is there
            if(secondCommaIndex != -1)
            {
                // write to the error log
                writeErrorLog(transactionLineHolder, errorLog, errorWriter);
            }
            else {
                // otherwise check and see if the movie doesn't exist in the kiosk
                if(BSTTraversor == null)
                {
                    // if it doesn't then check to see if the removal or add amount is not 0
                    if(removalOrAddAmt == 0)
                    {
                        // if it is then do the addition transaction with 1
                        ((DVD)tempHolder.getPayload()).setAvailable(1);
                        ((DVD)tempHolder.getPayload()).setRented(0);
                    }
                    else {
                        // otherwise do it with removalOrAddAMount
                        ((DVD)tempHolder.getPayload()).setAvailable(removalOrAddAmt);
                        ((DVD)tempHolder.getPayload()).setRented(0);
                    }
                    // and insert tempHolder into the BST
                    Kiosk.BSTInsert(tempHolder);
                }
                else {
                    // if the move does exist go straight to the addition transaction and do it based on the condition above
                    if(removalOrAddAmt == 0)
                    {
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()+1);
                    }
                    else {
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()+removalOrAddAmt);
                    }
                }
            }
        }
        // if the first word is "return"
        else if(doWhat.equals("return"))
        {
            // search the kiosk for the movie
            BSTTraversor = Kiosk.BSTSearch(BSTTraversor);

            // if you can't find it
            if(BSTTraversor == null)
            {
                // write the line to the error log
                writeErrorLog(transactionLineHolder, errorLog, errorWriter);
            }
            // if you od find it
            else
            {
                // check to see if it ends with a quatation mark
                if(transactionLineHolder.charAt(transactionLineHolder.length()-1) != '"')
                {
                    // if it doesn't then write to error log
                    writeErrorLog(transactionLineHolder, errorLog, errorWriter);
                }
                else
                {
                    // if it does then search the kiosk for the move name
                    BSTTraversor = Kiosk.BSTSearch(BSTTraversor);
                    // and check to see what the removal and add amount is
                    if(removalOrAddAmt == 0)
                    {
                        // if 0 then do the transaction with 1
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()+1);
                        ((DVD)BSTTraversor.getPayload()).setRented(((DVD)BSTTraversor.getPayload()).getRented()-1);
                    }
                    else
                    {
                        // if not 0 then do the transaction with addOrRemovalAmt
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()+removalOrAddAmt);
                        ((DVD)BSTTraversor.getPayload()).setRented(((DVD)BSTTraversor.getPayload()).getRented()-removalOrAddAmt);
                    }
                }
            }
        }
        // if the first word is "remove"
        else if(doWhat.equals("remove"))
        {
            // set tempHolder to the movie in BST traversor and search the kiosk for the movie
            Node tempHolder = BSTTraversor;
            BSTTraversor = Kiosk.BSTSearch(BSTTraversor);

            // if you don't find it
            if(BSTTraversor == null)
            {
                // write to the error log
                writeErrorLog(transactionLineHolder, errorLog, errorWriter);
            }
            else
            {
                // if you do find it then looks for the index positions of the first and second comma
                firstCommaIndex = transactionLineHolder.indexOf(',');
                secondCommaIndex = transactionLineHolder.indexOf(',',firstCommaIndex+1);

                // if there is a second comma
                if(secondCommaIndex != -1)
                {
                    // write to the error log
                    writeErrorLog(transactionLineHolder, errorLog, errorWriter);
                }
                else
                {
                    // otherwise check the AddOrRemovalAmt
                    if(removalOrAddAmt == 0)
                    {
                        // if it's 0 then remove one copy
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()-1);
                    }
                    else
                    {
                        // if not then remove addOrRemovalAMT copies
                        ((DVD)BSTTraversor.getPayload()).setAvailable(((DVD)BSTTraversor.getPayload()).getAvailable()-removalOrAddAmt);
                    }
                }
            }
        }
        // if it doesn't begin with any one of those
        else
        {
            // write to the error log
            writeErrorLog(transactionLineHolder, errorLog, errorWriter);
        }

        // check to see if the movie after the order exists
        if(BSTTraversor != null)
        {
            // check to see if the movie after the order has any rented or any availability
            if(((DVD)BSTTraversor.getPayload()).getAvailable() == 0 && ((DVD)BSTTraversor.getPayload()).getRented() == 0)
            {
                // if not then remove it
                Kiosk.BSTRemove(BSTTraversor);
            }
        }
    }

    // writeErrorLog function that takes 3 parameters: the errorLine, the errorLog file and the writer to it and
    // writes the line to the error log file
    public static void writeErrorLog(String errorLine, File errorLog, FileWriter errorWriter) throws IOException {
        // writing the error lines to the errorlog file
        errorWriter.write(errorLine);
        errorWriter.write("\n");
    }

    // writeReport function that takes a parameter, the root and prints report
    public static void writeReport(Node BSTTraversor)
    {
        // if the traversor is null then get out of the function
        if(BSTTraversor == null)
            return;
        // call the left side of the function
        writeReport(BSTTraversor.getLeft());
        // print out the values and their data
        System.out.print(String.format("%-32s", ((DVD)BSTTraversor.getPayload()).getTitle()));
        System.out.format("%-7d", ((DVD)BSTTraversor.getPayload()).getAvailable());
        System.out.println(((DVD)BSTTraversor.getPayload()).getRented());
        // call the right side of the function
        writeReport(BSTTraversor.getRight());
    }

}
