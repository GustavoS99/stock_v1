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

    public static Sort buildSortForTwoProperties(
            String objectProperty, String subObjectProperty, String sortDirection
    ) {
        Sort sort;

        sort = Sort.by(subObjectProperty).ascending().and(
                    Sort.by(objectProperty).ascending());
        if (sortDirection.equalsIgnoreCase(Sort.Direction.DESC.toString())) {
            sort = Sort.by(subObjectProperty).descending().and(
                    Sort.by(objectProperty).descending());
        }

        return sort;
    }

    public static boolean isOrdered(String sortProperty, String sortDirection) {
        return sortProperty != null && !sortProperty.isEmpty() && sortDirection != null && !sortDirection.isEmpty();
    }
}
