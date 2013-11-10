/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.booklibrary.domain.Book;
import wad.booklibrary.repository.BookRepository;

@Service
public class JPABookService implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    private void init() {

        Book book = new Book();
        List<String> authors = new ArrayList<String>();
        List<String> publishers = new ArrayList<String>();
        book.setTitle("Mattisten Historia");
        authors.add("Mikko Mattinen");
        book.setAuthors(authors);
        publishers.add("Minni Mattinen");
        book.setPublishers(publishers);
        book.setIsbn("78372678");
        book.setPublishingYear(2012);
        bookRepository.save(book);

        book = new Book();
        book.setTitle("Mattisten Historia, osa2");
        authors.add("Kalle Ankkanen");
        book.setAuthors(authors);
        publishers.add("Janne Vakio");
        book.setPublishers(publishers);
        book.setIsbn("78372678787");
        book.setPublishingYear(2000);
        bookRepository.save(book);

        book = new Book();
        book.setTitle("Plop Plop Plop");
        authors.add("Janne Mäkinen");
        book.setAuthors(authors);
        publishers.add("Jenni Virtanen");
        book.setPublishers(publishers);
        book.setIsbn("11172678787");
        book.setPublishingYear(1990);
        bookRepository.save(book);

        book = new Book();
        authors = new ArrayList<String>();
        book.setTitle("Joo Poistoon");
        authors.add("Authori");
        book.setAuthors(authors);
        book.setIsbn("159863332752");
        bookRepository.save(book);

        book = new Book();
        authors = new ArrayList<String>();
        publishers = new ArrayList<String>();
        book.setTitle("Hilloviinaa");
        authors.add("Kalle Pirinen");
        book.setAuthors(authors);
        publishers.add("Janne Virtanen");
        book.setPublishers(publishers);
        book.setIsbn("1117267878799");
        book.setPublishingYear(1999);
        bookRepository.save(book);

        book = new Book();
        authors = new ArrayList<String>();
        publishers = new ArrayList<String>();
        book.setTitle("Vajaa Kirja");
        authors.add("Väinö Ojanen");
        book.setAuthors(authors);
        book.setIsbn("None");
        bookRepository.save(book);

        book = new Book();
        authors = new ArrayList<String>();
        publishers = new ArrayList<String>();
        book.setTitle("Voi pojat");
        authors.add("Mika Mikkonen");
        book.setAuthors(authors);
        publishers.add("Erkki Viinanen");
        book.setPublishers(publishers);
        book.setIsbn("11172678787996");
        bookRepository.save(book);

        book = new Book();
        authors = new ArrayList<String>();
        publishers = new ArrayList<String>();
        book.setTitle("Java Programming for the Absolute Beginner");
        authors.add("John P. Flynt");
        book.setAuthors(authors);
        publishers.add("Course Technology PTR");
        book.setPublishers(publishers);
        book.setIsbn("1598632752");
        book.setPublishingYear(2006);
        bookRepository.save(book);

        book = new Book();
        authors = new ArrayList<String>();
        publishers = new ArrayList<String>();
        book.setTitle("Monty Python's Flying Circus");
        authors.add("Monty Python");
        book.setAuthors(authors);
        publishers.add("Methuen Publishing Ltd.");
        book.setPublishers(publishers);
        book.setIsbn("0413772802");
        book.setPublishingYear(1998);
        bookRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAllByTitle(String query, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "title");
        return bookRepository.findByTitleContainingIgnoreCase(query, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAllByAuthor(String query, int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "title");
        return bookRepository.findByAuthorsContainingIgnoreCase(query, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> list(int pageNumber, int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "title");
        return bookRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = false)
    public Book add(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(Long id) {
        bookRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Long id, Book book) {
        bookRepository.save(book);
    }
}
