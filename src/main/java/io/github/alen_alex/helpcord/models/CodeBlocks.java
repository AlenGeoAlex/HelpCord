package io.github.alen_alex.helpcord.models;

import io.github.alen_alex.helpcord.enums.CodeLanguage;
import io.github.alen_alex.helpcord.enums.Tags;
import okhttp3.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class CodeBlocks {

    private final String rawMessage;
    private final String parsedMessage;
    private final CodeLanguage language;
    private static final OkHttpClient client = new OkHttpClient();

    public CodeBlocks(String rawMessage, String parsedMessage, CodeLanguage language) {
        this.rawMessage = rawMessage;
        this.parsedMessage = parsedMessage;
        this.language = language;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public String getParsedMessage() {
        return parsedMessage;
    }

    public CodeLanguage getLanguage() {
        return language;
    }

    public CompletableFuture<Optional<String>> upload(){
        return CompletableFuture.supplyAsync(new Supplier<Optional<String>>() {
            @Override
            public Optional<String> get() {

                RequestBody formBody = new FormBody.Builder()
                        .add("content", parsedMessage)
                        .build();

                System.out.println("text/"+language.getLanguage());

                Request request = new Request.Builder()
                        .url(Tags.PASTE_API.getTag())
                        .post(formBody)
                        .headers(
                                new Headers.Builder()
                                        .add("Content-Type", "text/"+language.getLanguage())
                                        .add("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0")
                                        .build()
                        )
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful())
                        return Optional.of(response.headers().get("Location"));
                    else return Optional.empty();
                }catch (Exception e){
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        });
    }
}
