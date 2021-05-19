package com.learningjunit.unittestsample.bookstoread;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class BooksParameterResolver implements ParameterResolver {

    private final Map<String, Book> books;

    public BooksParameterResolver() {
        Map<String, Book> books = new HashMap<>();
        books.put("Effective Java", new Book("Effective Java", "Joshua Bloch",
                LocalDate.of(2008, Month.MAY, 8)));
        books.put("Code Complete", new Book("Code Complete",
                "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9)));
        books.put("The Mythical Man-Month", new Book("The Mythical ManMonth",
                "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1)));
        books.put("Clean Code", new Book("Clean Code", "Robert C. Martin",
                LocalDate.of(2008, Month.AUGUST, 1)));
        books.put("Refactoring: Improving the Design of Existing Code", new Book("Refactoring: Improving the Design of Existing Code",
                "Martin Fowler", LocalDate.of(2002, Month.MARCH, 9)));
        this.books = books;
    }
    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        boolean checker = Objects.equals(parameter.getParameterizedType().getTypeName(), "java.util.Map<java.lang.String, " +
                "com.learningjunit.unittestsample.bookstoread.Book>");
        return checker;
    }
    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
       return books;
    }


}