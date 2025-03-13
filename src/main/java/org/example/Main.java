package org.example;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        String inp;

        inp = scanner.nextLine();

        String defaultURL = "https://api.github.com/users/";

        String URL = defaultURL + inp + "/events";

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Authorization", Constants.TOKEN)
                .header("Accept", Constants.ACCEPT)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        Activity activity = new Activity();

        Gson gson = new Gson();

        Activity[] events = gson.fromJson(getResponse.body(), Activity[].class);

        output(events);

    }

    public static void output(Activity[] events) {
        for (int i = 0; i < events.length; i++) {

            String eventName = events[i].getType();

            switch (eventName) {
                case "PushEvent":
                    System.out.print("Pushed ");
                    System.out.print(events[i].getPayload().getCommits().size());
                    System.out.print(" commits to ");
                    System.out.println(events[i].getRepo().getName());
                    break;
                case "WatchEvent":
                    System.out.print("Starred ");
                    System.out.println(events[i].getRepo().getName());
                    break;
                case "IssuesEvent":
                    System.out.print("Opened a new issue in ");
                    System.out.println(events[i].getRepo().getName());
                    break;
            }

        }
    }
}