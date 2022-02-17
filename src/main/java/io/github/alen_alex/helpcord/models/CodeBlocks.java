package io.github.alen_alex.helpcord.models;

import io.github.alen_alex.helpcord.enums.CodeLanguage;
import io.github.alen_alex.helpcord.enums.Tags;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public final class CodeBlocks {

    private final String rawMessage;
    private final String parsedMessage;
    private final CodeLanguage language;
    private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();


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
                final HttpPost post = new HttpPost(Tags.PASTE_API.getTag());

                post.setHeader("Content-Type",language.getLanguage());
                post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");

                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("HelpCord", parsedMessage));
                try {
                    post.setEntity(new UrlEncodedFormEntity(pairs ));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }

                try {
                    HttpResponse response = HTTP_CLIENT.execute(post);
                    System.out.println(response.getFirstHeader("Location"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }


                return Optional.empty();
            }
        });
    }
}
