import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BahaWood on 1/22/19.
 */

public class DollarToTenge extends TelegramLongPollingBot {

    public String getBotToken() {
        return "631663765:AAF8pWDJCuO2eYhvvjjW31Nj0r5sxXfNahw";
    }

    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        String command = update.getMessage().getText();

        if(command.equals("/usdtokzt")) {
            message.setText(getUsdToKzt());
        }

        message.setChatId(update.getMessage().getChatId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return "Dollar To Tenge";
    }

    public String getUsdToKzt(){

        String url = "http://localhost:8080/getCourse";
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject myResponse = new JSONObject(response.toString());
            String usd = myResponse.get("rate").toString();
            return usd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Bot doesn't work, now";
    }
}
