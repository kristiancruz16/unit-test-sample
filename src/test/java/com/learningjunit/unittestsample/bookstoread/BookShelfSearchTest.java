package com.learningjunit.unittestsample.bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.verification.After;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("search")
public class BookShelfSearchTest {

    private BookShelf shelf = new BookShelf();
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;
    private Book refactoring;

    @BeforeEach
    void setup() {
        effectiveJava = new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete",
                "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical ManMonth",
                "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("Clean Code", "Robert C. Martin",
                LocalDate.of(2008, Month.AUGUST, 1));
        refactoring = new Book("Refactoring: Improving the Design of Existing Code",
                "Martin Fowler", LocalDate.of(2002, Month.MARCH, 9));
        shelf.add(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
    }

    @Test
    @DisplayName(" should find books with title containing text")
    void shouldFindBooksWithTitleContainingText() {
        List<Book> books = shelf.findBooksByTitle("code");
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName(" should find books with title containing text and published after specified date.")
    void shouldFilterSearchedBooksBasedOnPublishedDate() {
        List<Book> books = shelf.findBooksByTitle("code", b -> b.getPublishedOn()
                .isBefore(LocalDate.of(2014, 12, 31)));
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("is after specified year")
    void validateBookPublishedDatePostAskedYear(){
        BookFilter filter = BookPublishedYearFilter.After(2007);
        assertTrue(filter.apply(cleanCode));
        assertFalse(filter.apply(codeComplete));
    }
    @Test
    @DisplayName("is after specified year")
    void validateBookPublishedDatePreAskedYear(){
        BookFilter filter = BookPublishedYearFilter.Before(2007);
        assertTrue(filter.apply(refactoring));
        assertFalse(filter.apply(effectiveJava));
    }

    @Test
    @DisplayName("Composite criteria is based on multiple filters")
    void shouldFilterOnMultiplesCriteria(){
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter( b -> false);
        assertFalse(compositeFilter.apply(cleanCode));
    }

    @Test
    @DisplayName("Composite criteria does not invoke after first failure")
    void shouldNotInvokeAfterFirstFailure() {
        CompositeFilter compositeFilter = new CompositeFilter();
        BookFilter invokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(invokedMockedFilter.apply(cleanCode)).thenReturn(false);
        compositeFilter.addFilter(invokedMockedFilter);
        BookFilter nonInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(nonInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
        compositeFilter.addFilter(nonInvokedMockedFilter);
        assertFalse(compositeFilter.apply(cleanCode));
        Mockito.verify(invokedMockedFilter).apply(cleanCode);
        Mockito.verifyZeroInteractions(nonInvokedMockedFilter);
    }

    @Test
    @DisplayName("Composite criteria invokes all filters")
    void shouldInvokeAllFilters() {
        CompositeFilter compositeFilter = new CompositeFilter();
        BookFilter firstInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(firstInvokedMockedFilter.apply(cleanCode)).
                thenReturn(true);
        compositeFilter.addFilter(firstInvokedMockedFilter);
        BookFilter secondInvokedMockedFilter = Mockito.mock(BookFilter.class);
        Mockito.when(secondInvokedMockedFilter.apply(cleanCode)).
                thenReturn(true);
        compositeFilter.addFilter(secondInvokedMockedFilter);
        assertTrue(compositeFilter.apply(cleanCode));
        Mockito.verify(firstInvokedMockedFilter).apply(cleanCode);
        Mockito.verify(secondInvokedMockedFilter).apply(cleanCode);
    }

}
