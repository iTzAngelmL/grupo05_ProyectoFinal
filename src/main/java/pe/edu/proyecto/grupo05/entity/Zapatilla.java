package pe.edu.proyecto.grupo05.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zapatilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zapatillaId;
    private String modelo;
    /*private Integer marcaId;*/
    /*private Integer categoriaId;*/
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String urlimg;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
