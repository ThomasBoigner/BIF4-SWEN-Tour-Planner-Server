package at.fhtw.tourplanner.domain.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Page<T> {
    List<T> content;
    boolean last;
    int totalPages;
    long totalElements;
    boolean first;
    int size;
    int number;
    int numberOfElements;
    boolean empty;
}
