import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner =new Scanner(System.in);
        DataSystem dataSystem = new DataSystem();
        Translator translator = new Translator(scanner, dataSystem);

        while (true){
            translator.Translate(scanner.nextLine());
        }

//        File myObj = new File("C://file.txt");
//        Scanner myReader = new Scanner(myObj);
//        while (myReader.hasNextLine()) {
//            String data = myReader.nextLine();
//            translator.Translate(data);
//        }

    }
}
