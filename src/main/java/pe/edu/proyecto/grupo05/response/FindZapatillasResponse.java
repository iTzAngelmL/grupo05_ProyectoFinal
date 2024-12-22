package pe.edu.proyecto.grupo05.response;

import pe.edu.proyecto.grupo05.dto.ZapatillaDto;

public record FindZapatillasResponse(String code,
                                     String error,
                                     Iterable<ZapatillaDto> zapatillas) {

}
