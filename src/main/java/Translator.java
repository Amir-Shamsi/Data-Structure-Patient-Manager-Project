import java.util.Scanner;

public class Translator {
    Scanner scanner;
    DataSystem dataSystem;
    public Translator(Scanner scanner, DataSystem dataSystem){
        this.scanner = scanner;
        this.dataSystem = dataSystem;
    }
    public void Translate(String data){
        if(data.toLowerCase().contains("add")){
            int[] patientData;
            try {
                patientData = parsInteger(data.substring(4));
                dataSystem.Add(patientData[0], patientData[1]);
            }catch (Exception e){
                System.out.println(TextColors.RED + "No Valid Data for \"Add\"" + TextColors.RESET);
            }
        }
        else if(data.toLowerCase().contains("update")){
            int[] patientData;
            try {
                patientData = parsInteger(data.substring(7));
                dataSystem.Update(patientData[0], patientData[1]);
            }catch (Exception e){
                System.out.println(TextColors.RED + "No Valid Data for \"Update\"" + TextColors.RESET);
            }
        }
        else if(data.toLowerCase().contains("serve sickest")) {
            dataSystem.ServeSickest();
        }
        else if(data.toLowerCase().contains("serve first")){
            dataSystem.ServeFirst();
        }
        else if (data.toLowerCase().contains("exit") || data.toLowerCase().contains("-1")){
            System.exit(0);
        }
        else{
            System.out.println(TextColors.RED + "No valid keyword, buddy!" + TextColors.RESET);
        }
    }

    private int[] parsInteger(String substring) {
        int [] result = new int[2];
        String[] numbers = substring.split(" ");
        result[0] = Integer.parseInt(numbers[0]);
        result[1] = Integer.parseInt(numbers[1]);
        return result;
    }
}
