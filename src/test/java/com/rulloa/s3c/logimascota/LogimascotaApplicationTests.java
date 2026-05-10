package com.rulloa.s3c.logimascota;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class LogimascotaApplicationTests {
	private com.rulloa.s3c.logimascota.model.Envio envio;

	@BeforeEach
	void setUp() {
		envio = new com.rulloa.s3c.logimascota.model.Envio();
		envio.setEstado(com.rulloa.s3c.logimascota.model.Envio.Estado.PENDIENTE);
	}

	@AfterEach
	void tearDown() {
		envio = null;
	}

	@Test
	void shouldAdvanceEnvioStateUntilEntregado() {
		envio.avanzaEstado();
		envio.avanzaEstado();
		envio.avanzaEstado();

		assertEquals(com.rulloa.s3c.logimascota.model.Envio.Estado.ENTREGADO, envio.getEstado());
	}

	@Test
	void shouldKeepPendienteWhenRetrocedeFromInitialState() {
		envio.retrocedeEstado();

		assertEquals(com.rulloa.s3c.logimascota.model.Envio.Estado.PENDIENTE, envio.getEstado());
	}

}
