/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wad.booklibrary.domain.Book;

@Service
public class OpenLibraryJsonService {

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
    }

    public Book searchForBookByIsbn(String isbn) throws IOException {
        Book book = new Book();
        String url = "http://openlibrary.org/api/books?bibkeys=ISBN:" + isbn
                + "&jscmd=data&format=json";
        String jsonString = restTemplate.getForObject(url, String.class);
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        if (jsonString.equals("{}")) {
            return book;
        }
        JsonObject jsonBook = json.get("ISBN:" + isbn).getAsJsonObject();
        book.setTitle(jsonBook.get("title").getAsString());
        book.setIsbn(isbn);
        book.setAuthors(getAuthors(jsonBook.get("authors").getAsJsonArray()));
        book.setPublishers(getPublishers(jsonBook.get("publishers").getAsJsonArray()));
        book.setPublishingYear(getPublishingYear(jsonBook.get("publish_date").getAsString()));
        return book;
    }

    public Book searchForBookByTitle(String title) {
        Book book = new Book();
        String url = "http://openlibrary.org/search.json?title=" + title;
        String jsonString = restTemplate.getForObject(url, String.class);
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonElement num_found = json.get("num_found");
        if (num_found.getAsString().equals("0")) {
            return book;
        }
        JsonElement docs = json.get("docs");
        JsonObject jsonBook = docs.getAsJsonArray().get(0).getAsJsonObject();
        book.setTitle(jsonBook.get("title").getAsString());
        String isbnFinal = "None";
        try {
            String ob = jsonBook.get("isbn").getAsJsonArray().get(0).getAsString();
            String[] array = ob.split("\t");
            for (String isbn : array[0].split(" ")) {
                isbnFinal = isbn;
                break;
            }
        } catch (Exception e) {
        }
        book.setIsbn(isbnFinal);

        List<String> authors = new ArrayList<String>();
        try {
            authors.add(jsonBook.get("author_name").getAsString());
        } catch (Exception e) {
            try {
                authors = getAuthorsNoKeys(jsonBook.get("author_name").getAsJsonArray());
            } catch (Exception ee) {
            }
        }
        book.setAuthors(authors);

        List<String> publishers = new ArrayList<String>();
        try {
            publishers.add(jsonBook.get("publisher").getAsString());
        } catch (Exception e) {
            try {
                publishers = getPublishersNoKeys(jsonBook.get("publisher").getAsJsonArray());
            } catch (Exception ee) {
            }
        }
        book.setPublishers(publishers);
        book.setPublishingYear(Integer.parseInt(jsonBook.get("first_publish_year").getAsString()));
        return book;
    }

    private List<String> getAuthors(JsonArray authorsArray) {
        List<String> authors = new ArrayList<String>();
        for (JsonElement author : authorsArray) {
            String name = author.getAsJsonObject().get("name").getAsString();
            authors.add(name);
        }
        return authors;
    }

    private List<String> getAuthorsNoKeys(JsonArray authorsArray) {
        List<String> authors = new ArrayList<String>();
        for (JsonElement author : authorsArray) {
            authors.add(author.getAsString());
        }
        return authors;
    }

    private List<String> getPublishers(JsonArray publishersArray) {
        List<String> publishers = new ArrayList<String>();
        for (JsonElement publisher : publishersArray) {
            String name = publisher.getAsJsonObject().get("name").getAsString();
            publishers.add(name);
        }
        return publishers;
    }

    private List<String> getPublishersNoKeys(JsonArray publishersArray) {
        List<String> publishers = new ArrayList<String>();
        for (JsonElement publisher : publishersArray) {
            publishers.add(publisher.getAsString());
        }
        return publishers;
    }

    private Integer getPublishingYear(String date) {
        Integer year = -1;
        String pattern = "(.*)(\\d{4})(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(date);
        if (m.find()) {
            try {
                year = Integer.parseInt(m.group(2));
            } catch (Exception e) {
            }
        }
        return year;
    }

}
