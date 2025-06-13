import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        int[] values = new int[]{1,2,3,4,5,6,2,4,3,6,5,7,8,5,4,9,1};
        System.out.println("Get two min digits and collect them into number");
        System.out.println(minValue2(values));
        System.out.println("* * *");
        System.out.println("Create modified list of odd or even elements depends of initial list sum");
        List<Integer> integers = Arrays.stream(values).boxed().collect(Collectors.toList());
        List<Integer> newIntegers = oddOrEven(integers);
        newIntegers.forEach(x -> System.out.printf("%s, ", x));
    }


    public static int minValue(int[] values) {
        int first_min = Arrays.stream(values).min().getAsInt();
        int second_min = Arrays.stream(values).filter(x -> x > first_min).min().getAsInt();
        return first_min * 10 + second_min;
    }

    public static int minValue2(int[] values) {
        return Arrays.stream(values).limit(2).distinct().sorted().reduce(0, (acc, digit) -> acc * 10 + digit);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(i -> i).sum();
        System.out.printf("sum = %s\n", sum);
        Predicate<Integer> predicate;
        if (sum % 2 == 0) {
            predicate = x -> x %2 == 0;
        } else {
            predicate = x -> x %2 != 0;
        }
        return integers.stream().filter(predicate).collect(Collectors.toList());
    }
}
