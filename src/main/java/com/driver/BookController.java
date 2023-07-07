package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    private HashMap<Integer,Book> db= new HashMap<>();

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
            Book book1=book;
            book1.setId(id);

            db.put(id,book1);
            id+=1;

            bookList.add(book1);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id){
        Book book=null;
        if(db.containsKey(db.get(id))){
            book = db.get(id);
        }

        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable("id") int id){
        db.remove(id);
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                bookList.remove(i);
            }
        }
        return new ResponseEntity("SUCCESS",HttpStatus.ACCEPTED);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        List<Book> books= getBookList();
        return new ResponseEntity(books,HttpStatus.FOUND);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks(){
            bookList.clear();
            db.clear();
        setId(1);
       return new ResponseEntity("SUCCESS", HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam String author){
        List<Book> list =new ArrayList<>();
        for(int key: db.keySet()){
            Book book= db.get(key);
            if(author.equals(book.getAuthor())){
                list.add(book);
            }
        }
        return new ResponseEntity(list,HttpStatus.FOUND);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam String genre){
        List<Book> lists= new ArrayList<>();
        for(int key: db.keySet()){
            Book book= db.get(key);
            if(book.getGenre().equals(genre)){
                lists.add(book);
            }
        }
        return new ResponseEntity(lists, HttpStatus.FOUND);
    }

}
