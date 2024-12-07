package tech.leonam.encurtadorurl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroDTO {
    private int codErro;
    private String msgErro;
}
