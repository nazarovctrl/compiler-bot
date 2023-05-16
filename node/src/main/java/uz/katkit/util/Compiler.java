package uz.katkit.util;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import uz.katkit.dto.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class Compiler {

    public String compiler(String code, String language, String versionIndex) {

        String clientId = "43a62af4f71b91d7b84f9456be9fcfbc"; //Replace with your client ID
        String clientSecret = "3549872b9c898a14e32090094341d9b4f0d5515af9d4346396834456915f130f"; //Replace with your client Secret
        String script = CodeUtil.escapeString(code);
        script = script.replace("\u00a0","");

        System.out.println(code);

        System.out.println("\n\n\n");

        System.out.println(script);

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