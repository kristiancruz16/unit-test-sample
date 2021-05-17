package com.learningjunit.unittestsample.bookstoread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("A bookshelf")
public class BookShelfTest {

    private BookShelfTest(TestInfo testInfo) {
        System.out.println("Working on test " + testInfo.getDisplayName());
    }

    @Test
    @DisplayName("is empty when no book is added to it")
    public void shelfEmptyWhenNoBookAdded(TestInfo testInfo) throws Exception {
        System.out.println("Working on test " + testInfo.getDisplayName());
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    public void shelfToStringShouldPrintBookCountAndTitles() throws Exception {
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        books.add("The Phoenix Project");
        books.add("Java 8 in Action");
        String shelfStr = shelf.toString();
        assertAll( () -> assertTrue(shelfStr.contains("The Phoenix Project"),
                "1st book title missing"),
                () -> assertTrue(shelfStr.contains("Java 8 in Action") ,
                "2nd book title missing "),
                () -> assertTrue(shelfStr.contains("2 books found"),
                        "Book count missing"));
    }
}
