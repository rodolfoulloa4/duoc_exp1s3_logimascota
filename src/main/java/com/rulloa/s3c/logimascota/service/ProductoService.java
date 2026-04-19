package com.rulloa.s3c.logimascota.service;
import java.util.List;
import com.rulloa.s3c.logimascota.model.Producto;

public interface ProductoService {
    List<Producto> getProductos();
    Producto getProducto(int idproducto);
    Producto addProducto(Producto producto);
    Producto updateProducto(Producto producto);
    void deleteProducto(int idproducto);

}
