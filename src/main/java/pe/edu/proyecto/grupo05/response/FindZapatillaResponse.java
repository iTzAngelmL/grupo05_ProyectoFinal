package pe.edu.proyecto.grupo05.response;

public record FindZapatillaResponse(String code,
                                    String error,
                                    pe.edu.proyecto.grupo05.dto.ZapatillaDetailDto zapatillaDetailDto) {

}
