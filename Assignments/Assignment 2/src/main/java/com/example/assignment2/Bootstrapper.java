package com.example.assignment2;

import com.example.assignment2.book.model.Book;
import com.example.assignment2.book.repository.BookRepository;
import com.example.assignment2.security.AuthService;
import com.example.assignment2.security.dto.SignupRequest;
import com.example.assignment2.user.RoleRepository;
import com.example.assignment2.user.UserRepository;
import com.example.assignment2.user.model.ERole;
import com.example.assignment2.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BookRepository bookRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("gabi@email.com")
                    .username("gabi")
                    .password("Gabriela3!")
                    .roles(Set.of("ADMINISTRATOR"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("gabi1@email.com")
                    .username("gabi1")
                    .password("Gabriela3!")
                    .roles(Set.of("EMPLOYEE"))
                    .build());

            bookRepository.save(Book.builder()
                    .id(-1L)
                    .title("To kill a mockingbird")
                    .author("Harper Lee")
                    .genre("Southern Gothic")
                    .price(35L)
                    .quantity(0L)
                    .build());

            bookRepository.save(Book.builder()
                    .id(-1L)
                    .title("The meaning of Happiness")
                    .author("Alan Watts")
                    .genre("Philosophy")
                    .price(43L)
                    .quantity(0L)
                    .build());

            bookRepository.save(Book.builder()
                    .id(-1L)
                    .title("Uncle Tom's Cabin")
                    .author("Harriet Beecher Stowe")
                    .genre("Historical Fiction")
                    .price(33L)
                    .quantity(10L)
                    .build());

        }
    }
}