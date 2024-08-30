package com.emazon.stock_v1.utils;

import org.springframework.data.domain.Sort;

public class Helpers {

    private Helpers() {
    }

    public static Sort buildSortForOneProperty(String sortProperty, String sortDirection) {
        Sort sort = Sort.by(sortProperty).ascending();
        if(sortDirection.equalsIgnoreCase(Sort.Direction.DESC.toString())) {
            sort = Sort.by(sortProperty).descending();
        }

        return sort;
    }
}
