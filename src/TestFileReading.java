import java.io.File;

public class TestFileReading {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        /*
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

         */
        printDirFiles(System.getProperty("user.dir"), "");




    }

    private static void printDirFiles(String filePath, String indent) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            System.out.printf("%s%s %s\n", indent, "Dir:", file.getAbsolutePath());
            indent = String.format("  %s", indent);
            String[] list = file.list();
            if (list != null) {
                for (String name : list) {
                    printDirFiles(filePath + "\\" + name, indent);
                }
            }
        } else {
            System.out.printf("%s%s %s\n", indent, "File:", file.getName());
        }
    }


}
