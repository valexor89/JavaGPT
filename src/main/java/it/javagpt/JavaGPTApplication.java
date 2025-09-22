package it.javagpt;
import it.javagpt.OpenAIClient;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class JavaGPTApplication extends Application {
    // Metodo di utilità per estrarre la risposta dal JSON OpenAI
    private String estraiRisposta(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            // Naviga nel JSON per trovare la risposta (semplificato)
            return obj.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } catch (Exception e) {
            return "[Risposta non disponibile]";
        }
    }

    @Override
    public void start(Stage stage) {
        javafx.scene.control.TextArea chatArea = new javafx.scene.control.TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        javafx.scene.control.TextField inputField = new javafx.scene.control.TextField();
        inputField.setPromptText("Scrivi un messaggio...");

        Button sendButton = new Button("Invia");

    // Istanza OpenAIClient: la chiave viene letta dalla variabile d'ambiente OPENAI_API_KEY
    String apiKey = System.getenv("OPENAI_API_KEY");
    OpenAIClient openAIClient = new OpenAIClient(apiKey);

        sendButton.setOnAction(e -> {
            String userMessage = inputField.getText();
            if (!userMessage.isEmpty()) {
                chatArea.appendText("Tu: " + userMessage + "\n");
                inputField.clear();

                // Chiamata asincrona a OpenAI
                new Thread(() -> {
                    try {
                        String rispostaOpenAI = openAIClient.callChatAPI(userMessage);
                        // Estrai solo il testo della risposta (semplificato)
                        String reply = estraiRisposta(rispostaOpenAI);
                        javafx.application.Platform.runLater(() -> {
                            chatArea.appendText("ChatGPT: " + reply + "\n");
                        });
                    } catch (Exception ex) {
                        javafx.application.Platform.runLater(() -> {
                            chatArea.appendText("[ERRORE chiamata OpenAI]\n");
                        });
                    }
                }).start();
            }
        });

        // Metodo di utilità per estrarre la risposta dal JSON
        // (Da migliorare secondo il formato reale della risposta OpenAI)
        // Puoi spostare questo metodo in una classe separata se preferisci
        // Qui è solo un esempio semplificato
        //
        // private String estraiRisposta(String json) { ... }

            javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10, chatArea, inputField, sendButton);
            vbox.setPadding(new javafx.geometry.Insets(10));

            stage.setTitle("JavaGPT Chat");
            stage.setScene(new Scene(vbox, 500, 400));
            stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}