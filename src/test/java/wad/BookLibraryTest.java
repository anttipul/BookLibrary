package wad;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.booklibrary.domain.Book;
import wad.booklibrary.repository.BookRepository;
import wad.booklibrary.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
public class BookLibraryTest {

    private WebDriver driver;
    private String baseUrl;
    private String port;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        baseUrl = "http://localhost:" + port + "/app/";
        initDatabase();
    }

    @Test
    public void loadsApp() {
        driver.get(baseUrl);
        Assert.assertTrue(driver.getPageSource().contains("BookLibrary"));
    }

    @Test
    public void listBooks() {
        driver.get(baseUrl + "books");
        Assert.assertTrue(driver.getPageSource().contains("Java Programming for the Absolute Beginner"));
    }

    @Test
    public void buttonsCorrect() {
        driver.get(baseUrl);
        Assert.assertTrue(driver.getPageSource().contains("Login"));
        Assert.assertTrue(driver.getPageSource().contains("Register"));
        Assert.assertFalse(driver.getPageSource().contains("Logout"));
        Assert.assertFalse(driver.getPageSource().contains("Add"));
        Assert.assertTrue(driver.getPageSource().contains("Menu"));
        Assert.assertTrue(driver.getPageSource().contains("List"));
    }

    @Test
    public void loginAdminButtonsCorrect() {
        driver.get(baseUrl + "login");
        WebElement e = driver.findElement(By.name("j_username"));
        e.sendKeys("admin");
        e = driver.findElement(By.name("j_password"));
        e.sendKeys("admin");
        e.submit();
        Assert.assertFalse(driver.getPageSource().contains("Login"));
        Assert.assertFalse(driver.getPageSource().contains("Register"));
        Assert.assertTrue(driver.getPageSource().contains("Logout"));
        Assert.assertTrue(driver.getPageSource().contains("Add"));
    }

    @Test
    public void logout() {
        loginAdmin();
        driver.get(baseUrl + "logout");
        Assert.assertTrue(driver.getPageSource().contains("Login"));
        Assert.assertTrue(driver.getPageSource().contains("Register"));
        Assert.assertFalse(driver.getPageSource().contains("Logout"));
        Assert.assertFalse(driver.getPageSource().contains("Add"));
    }

    @Test
    public void loginFails() {
        driver.get(baseUrl + "login");
        WebElement e = driver.findElement(By.name("j_username"));
        e.sendKeys("admin");
        e = driver.findElement(By.name("j_password"));
        e.sendKeys("a");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("Bad Credential"));
    }
    
    @Test
    public void register() {
        driver.get(baseUrl + "logout");
        driver.get(baseUrl + "register");
        WebElement e = driver.findElement(By.name("name"));
        e.sendKeys("mikko");
        e = driver.findElement(By.name("password"));
        e.sendKeys("matti");
        e.submit();
        e = driver.findElement(By.name("j_username"));
        e.sendKeys("mikko");
        e = driver.findElement(By.name("j_password"));
        e.sendKeys("matti");
        e.submit();
        Assert.assertFalse(driver.getPageSource().contains("Login"));
        Assert.assertFalse(driver.getPageSource().contains("Register"));
        Assert.assertTrue(driver.getPageSource().contains("Logout"));
        Assert.assertTrue(driver.getPageSource().contains("Add"));
    }
    
    @Test
    public void viewBook() {
        driver.get(baseUrl + "books");
        List<WebElement> e = driver.findElements(By.linkText("View"));
        e.get(0).click();
        //System.out.print(driver.getPageSource());
        Assert.assertTrue(driver.getPageSource().contains("1598632752"));
        Assert.assertTrue(driver.getPageSource().contains("Course Technology PTR"));
        Assert.assertFalse(driver.getPageSource().contains("Delete"));
        Assert.assertFalse(driver.getPageSource().contains("Edit"));
    }
    
    @Test
    public void viewBookLoggedIn() {
        loginAdmin();
        driver.get(baseUrl + "books");
        List<WebElement> e = driver.findElements(By.linkText("View"));
        e.get(1).click();
        Assert.assertTrue(driver.getPageSource().contains("1598632752"));
        Assert.assertTrue(driver.getPageSource().contains("Course Technology PTR"));
        Assert.assertTrue(driver.getPageSource().contains("Delete"));
        Assert.assertTrue(driver.getPageSource().contains("Edit"));
    }
    
    @Test
    public void deleteBook() {
        loginAdmin();
        driver.get(baseUrl);
        WebElement e = driver.findElement(By.id("odtitle"));
        e.click();
        e = driver.findElement(By.id("odquery"));
        e.sendKeys("Poistoon");
        e.submit();
        e = driver.findElement(By.linkText("View"));
        e.click();
        Assert.assertTrue(driver.getPageSource().contains("Poistoon"));
        e = driver.findElement(By.linkText("Delete"));
        e.click();
        driver.get(baseUrl);
        e = driver.findElement(By.id("odtitle"));
        e.click();
        e = driver.findElement(By.id("odquery"));
        e.sendKeys("Poistoon");
        e.submit();
        Assert.assertFalse(driver.getPageSource().contains("Poistoon"));
    }
    
    @Test
    public void updateBook() {
        loginAdmin();
        driver.get(baseUrl + "books");
        List<WebElement> e = driver.findElements(By.linkText("View"));
        e.get(1).click();
//        System.out.print(driver.getPageSource());
        WebElement ee = driver.findElement(By.linkText("Edit"));
        ee.click();
        Assert.assertTrue(driver.getPageSource().contains("Java Programming for the Absolute Beginner"));
        ee = driver.findElement(By.name("title"));
        ee.clear();
        ee.sendKeys("AAAH Java");
        ee.submit();
        driver.get(baseUrl + "books");
        Assert.assertFalse(driver.getPageSource().contains("Java Programming for the Absolute Beginner"));
        Assert.assertTrue(driver.getPageSource().contains("AAAH Java"));
    }
    
    @Test
    public void addBook() {
        loginAdmin();
        driver.get(baseUrl + "add");
        WebElement e = driver.findElement(By.name("title"));
        e.sendKeys("YYY");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("may not be empty"));
        e = driver.findElement(By.name("isbn"));
        e.sendKeys("643864873876");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("may not be empty"));
        e = driver.findElement(By.name("authors"));
        e.sendKeys("Antti Author, Matti Mainio, Veikko Virtanen");
        e = driver.findElement(By.name("publishers"));
        e.sendKeys("Mikko Mato, Ville Vartija");
        e = driver.findElement(By.name("publishingYear"));
        e.sendKeys("-1");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("must be greater than or equal to 0"));
        e = driver.findElement(By.name("publishingYear"));
        e.clear();
        e.sendKeys("1999");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("Veikko Virtanen"));
        Assert.assertTrue(driver.getPageSource().contains("Ville Vartija"));
        Assert.assertTrue(driver.getPageSource().contains("1999"));
        Assert.assertTrue(driver.getPageSource().contains("YYY"));
        driver.get(baseUrl + "books/?pageNumber=2");
        Assert.assertTrue(driver.getPageSource().contains("YYY"));
    }
    
    @Test
    public void findByIsbn() {
        driver.get(baseUrl);
        WebElement e = driver.findElement(By.id("olisbn"));
        e.click();
        e = driver.findElement(By.id("olquery"));
        e.sendKeys("0078681707");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("McGraw-Hill"));
        Assert.assertTrue(driver.getPageSource().contains("Algebra"));
        Assert.assertTrue(driver.getPageSource().contains("0078681707"));
    }
    
    @Test
    public void findByTitle() {
        driver.get(baseUrl);
        WebElement e = driver.findElement(By.id("oltitle"));
        e.click();
        e = driver.findElement(By.id("olquery"));
        e.sendKeys("algebra");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("Algebra"));
        Assert.assertTrue(driver.getPageSource().contains("McGraw-Hill"));
        Assert.assertTrue(driver.getPageSource().contains("0078681707"));
    }

    @Test
    public void findFromOwnDbByTitle() {
        driver.get(baseUrl);
        WebElement e = driver.findElement(By.id("odtitle"));
        e.click();
        e = driver.findElement(By.id("odquery"));
        e.sendKeys("monty");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("Monty Python's Flying Circus"));
    }

    @Test
    public void findFromOwnDbByAuthor() {
        driver.get(baseUrl);
        WebElement e = driver.findElement(By.id("odauthor"));
        e.click();
        e = driver.findElement(By.id("odquery"));
        e.sendKeys("Python");
        e.submit();
        Assert.assertTrue(driver.getPageSource().contains("Monty Python's Flying Circus"));
    }
    
    private void loginAdmin() {
        driver.get(baseUrl + "login");
        WebElement e = driver.findElement(By.name("j_username"));
        e.sendKeys("admin");
        e = driver.findElement(By.name("j_password"));
        e.sendKeys("admin");
        e.submit();
    }

    private void initDatabase() {
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
        publishers = new ArrayList<String>();
        book.setTitle("Hienoa elämää");
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
        book.setTitle("Hilloviinaa");
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
        book.setTitle("Joo Poistoon");
        authors.add("Authori");
        book.setAuthors(authors);
        book.setIsbn("159863332752");
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
}
