package giuseppeperna.U5_W3_D1.services;

import giuseppeperna.U5_W3_D1.entities.Dipendente;
import giuseppeperna.U5_W3_D1.exceptions.UnauthorizedException;
import giuseppeperna.U5_W3_D1.payloads.DipendenteLoginDTO;
import giuseppeperna.U5_W3_D1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(DipendenteLoginDTO body) {
        Dipendente found = this.dipendenteService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}

