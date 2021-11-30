package comom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorsUtil {

    private static List<String> matchWordsErrors = new ArrayList<>();
    private static List<String> denyWordErrors = new ArrayList<>();

    public static void addMatchWordsError(String error){
        matchWordsErrors.add(error);
    }

    public static void addDenyWordError(String error){
        denyWordErrors.add(error);
    }

    public static void createErrorsOutput(String reportsPath, String productName) throws IOException {

        File dir = new File(reportsPath + "/errors");
        if (!dir.exists()) {
            dir.mkdir();
        }

        if(!matchWordsErrors.isEmpty()) {
            File file = new File(dir, "/" + productName + "_matchWordsErrors.log");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(matchWordsErrors.stream().collect(Collectors.joining(System.lineSeparator())));

            bw.flush();
            bw.close();
            matchWordsErrors.clear();
        }

        if(!denyWordErrors.isEmpty()){
            File file = new File(dir, "/" + productName + "_denyWordErrors.log");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(denyWordErrors.stream().collect(Collectors.joining(System.lineSeparator())));

            bw.flush();
            bw.close();
            denyWordErrors.clear();
        }
    }
}
