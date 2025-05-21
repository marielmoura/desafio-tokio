package com.tokiomarine.financial_transfer_scheduler.controller;

import java.math.BigDecimal;
import static java.math.BigDecimal.valueOf;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokiomarine.financial_transfer_scheduler.dto.TransferRequestDTO;
import com.tokiomarine.financial_transfer_scheduler.dto.TransferResponseDTO;
import com.tokiomarine.financial_transfer_scheduler.model.Transfer;
import com.tokiomarine.financial_transfer_scheduler.service.TransferService;

@WebMvcTest(TransferController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransferService transferService;

	@Autowired
	private ObjectMapper objectMapper;

	private TransferRequestDTO validRequest;
	private TransferRequestDTO invalidRequest;

	@BeforeEach
	void setUp() {
		validRequest = createTransferRequest("123456", "654321", valueOf(1000.0), LocalDate.now().plusDays(5));
		invalidRequest = createTransferRequest("", "654321", BigDecimal.valueOf(-100.0), LocalDate.of(2023, 12, 1));
	}

	@Test
	void testSchedule_withValidRequest_shouldReturnCreated() throws Exception {
		TransferResponseDTO transfer = createTransfer(validRequest).toDto();

		when(transferService.schedule(any(TransferRequestDTO.class))).thenReturn(transfer);

		mockMvc.perform(post("/transfers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(validRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.amount").value(1000.0))
				.andExpect(jsonPath("$.sourceAccount").value("123456"))
				.andExpect(jsonPath("$.destinationAccount").value("654321"))
				.andExpect(jsonPath("$.transferDate").value(LocalDate.now().plusDays(5).toString()))
				.andExpect(jsonPath("$.fee").value(valueOf(12.0)))
				.andExpect(jsonPath("$.schedulingDate").value(LocalDate.now().toString()));
	}

	@Test
	void testSchedule_withInvalidRequest_shouldReturnBadRequest() throws Exception {
		performBadRequestTest(invalidRequest);
	}

	@Test
	void testSchedule_withBlankSourceAccount_shouldReturnBadRequest() throws Exception {
		TransferRequestDTO request = createTransferRequest("", "654321", valueOf(1000.0), LocalDate.now().plusDays(5));

		performBadRequestTest(request, "Conta de origem não pode estar em branco");
	}

	@Test
	void testSchedule_withBlankDestinationAccount_shouldReturnBadRequest() throws Exception {
		TransferRequestDTO request = createTransferRequest("123456", "", valueOf(1000.0), LocalDate.now().plusDays(5));

		performBadRequestTest(request, "Conta de destino não pode estar em branco");
	}

	@Test
	void testSchedule_withNullAmount_shouldReturnBadRequest() throws Exception {
		TransferRequestDTO request = createTransferRequest("123456", "654321", null, LocalDate.now().plusDays(5));

		performBadRequestTest(request, "Valor é obrigatório");
	}

	@Test
	void testSchedule_withPastTransferDate_shouldReturnBadRequest() throws Exception {
		TransferRequestDTO request = createTransferRequest("123456", "654321", valueOf(1000.0),
				LocalDate.now().minusDays(1));

		performBadRequestTest(request, "A data da transferência deve ser hoje ou no futuro");
	}

	private TransferRequestDTO createTransferRequest(String sourceAccount, String destinationAccount, BigDecimal amount,
			LocalDate transferDate) {
		return new TransferRequestDTO(sourceAccount, destinationAccount, amount, transferDate);
	}

	private Transfer createTransfer(TransferRequestDTO request) {
		return new Transfer(request.getSourceAccount(), request.getDestinationAccount(), request.getAmount(),
				request.getTransferDate());
	}

	private String asJsonString(Object obj) throws Exception {
		return objectMapper.writeValueAsString(obj);
	}

	private void performBadRequestTest(TransferRequestDTO request) throws Exception {
		mockMvc.perform(post("/transfers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isBadRequest());
	}

	private void performBadRequestTest(TransferRequestDTO request, String errorMessage) throws Exception {
		mockMvc.perform(post("/transfers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].message").value(errorMessage));
	}
}
