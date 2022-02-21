import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Antiduplicator {
    public static void main(String[] args) throws IOException {
        Antiduplicator antiduplicator = new Antiduplicator();
        Path pathTargetDirectory = Paths.get("src/resources/1");
        Path pathMovedFiles = Paths.get("src/resources/moved");
        if (!Files.exists(pathMovedFiles)) Files.createDirectories(pathMovedFiles);
        antiduplicator.doAntidup(pathTargetDirectory, pathMovedFiles);
    }

    private void doAntidup(Path pathTargetDirectory, Path pathMovedFiles) throws IOException {
        List<Path> files = Files.list(pathTargetDirectory).collect(Collectors.toList());
        for (int i = 0; i < files.size(); i++) {
            for (int j = i + 1; j < files.size(); j++) {
                Path fileA = files.get(i);
                Path fileB = files.get(j);

                if (!(Files.exists(fileA) && Files.exists(fileB))) continue;

                if ((Files.size(fileA) == Files.size(fileB)) && isCognate(fileA, fileB)) {
                    String nameFileA = fileA.getFileName().toString();
                    String nameFileB = fileB.getFileName().toString();
                    if (nameFileA.length()>nameFileB.length()) moveFile(fileA, pathMovedFiles);
                    else moveFile(fileB, pathMovedFiles);
                }
            }
        }
    }

    private boolean isCognate(Path fileA, Path fileB) throws IOException {
        String nameFileA = fileA.getFileName().toString();
        String nameFileB = fileB.getFileName().toString();
        System.out.println(nameFileA + " equals " + nameFileB);
        return nameFileA.substring(0, 7).equals(nameFileB.substring(0, 7));
    }

    private void moveFile (Path source, Path target) throws IOException {
        String nameFile = source.getFileName().toString();
        Path newNameFile = Paths.get(String.valueOf(target), nameFile);
        System.out.println("The file \"" + nameFile + "\" is moved");
        Files.move(source, newNameFile);
    }

}
