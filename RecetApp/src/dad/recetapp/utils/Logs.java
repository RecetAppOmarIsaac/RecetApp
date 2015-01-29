package dad.recetapp.utils;

import dad.recetapp.services.items.RecetaItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class Logs {
    private static File logFile;

    private static void checkLogFile() {
        if (logFile == null) {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            String name = date.getYear() + "" + date.getMonthValue() + "" + date.getDayOfMonth() + "_" + time.getHour() + "" + time.getMinute() + "" + time.getSecond();
            logFile = new File(name + ".log");
        }
    }

    public static void log(Throwable e) {
        checkLogFile();
        LinkedList<String> logContents = new LinkedList<>();
        logContents.add("---- " + LocalTime.now() + " ----");
        if (e == null)
            logContents.add("Algo ha intentado meter una excepcion nula");
        else {
            logContents.add("Message: " + e.getMessage());
            logContents.add("Cause: " + e.getCause());
            logContents.add("** Stacktrace **");
            for (StackTraceElement stack : e.getStackTrace()) {
                String s = "Line " + stack.getLineNumber() + " in " + stack.getFileName() + ". " + stack.getClassName() + "." + stack.getMethodName();
                logContents.add(s);
            }
        }
        logContents.add("--------------");
        try {
            Files.write(logFile.toPath(), logContents, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        catch (IOException e1) {
            System.err.println("El sistema de logs ha lanzado una excepcion. Mensaje: " + e1.getMessage() + " causa: " + e1.getCause());
        }
    }

    public static void dataDump(RecetaItem ri) {
        //TODO quitar esto al terminar de depurar
        System.out.println("----data dump----");
        System.out.println("Nombre: " + ri.getNombre());
        System.out.println("Para " + ri.getCantidad() + " " + ri.getPara());
        System.out.println("Secciones.len: " + ri.getSecciones().size());
        ri.getSecciones().forEach(seccionItem -> {
            System.out.println("{");
            System.out.println("SeccionItem: " + seccionItem.getNombre() + " id: " + seccionItem.getId());
            System.out.println("Ingreds.len: " + seccionItem.getIngredientes().size());
            seccionItem.getIngredientes().forEach(item -> System.out.println(item.getTipo().getNombre() + " " + item.getCantidad()));
            System.out.println("Instrucc.len: " + seccionItem.getInstrucciones().size());
            seccionItem.getInstrucciones().forEach(item -> System.out.println(item.getDescripcion()));
            System.out.println("}");
        });
        System.out.println("------------------");
    }
}

