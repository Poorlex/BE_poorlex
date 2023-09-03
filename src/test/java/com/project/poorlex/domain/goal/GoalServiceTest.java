package com.project.poorlex.domain.goal;
import com.project.poorlex.api.service.AuthService;
import com.project.poorlex.api.service.GoalService;
import com.project.poorlex.domain.member.MemberRepository;
import com.project.poorlex.dto.goal.GoalListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = GoalService.class)
public class GoalServiceTest {

    @Autowired
    private GoalService goalService;

    @MockBean
    private GoalRepository goalRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("Test getAllGoals")
    public void testGetAllGoals() {

        List<Goal> goalList = new ArrayList<>(Arrays.asList(
                Goal.builder()
                        .id(2L)
                        .amount(500000)
                        .name("목표는 에어맥스")
                        .currentAmount(2000)
                        .startDate(LocalDateTime.of(2023, 9, 3, 9, 0))
                        .endDate(LocalDateTime.of(2023, 12, 31, 18, 0))
                        .isCompleted(false)
                        .build()
        ));

        when(goalRepository.findAllByMemberId_OauthIdAndIsCompletedFalse(anyString())).thenReturn(goalList);


        String oauthId = "oauthId";

        List<GoalListResponse> result = goalService.getAllGoals(oauthId);

        List<GoalListResponse> goalListResponse = GoalListResponse.of(goalList);

        assertGoalsListEquals(goalListResponse, result);
    }

    private void assertGoalsListEquals(List<GoalListResponse> expected, List<GoalListResponse> actual) {
        assertEquals(expected.size(), actual.size(), "크기가 일치하지 않습니다.");

        for (int i = 0; i < expected.size(); i++) {
            GoalListResponse expectedItem = expected.get(i);
            GoalListResponse actualItem = actual.get(i);

            assertEquals(expectedItem.getId(), actualItem.getId(), "ID가 일치하지 않습니다. " + i);
            assertEquals(expectedItem.getName(), actualItem.getName(), "이름이 일치하지 않습니다. " + i);
            assertEquals(expectedItem.isCompleted(), actualItem.isCompleted(), "완료 상태가 일치하지 않습니다. " + i);
        }
    }
}
