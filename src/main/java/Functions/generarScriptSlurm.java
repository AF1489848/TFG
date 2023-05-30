package Functions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class generarScriptSlurm {
    public static void generarScript(String scriptPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptPath))) {
            writer.write("#!/bin/bash");
            writer.newLine();
            writer.write("#SBATCH --job-name=myjob"); // Nombre del trabajo
            writer.newLine();
            writer.write("#SBATCH --output=output.txt"); // Archivo de salida
            writer.newLine();
            writer.write("#SBATCH --nodes=1"); // Número de nodos
            writer.newLine();
            writer.write("#SBATCH --ntasks-per-node=1"); // Número de tareas por nodo
            writer.newLine();
            writer.write("#SBATCH --cpus-per-task=1"); // Número de CPUs por tarea
            writer.newLine();
            writer.write("#SBATCH --time=00:30:00"); // Tiempo máximo de ejecución
            writer.newLine();
            writer.write("#SBATCH --mem=1G"); // Memoria máxima por tarea
            writer.newLine();
            writer.write("echo 'Hello, world!'"); // Comando de ejecución del trabajo
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

