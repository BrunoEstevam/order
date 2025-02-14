package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.service.impl.CalculateOrderPriceImpl;

@ExtendWith(value = MockitoExtension.class)
public class CalculateOrderPriceImplTest {
	
	@InjectMocks
	private CalculateOrderPriceImpl calculateOrderPriceImpl;
	
	@Test
	@DisplayName("Deve calcular e retornar o valor exato")
	private void shouldCalculateListOfItemsAndRreturExactPrice() {
		ItemEntity item = new ItemEntity();
		item.setQuantity(1);
		item.setPrice(new BigDecimal(10.20));
		ItemEntity item2 = new ItemEntity();
		item2.setQuantity(1);
		item2.setPrice(BigDecimal.TEN);
		ItemEntity item3 = new ItemEntity();
		item3.setQuantity(2);
		item3.setPrice(BigDecimal.TEN);
		ItemEntity item4 = new ItemEntity();
		item4.setQuantity(4);
		item4.setPrice(BigDecimal.TEN);
		
		List<ItemEntity> items = List.of(item, item2, item3, item4);
		
		BigDecimal totalPrice = calculateOrderPriceImpl.calculate(items).setScale(2, RoundingMode.HALF_EVEN);
		
		assertEquals(new BigDecimal(80.20).setScale(2, RoundingMode.HALF_EVEN), totalPrice);
	}
	
    @Test
    @DisplayName("Deve calcular e retornar o valor zero para a lista")
    private void shouldReturnZeroPriceForEmptyList() {
        List<ItemEntity> items = Arrays.asList();
        BigDecimal totalPrice = calculateOrderPriceImpl.calculate(items);

        assertEquals(BigDecimal.ZERO, totalPrice);
    }
}
