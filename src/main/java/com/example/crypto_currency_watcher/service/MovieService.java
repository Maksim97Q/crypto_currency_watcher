package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.entity.Movie;
import com.example.crypto_currency_watcher.repository.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MovieService {
    private final static String URL = "https://afisha.relax.by/kino/gomel/";
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Scheduled(fixedDelay = 15000)
    public void parserMovie() throws IOException {
        Document document = Jsoup.connect(URL).get();
        Element element = document.select("div[class=schedule__list]").first();
        Elements elements = Objects.requireNonNull(element).select(
                "div[class=schedule__table--movie__item]");
        System.out.println(element.select("h5[class=h5 h5--compact h5--bolder u-mt-6x]").text());
        for (Element elem : elements) {
            if (!elem.select("a[class=schedule__place-link link]").text().isEmpty()) {
                System.out.println("\nкинотеатр: " + elem.select("a[class=schedule__place-link link]").text());
            }
            Movie movie = new Movie();
            String time = elem.select("a[data-action=saleframe.bycard.by]").text();
            Double price = Double.parseDouble(elem.select("span[class=seance-price]").text()
                    .split(" ")[1]);
            String nameMovie = elem.select("div[class=schedule__event-block]").text();
            System.out.println(nameMovie + " - " + time + " " + price);
            movie.setName(nameMovie);
            movie.setPrice(price);
            movie.setTime(time);
            movieRepository.save(movie);
        }
    }
}
