/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.booklibrary.domain.Book;


public interface BookRepository extends JpaRepository<Book, Long> {
    public Page<Book> findByTitleContainingIgnoreCase(String title,Pageable pageable);
//    TODO: Special query that doesn't return duplicates
    public Page<Book> findByAuthorsContainingIgnoreCase(String author, Pageable pageable);
}
