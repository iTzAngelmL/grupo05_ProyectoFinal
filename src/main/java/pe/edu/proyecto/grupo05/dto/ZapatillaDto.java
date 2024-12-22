package pe.edu.proyecto.grupo05.dto;

public record ZapatillaDto(
        Integer zapatillaId,
        String modelo,
        String marca,
        String categoria,
/*        String descripcion,*/
        Double precio,
        Integer stock,
        String urlimg
        ) {
}
