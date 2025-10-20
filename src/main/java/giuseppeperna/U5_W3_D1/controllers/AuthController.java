package giuseppeperna.U5_W3_D1.controllers;

import giuseppeperna.U5_W3_D1.payloads.DipendenteDTO;
import giuseppeperna.U5_W3_D1.payloads.DipendenteLoginDTO;
import giuseppeperna.U5_W3_D1.payloads.DipendenteLoginResponseDTO;
import giuseppeperna.U5_W3_D1.payloads.DipendenteResponseDTO;
import giuseppeperna.U5_W3_D1.services.AuthService;
import giuseppeperna.U5_W3_D1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginResponseDTO login(@RequestBody @Validated DipendenteLoginDTO body) {
        return new DipendenteLoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteResponseDTO register(@RequestBody @Validated DipendenteDTO body) {
        return this.dipendenteService.save(body);
    }
}

