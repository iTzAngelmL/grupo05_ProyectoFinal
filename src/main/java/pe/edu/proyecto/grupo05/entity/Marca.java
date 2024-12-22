package pe.edu.proyecto.grupo05.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer marcaId;
    private String marca;

    @OneToMany(mappedBy = "marca")
    private List<Zapatilla> zapatillas;


}
