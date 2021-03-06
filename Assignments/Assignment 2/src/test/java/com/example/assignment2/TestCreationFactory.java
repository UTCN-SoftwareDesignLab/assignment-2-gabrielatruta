package com.example.assignment2;

import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.user.dto.UserDTO;
import com.example.assignment2.user.dto.UserListDTO;
import com.example.assignment2.user.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.assignment2.user.model.ERole.ADMINISTRATOR;
import static com.example.assignment2.user.model.ERole.EMPLOYEE;
import static java.util.stream.Collectors.toList;

public class TestCreationFactory {
    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDTO.class)) {
            supplier = TestCreationFactory::newUserListDTO;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else if (cls.equals(BookDTO.class)) {
            supplier = TestCreationFactory::newBookDTO;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static UserListDTO newUserListDTO() {
        return UserListDTO.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .build();
    }

    public static UserDTO newUserDTO() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .id(-1)
                .name(ADMINISTRATOR)
                .build());
        roles.add(Role.builder()
                .id(-1)
                .name(EMPLOYEE)
                .build());
        return UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .password(randomString())
                .username(randomString())
                .build();
    }

    private static Book newBook() {
        return Book.builder()
                .id(randomLong())
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();
    }

    private static BookDTO newBookDTO() {
        return BookDTO.builder()
                .id(randomLong())
                .author(randomString())
                .title(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomLong())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
