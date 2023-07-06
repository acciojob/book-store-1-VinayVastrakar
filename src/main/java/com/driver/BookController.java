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

    HashMap<Integer,Book> db= new HashMap<>();

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

        if(id==1){
            db.put(id++,book1);
        }else{
            db.put(id++,book1);
        }
        bookList.add(book1);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id){
        Book book = db.get(id);
        if(db.containsKey(db.get(id))){
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") int id){
        db.remove(id);
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                bookList.remove(i);
            }
        }
        this.id=1;
        return new ResponseEntity<>("Book Deleted",HttpStatus.ACCEPTED);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books= getBookList();
        return new ResponseEntity<>(books,HttpStatus.FOUND);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
            bookList.clear();
            db.clear();
       return new ResponseEntity<>("All Books Deleted", HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor
    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author){
        List<Book> list =new ArrayList<>();
        for(int key: db.keySet()){
            Book book= db.get(key);
            if(book.getAuthor().equals(author)){
                list.add(book);
            }
        }
        return new ResponseEntity<>(list,HttpStatus.FOUND);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam String genre){
        List<Book> lists= new ArrayList<>();
        for(int key: db.keySet()){
            Book book= db.get(key);
            if(book.getGenre().equals(genre)){
                lists.add(book);
            }
        }
        return new ResponseEntity<>(lists, HttpStatus.FOUND);
    }

}
