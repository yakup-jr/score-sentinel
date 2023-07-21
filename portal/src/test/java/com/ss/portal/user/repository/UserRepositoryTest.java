package com.ss.portal.user.repository;

import com.ss.portal.user.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/data/add-users.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        testEntityManager.clear();
    }

    @ParameterizedTest
    @MethodSource("provideEmail")
    public void should_return_user_when_email_exist(String email,
                                                    boolean exceptedFound) {
        // when
        Optional<UserEntity> user = userRepository.findByEmail(email);

        // then
        assertThat(user.isPresent()).isEqualTo(exceptedFound);
        if (exceptedFound) assertThat(user.get().getEmail()).isEqualTo(email);
    }

    public static Stream<Arguments> provideEmail() {
        return Stream.of(Arguments.of("user1@example.com", true),
            Arguments.of("falsyMail@example.com", false), Arguments.of(null, false));
    }

    @ParameterizedTest
    @MethodSource("provideUsername")
    public void should_return_user_when_username_exist(String username,
                                                       boolean exceptedFound) {
        // when
        Optional<UserEntity> user = userRepository.findByUsername(username);

        assertThat(user.isPresent()).isEqualTo(exceptedFound);
        if (exceptedFound) assertThat(user.get().getUsername()).isEqualTo(username);
    }

    public static Stream<Arguments> provideUsername() {
        return Stream.of(Arguments.of("user1", true),
            Arguments.of("falsyUser", false), Arguments.of(null, false));
    }
}