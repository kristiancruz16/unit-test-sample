package com.learningjunit.unittestsample.bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("progress")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfProgressTest {
    /*private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init() {
        shelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8));
        codeComplete =   new Book("Code Complete",
                "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical ManMonth",
                "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin",
                LocalDate.of(2008, Month.AUGUST, 1));
        refactoring = new Book("Refactoring: Improving the Design of Existing Code",
                "Martin Fowler", LocalDate.of(2002, Month.MARCH, 9));
    }*/

/*    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void init(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring=books.get("Refactoring: Improving the Design of Existing Code");
    }*/

    private BookShelf shelf;
    private Book effectiveJava, codeComplete, mythicalManMonth, cleanCode, refactoring;
    private List<Book> listOfBooks;

    @BeforeEach
    void init(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
        this.refactoring = books.get("Refactoring: Improving the Design of Existing Code");
        listOfBooks = Arrays.asList(effectiveJava,codeComplete,mythicalManMonth,cleanCode,refactoring);
        listOfBooks.stream().forEach(shelf::add);

    }

    @Test
    @DisplayName("is 0% completed and 100% to-read when no book is read yet")
    void progress100PercentUnread() {
        Progress progress = shelf.progress();
        assertThat(progress.completed()).isEqualTo(0);
        assertThat(progress.toRead()).isEqualTo(100);
    }

    @Test
    @DisplayName("is 40% completed and 60% to-read when 2 books are finished and 3 books not read yet")
    void progressWithCompletedAndToReadPercentages() {
            listOfBooks.get(0).startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
            listOfBooks.get(0).finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
            listOfBooks.get(1).startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
            listOfBooks.get(1).finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
            Progress progress = shelf.progress();
            assertThat(progress.completed()).isEqualTo(40);
            assertThat(progress.toRead()).isEqualTo(60);

    }

    @Test
    @DisplayName("is 40% completed, 20% in-progress, and 40% to-read when 2 books read, 1 book in progress, and 2 books unread")
    void reportProgressOfCurrentlyReadingBooks(){
        listOfBooks.get(0).startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
        listOfBooks.get(0).finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));

        listOfBooks.get(1).startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
        listOfBooks.get(1).finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));

        listOfBooks.get(2).startedReadingOn(LocalDate.of(2016, Month.SEPTEMBER, 1));

        Progress progress = shelf.progress();

        assertThat(progress.completed()).isEqualTo(40);
        assertThat(progress.inProgress()).isEqualTo(20);
        assertThat(progress.toRead()).isEqualTo(40);
    }
}
