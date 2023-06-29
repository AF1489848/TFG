

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        String scriptPrefix = "job_script"; // Prefijo para los nombres de los scripts
        String outputDirectory = "./target/Scripts"; // Directorio de salida para los scripts


        String scriptName = scriptPrefix + ".sub";
        String scriptPath = outputDirectory + "/" + scriptName;
        ScriptSlurm.ScriptGeneration(scriptPath);

    }




}