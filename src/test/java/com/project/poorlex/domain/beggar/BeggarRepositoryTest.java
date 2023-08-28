package com.project.poorlex.domain.beggar;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.project.poorlex.IntegrationTestSupport;

@Transactional
class BeggarRepositoryTest extends IntegrationTestSupport {

	@Autowired
	private BeggarRepository beggarRepository;

	@DisplayName("포인트를 이용해 가장 가까운 레벨을 조회힌다.")
	@Test
	void findFirstByNeedPointGreaterThanOrderByLevel() {
		// given
		Beggar beggar1 = createBeggar(1, 0, 19);
		Beggar beggar2 = createBeggar(2, 20, 199);
		Beggar beggar3 = createBeggar(3, 100, 299);
		beggarRepository.saveAll(List.of(beggar1, beggar2, beggar3));

		// when
		Beggar result1 = beggarRepository.findFirstByMinPointLessThanEqualOrderByLevelDesc(10)
			.orElse(null);
		Beggar result2 = beggarRepository.findFirstByMinPointLessThanEqualOrderByLevelDesc(20)
			.orElse(null);
		Beggar result3 = beggarRepository.findFirstByMinPointLessThanEqualOrderByLevelDesc(100)
			.orElse(null);
		Beggar result4 = beggarRepository.findFirstByMinPointLessThanEqualOrderByLevelDesc(500)
			.orElse(null);

		// then
		System.out.println("result1.getLevel() = " + result1.getLevel());
		System.out.println("result2.getLevel() = " + result2.getLevel());
		System.out.println("result3.getLevel() = " + result3.getLevel());
		System.out.println("result4.getLevel() = " + result4.getLevel());
	}

	private Beggar createBeggar(int level, int minPoint, int maxPoint) {
		return Beggar.builder()
			.level(level)
			.minPoint(minPoint)
			.maxPoint(maxPoint)
			.photoName("-")
			.photoUrl("")
			.build();
	}
}
