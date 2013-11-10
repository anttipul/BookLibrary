/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.controller;

import javax.validation.Valid;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.booklibrary.domain.Book;
import wad.booklibrary.service.BookService;
import wad.booklibrary.service.OpenLibraryJsonService;

@Controller
public class BookLibraryController {
    private final int PAGE_SIZE = 5;

    @Autowired
    private BookService bookService;
    @Autowired
    private OpenLibraryJsonService openLibraryJsonService;

    @RequestMapping(value = "books", method = RequestMethod.GET)
    public String list(Model model,@RequestParam(required=false, defaultValue="1") int pageNumber) {
        Page<Book> res;
        res = bookService.list(pageNumber, PAGE_SIZE);
        model.addAttribute("books", res.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", res.getTotalPages());
        model.addAttribute("totalItems", res.getTotalElements());
        model.addAttribute("query", "?");
        return "books";
    }

    @RequestMapping(value = "books/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable(value = "id") Long bookId) {
        model.addAttribute("book", bookService.findById(bookId));
        return "book";
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @RequestMapping(value = "books/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable(value = "id") Long bookId) {
        model.addAttribute("book", bookService.findById(bookId));
        return "edit";
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @RequestMapping(value = "books/delete/{id}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable(value = "id") Long bookId) {
        bookService.remove(bookId);
        return "menu";
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @RequestMapping(value = "books", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute("book") Book book,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "add";
        }

        bookService.add(book);
        model.addAttribute(book);
        return "book";
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @RequestMapping(value = "books/update/{id}", method = RequestMethod.POST)
    public String update(Model model, @PathVariable(value = "id") Long bookId, 
            @Valid @ModelAttribute("book") Book book,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        bookService.update(bookId, book);
        return "redirect:/app/books/{id}";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "add";
    }


    @RequestMapping(value = "openlibrary/search/", method = RequestMethod.GET)
    public String searchForBookByIsbn(@RequestParam("isbnortitle") String isbnortitle,
            @RequestParam("query") String query, Model model) throws IOException {
        Book book = new Book();
        if (isbnortitle.equals("isbn")) {
            book = openLibraryJsonService.searchForBookByIsbn(query);
        } else if (isbnortitle.equals("title")) {
            book = openLibraryJsonService.searchForBookByTitle(query);
        }
        model.addAttribute(book);
        return "add";
    }

    @RequestMapping(value = "books/search/", method = RequestMethod.GET)
    public String searchForBookFromOwnDb(@RequestParam(value="pageNumber", required=false, defaultValue="1") int pageNumber,
    @RequestParam("where") String where, @RequestParam("query") String query, Model model) throws IOException {
        Page<Book> res = null;
        if (where.equals("author")) {
            res = bookService.findAllByAuthor(query,pageNumber,PAGE_SIZE);
        } else if (where.equals("title")) {
            res = bookService.findAllByTitle(query,pageNumber,PAGE_SIZE);
        }
        model.addAttribute("books", res.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", res.getTotalPages());
        model.addAttribute("totalItems", res.getTotalElements());
        model.addAttribute("query", "search/?where=" + where + "&query=" + query + "&");
        
        return "books";
    }

}
