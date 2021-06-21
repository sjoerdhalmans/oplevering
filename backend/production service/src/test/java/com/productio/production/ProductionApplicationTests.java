package com.productio.production;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductionApplicationTests {
	@Test
	void testThis() {
		assertEquals(2, 2);
	}
}
