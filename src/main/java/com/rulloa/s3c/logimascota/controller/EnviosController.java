package com.rulloa.s3c.logimascota.controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.rulloa.s3c.logimascota.model.Envio;
import com.rulloa.s3c.logimascota.model.Producto;
import com.rulloa.s3c.logimascota.model.Ubicacion;



@RestController
@RequestMapping("/envios")
public class EnviosController {
    private List<Envio> envios = new ArrayList<>();

    public EnviosController() {
        // Agregar algunos envíos de ejemplo
        envios.add(new Envio("1", "Calle Falsa 123, Las Condes", List.of(
                new Producto("p1", "Comida para perros", 20.0, 2.0),
                new Producto("p2", "Juguete para gatos", 15.0, 0.5)
        )));

        envios.add(new Envio("2", "Avenida Siempre Viva 456, Vitacura", List.of(
                new Producto("p3", "Arena para gatos", 10.0, 5.0),
                new Producto("p4", "Collar para perros", 25.0, 0.3)
        )));

        envios.add(new Envio("3", "Boulevard de los Sueños Rotos 789, Ñuñoa", List.of(
                new Producto("p5", "Cama para perros", 50.0, 10.0),
                new Producto("p6", "Rascador para gatos", 30.0, 3.0)
        )));

        envios.add(new Envio("4","Loteo San Vicente 7, Pirque", List.of(
                new Producto("p7", "Comida para gatos", 20.0, 2.0),
                new Producto("p8", "Juguete para perros", 15.0, 0.5)
        )));

        envios.get(0).avanzaEstado();
        envios.get(0).avanzaEstado();
        envios.get(1).avanzaEstado();
        envios.get(2).avanzaEstado();
    }

    @GetMapping("list")
    public List<Envio> getEnvios() {
        return envios;
    }

    @GetMapping("ubicacion/{idenvio}")
    public Ubicacion getUbicacion(@PathVariable String idenvio) {
        int id = 0;
        try {
            id = Integer.parseInt(idenvio);
        } catch (NumberFormatException e) {
            return null;
        }
        if (id <= 0) {
            return null;
        }
        for (int i = 0; i < envios.size(); i++) {
            if (envios.get(i).getId().equals(idenvio)) {
                return envios.get(i).getUbicacion();
            }
        }
        return null;
    }
    
    

    @PostMapping("add")
    public Envio postMethodName(@RequestBody Envio envio) {
        envios.add(envio);
        return envio;
    }

    @PostMapping("update")
    public Envio updateEnvioStatus(@RequestBody Envio envio) {
        for (int i = 0; i < envios.size(); i++) {
            if (envios.get(i).getId().equals(envio.getId())) {
                envios.set(i, envio);
                break;
            }
        }
        
        return envio;
    }
    
    

}
