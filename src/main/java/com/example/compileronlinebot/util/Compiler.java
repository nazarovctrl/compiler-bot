package com.example.compileronlinebot.util;

import com.example.compileronlinebot.dto.Request;
import com.example.compileronlinebot.dto.Result;
import com.example.compileronlinebot.entity.RequestEntity;
import com.example.compileronlinebot.service.SendingService;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class Compiler {


    public String compiler(String code, String language, String versionIndex) {

        String clientId = "fbd6bc020025aa9370237accd1bfd13e"; //Replace with your client ID
        String clientSecret = "eda8750d90dbc6fcc65c1f4b6bdcf4c13ef0bbb35a26bbcfc350558a648e8b71"; //Replace with your client Secret
        String script = CodeUtil.escapeString(code);


        try {
            URL url = new URL("https://api.jdoodle.com/v1/execute");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            String input = "{\"clientId\": \"" + clientId + "\",\"clientSecret\":\"" + clientSecret + "\",\"script\":\"" + script +
                    "\",\"language\":\"" + language + "\",\"versionIndex\":\"" + versionIndex + "\"} ";


            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                connection.disconnect();
                return "Code could not run";
            }

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            StringBuilder text = new StringBuilder();
            while ((output = bufferedReader.readLine()) != null) {
                text.append(output);
            }

            Gson gson = new Gson();
            Result result = gson.fromJson(text.toString(), Result.class);


            connection.disconnect();

            if (result == null || !result.getStatusCode().equals(200)) {
                return "Could not run";
            }


            return result.getOutput();

        } catch (IOException e) {
            return "Code could not run";
        }

    }

}