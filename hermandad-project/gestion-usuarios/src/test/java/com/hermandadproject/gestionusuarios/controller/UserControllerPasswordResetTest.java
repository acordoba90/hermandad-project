package com.hermandadproject.gestionusuarios.controller;

import com.hermandadproject.gestionusuarios.logging.CurrentUserContext;
import com.hermandadproject.gestionusuarios.model.dto.SolicitudRestauracionContrasenaRequest;
import com.hermandadproject.gestionusuarios.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerPasswordResetTest {

    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController(userService, mock(CurrentUserContext.class)))
                .setValidator(validator)
                .build();
    }

    @Test
    void requestPasswordResetDevuelveRespuestaGenericaSinTokenNiEnlace() throws Exception {
        mockMvc.perform(post("/api/users/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correoElectronico\":\"usuario@correo.com\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.mensaje").value(
                        "Si el correo pertenece a una cuenta valida, se ha enviado un enlace para restaurar la contrasena."
                ))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("token"))))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("restaurar-contrasena"))));

        verify(userService).solicitarRestauracionContrasena(any(SolicitudRestauracionContrasenaRequest.class));
    }

    @Test
    void requestPasswordResetRechazaCorreoInvalido() throws Exception {
        mockMvc.perform(post("/api/users/password-reset/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"correoElectronico\":\"correo-invalido\"}"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @Test
    void confirmPasswordResetNoDevuelveTokenNiContrasena() throws Exception {
        mockMvc.perform(post("/api/users/password-reset/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "token": "token",
                                  "nuevaContrasena": "NuevaPass1!",
                                  "confirmacionContrasena": "NuevaPass1!"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("La contrasena se ha actualizado correctamente."))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("NuevaPass1!"))))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("token"))));
    }
}
