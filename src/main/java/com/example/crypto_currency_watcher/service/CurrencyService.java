package com.example.crypto_currency_watcher.service;

import com.example.crypto_currency_watcher.entity.Currency;
import com.example.crypto_currency_watcher.repository.CurrencyRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurrencyService {
    private final static String URL = "https://select.by/kurs/gomel";
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void schedule() throws IOException {
        Document page = Jsoup.connect(URL).get();
        Element element = page.select("section[class=table-responsive]").first();
        Elements elements = Objects.requireNonNull(element).select("tr[class=tablesorter-hasChildRow]");
        saveCurrency(elements);
    }

    private void saveCurrency(Elements elements) {
        List<Currency> currencyList = new ArrayList<>();
        for (Element elem : elements) {
            Currency currency = new Currency();
            currency.setNameCompany(elem.select("a[href=#]").text());
            currency.setUSD(Double.valueOf(elem.select("td").get(1).text().replace(',', '.')));
            currency.setEUR(Double.valueOf(elem.select("td").get(3).text().replace(',', '.')));
            currency.setRUB(Double.valueOf(elem.select("td").get(5).text().replace(',', '.')));
            currencyList.add(currency);
            if (!findByAllCurrency().isEmpty()) {
                currencyRepository.updateCurrencyByNameCompany(currency.getUSD(), currency.getEUR(),
                        currency.getRUB(), currency.getNameCompany());
            }
        }
        if (findByAllCurrency().isEmpty()) {
            currencyRepository.saveAll(currencyList);
        }
    }

    public List<Currency> findByAllCurrency() {
        return currencyRepository.findAll();
    }
}
