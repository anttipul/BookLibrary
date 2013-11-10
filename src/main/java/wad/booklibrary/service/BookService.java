/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.service;

import org.springframework.data.domain.Page;
import wad.booklibrary.domain.Book;

public interface BookService {
    public Book add(Book book);   
    public void update(Long id, Book book);
    public Book findById(Long id);
    public void remove(Long id);
    public Page<Book> list(int pageNumber, int pageSize);
    public Page<Book> findAllByTitle(String query, int pageNumber, int pageSize);
    public Page<Book> findAllByAuthor(String query, int pageNumber, int pageSize);

}

