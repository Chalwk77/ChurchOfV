// Copyright (c) 2023, Jericho Crosby <jericho.crosby227@gmail.com>

package com.chalwk.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class FileIO {

    static String programPath = getProgramPath();

    public static String getProgramPath() {
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory = currentDirectory.replace("\\", "/");
        return currentDirectory;
    }

    private static void checkExists(File file) throws IOException {
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Failed to create file: " + file);
        }
    }

    public static String readFile(String fileName) throws IOException {

        File f = new File(programPath + "/" + fileName);
        checkExists(f);

        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();

        while (line != null) {
            stringBuilder.append(line);
            line = reader.readLine();
        }

        reader.close();
        return stringBuilder.toString();
    }

    public static JSONArray getJSONArray(String fileName) throws IOException {
        String content = readFile(fileName);
        if (content.equals("")) {
            return new JSONArray();
        } else {
            return new JSONArray(content);
        }
    }

    public static JSONObject getJSONObject(String fileName) throws IOException {
        String content = readFile(fileName);
        if (content.equals("")) {
            return new JSONObject();
        } else {
            return new JSONObject(content);
        }
    }

    public static void writeJSONArray(JSONArray json, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(programPath + "/" + fileName);
        fileWriter.write(json.toString(4));
        fileWriter.flush();
        fileWriter.close();
    }
}
