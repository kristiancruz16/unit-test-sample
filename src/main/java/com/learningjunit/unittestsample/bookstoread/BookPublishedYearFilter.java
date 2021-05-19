package com.learningjunit.unittestsample.bookstoread;

import java.time.LocalDate;

public class BookPublishedYearFilter implements BookFilter{

    private LocalDate startDate;

    static BookPublishedYearFilter After(int year) {
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.startDate = LocalDate.of(year, 12, 31);
        return filter;
    }

    static BookPublishedYearFilter Before(int year) {
        BookPublishedYearFilter filterBefore = new BookPublishedYearFilter();
        filterBefore.startDate = LocalDate.of(year, 01, 01);
        return filterBefore;
    }

    @Override
    public boolean apply(Book b) {
      return startDate.getMonthValue()==12?b.getPublishedOn().isAfter(startDate):b.getPublishedOn().isBefore(startDate);
    }

}
