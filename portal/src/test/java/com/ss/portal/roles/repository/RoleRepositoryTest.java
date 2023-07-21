package com.ss.portal.roles.repository;

import com.ss.portal.roles.entity.RoleEntity;
import com.ss.portal.roles.enums.RoleEnum;
import org.junit.jupiter.api.AfterEach;
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
@Sql("/data/add-roles.sql")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void tearDown() {
        roleRepository.deleteAll();
        testEntityManager.clear();
    }

    @ParameterizedTest
    @MethodSource("provideRoles")
    public void should_return_role_when_name_exist(RoleEnum roleEnum,
                                                   boolean expectedFound) {
        //when
        Optional<RoleEntity> result = roleRepository.findByName(roleEnum);

        //then
        assertThat(result.isPresent()).isEqualTo(expectedFound);
        if (expectedFound) assertThat(result.get().getName()).isEqualTo(roleEnum);
    }

    private static Stream<Arguments> provideRoles() {
        return Stream.of(
            Arguments.of(RoleEnum.User, true),
            Arguments.of(RoleEnum.Admin, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRoleUpdates")
    public void should_update_name_when_id_exist(Long id,
                                                 RoleEnum newName) {
        // when
        roleRepository. updateNameById(id, newName);

        // then
        assertThat(roleRepository.findById(id).isPresent()).isEqualTo(true);
        assertThat(
            roleRepository.findById(id).get().getName()).isEqualTo(
            newName);
    }

    private static Stream<Arguments> provideRoleUpdates() {
        return Stream.of(
            Arguments.of(1L, RoleEnum.Admin),
            Arguments.of(2L, RoleEnum.User)
        );
    }

    @ParameterizedTest
    @MethodSource("provideIdDeletions")
    public void should_delete_role_when_id_exist(Long id,
                                                 boolean expectedDeleted) {
        // when
        roleRepository.deleteById(id);

        // then
        assertThat(roleRepository.findById(id).isPresent()).isEqualTo(
            !expectedDeleted);
    }

    private static Stream<Arguments> provideIdDeletions() {
        return Stream.of(
            Arguments.of(1L, true),
            Arguments.of(2L, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRoleDeletions")
    public void should_delete_role_when_name_exist(RoleEnum name,
                                                   boolean expectedDeleted) {
        // when
        roleRepository.deleteByName(name);

        // then
        assertThat(
            roleRepository.findByName(name).isPresent()).isEqualTo(
            !expectedDeleted);
    }

    private static Stream<Arguments> provideRoleDeletions() {
        return Stream.of(
            Arguments.of(RoleEnum.Admin, true),
            Arguments.of(RoleEnum.User, true)
        );
    }
}
