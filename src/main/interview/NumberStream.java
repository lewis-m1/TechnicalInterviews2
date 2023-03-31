package interview;

import java.util.List;
import java.util.stream.Collectors;

public class NumberStream {

    /*
    filter out numbers which are even and greater than 3

if the number is <= 5
	multiply by 5
		else x 10
		return all numbers
     */
    List<Integer> numbersList = List.of(1,2,3,4,5,6,7,8,9,10);

    List<Integer> collect = numbersList.stream()
            .filter(number -> !(number % 2 == 0) || number > 3)
            .map(number -> {
                if (number <= 5) {
                    number = number * 5;
                } else {
                    number = number * 10;
                }
                return number;
            })
            .collect(Collectors.toList());
}
