import java.io.File;
import java.util.Objects;

public class ProjectOutput {

    public static void main(String[] args) {
        String indent = "";
        File root = new File(System.getProperty("user.dir"));
        print(root, indent);
    }

    private static void print(File root, String indent) {
        if (root.isDirectory()) {
            System.out.printf("%s DIR %s\n", indent, root);
            indent = String.format("%s  ", indent);
            for (File file : Objects.requireNonNull(root.listFiles())) {
                print(file, indent);
            }
        } else {
            System.out.printf("%sfile %s\n", indent, root);
        }

    }

}
