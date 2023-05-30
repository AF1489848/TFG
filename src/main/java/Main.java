import static Functions.generarScriptSlurm.generarScript;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        String scriptPrefix = "job_script"; // Prefijo para los nombres de los scripts
        String outputDirectory = "C:\\Users\\aleja\\OneDrive - UAB\\TFG\\TFG_ScriptsAutom\\target\\Scripts"; // Directorio de salida para los scripts


        String scriptName = scriptPrefix + ".sub";
        String scriptPath = outputDirectory + "/" + scriptName;
        generarScript(scriptPath);

    }




}