package pe.edu.proyecto.grupo05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.proyecto.grupo05.dto.ZapatillaDetailDto;
import pe.edu.proyecto.grupo05.dto.ZapatillaDto;
import pe.edu.proyecto.grupo05.dto.ZapatillaEditDto;
import pe.edu.proyecto.grupo05.entity.Categoria;
import pe.edu.proyecto.grupo05.entity.Marca;
import pe.edu.proyecto.grupo05.repository.CategoriaRepository;
import pe.edu.proyecto.grupo05.repository.MarcaRepository;
import pe.edu.proyecto.grupo05.service.MaintenanceService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/restricted")
    public String restricted(Model model){
        return "restricted";
    }

    @GetMapping("/start")
    public String start(Model model) {
            List<ZapatillaDto> zapatillas = maintenanceService.findAllZapatillas();
            model.addAttribute("zapatillaStart", zapatillas);
            return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        ZapatillaDetailDto zapatillaDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("zapatillaDetail", zapatillaDetailDto);
        return "maintenance-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        ZapatillaEditDto zapatillaEditDto = maintenanceService.findDetailEditbyId(id);
        model.addAttribute("zapatillaEdit", zapatillaEditDto);
        model.addAttribute("marcas", marcaRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "maintenance-edit";
    }

    @PostMapping("/edit-confirm")
    public String edit(@ModelAttribute ZapatillaEditDto zapatillaEditDto, Model model) {

        maintenanceService.updateZapatilla(zapatillaEditDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("zapatillaCreate", new ZapatillaEditDto
                (null, "", null, null, "", null, null, ""));

        Iterable<Marca> marcasIterable = marcaRepository.findAll();
        List<Marca> marcas = StreamSupport.stream(marcasIterable.spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("marcas", marcas);

        Iterable<Categoria> categoriasIterable = categoriaRepository.findAll();
        List<Categoria> categorias = StreamSupport.stream(categoriasIterable.spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("categorias", categorias);

        return "maintenance-create";
    }

    @PostMapping("/create-confirm")
    public String create(@ModelAttribute ZapatillaEditDto zapatillaEditDto, Model model) {

        maintenanceService.createZapatilla(zapatillaEditDto);
        return "redirect:/maintenance/start";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id) throws Exception {
        maintenanceService.deleteZapatilla(id);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/cliente-inicio")
    public String clienteInicio(Model model) {
        List<ZapatillaDto> zapatillas = maintenanceService.findAllZapatillas();
        model.addAttribute("clienteInicio", zapatillas);
        return "cliente-inicio";
    }

    @GetMapping("/compra/{id}")
    public String clienteCompra(@PathVariable Integer id, Model model) {

        ZapatillaDetailDto zapatillaDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("clienteCompra", zapatillaDetailDto);
        return "cliente-compra";
    }

    @PostMapping("/confirmarCompra/{id}")
    public String confirmarCompra(@PathVariable Integer id) {
        // Obtener detalles de la zapatilla actual
        ZapatillaDetailDto zapatillaDetail = maintenanceService.findDetailById(id);

        // Comprobar si hay stock disponible
        if (zapatillaDetail.stock() <= 0) {

            return "redirect:/maintenance/compra/" + id;
        }

        // Obtener la versión editable para obtener los ID correctos
        ZapatillaEditDto currentZapatilla = maintenanceService.findDetailEditbyId(id);

        // Crear un DTO con stocks actualizados, manteniendo todos los demás valores, incluidos los ID.
        ZapatillaEditDto zapatillaUpdate = new ZapatillaEditDto(
                currentZapatilla.zapatillaId(),
                currentZapatilla.modelo(),
                currentZapatilla.marcaId(),
                currentZapatilla.categoriaId(),
                currentZapatilla.descripcion(),
                currentZapatilla.precio(),
                currentZapatilla.stock() - 1, // Reducir stock en 1
                currentZapatilla.urlimg()
        );

        // Update the zapatilla
        maintenanceService.updateZapatilla(zapatillaUpdate);

        // Redireccionar al inicio del cliente después de la compra fue realizada
        return "redirect:/maintenance/cliente-inicio";
    }

}


