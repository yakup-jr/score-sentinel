package com.ss.portal.match.repository;

import com.ss.portal.match.entity.MatchResultEntity;
import com.ss.portal.utils.TestDBFacade;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.ss.portal.match.entity.MatchResultTestBuilder.aMatchResult;
import static com.ss.portal.match.repository.MatchResultRepository.Specs.byTeamId;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(TestDBFacade.class)
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
class MatchResultRepositoryTest {

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private TestDBFacade db;

    @Test
    void shouldReturnMatchByTeamIdSuccessfully() {
        // given
        db.saveAll(
            aMatchResult()
        );

        // when
        List<MatchResultEntity> matchResults =
            matchResultRepository.findAll(byTeamId(1L));

        System.out.println(matchResultRepository.findAll());

        // then
        assertThat(matchResults.size()).isEqualTo(1);
        assertThat(matchResults.get(0).getTeam().getId()).isEqualTo(1);
    }

    @Test
    void shouldReturnMatchByTeamIdReject() {
        // given
        db.saveAll(
            aMatchResult()
        );

        // when
        List<MatchResultEntity> matchResults =
            matchResultRepository.findAll(byTeamId(4L));

        // then
        assertThat(matchResults.size()).isEqualTo(0);
    }


    @Test
    void shouldReturnMatchResultsByMatchId() {
        //given
        db.saveAll(aMatchResult());
    }
}