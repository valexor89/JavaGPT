package it.javagpt;

import okhttp3.*;

public class OpenAIClient {
    private final String apiKey;
    private final OkHttpClient client;

    public OpenAIClient(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    public String callChatAPI(String prompt) throws Exception {
        String url = "https://api.openai.com/v1/chat/completions";
        String json = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}] }";
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + apiKey)
            .post(body)
            .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
