package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Producto;

public class ProductoDialogControlador implements Initializable {

    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtProducto;
    @FXML
    private TextField txtCategoria;

    private Producto producto;

    private ObservableList<Producto> productos;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initAtributtes(ObservableList<Producto> productos) {
        this.productos = productos;
    }

    public void initAtributtes(ObservableList<Producto> productos, Producto producto) {
        this.productos = productos;
        this.producto = producto;
        // cargo los datos de la persona
        this.txtProducto.setText(this.producto.getCategoria());
        this.txtCategoria.setText(this.producto.getProducto());
        this.txtValor.setText(this.producto.getValor() + "");
    }

    public Producto getProducto() {
        return producto;
    }

    @FXML
    private void guardar(ActionEvent event) {

       
        String categoria = this.txtCategoria.getText();
        String producto = this.txtProducto.getText();
        int valor = Integer.parseInt(this.txtValor.getText());

        
        Producto p = new Producto(categoria, producto, valor);

        
        if (!productos.contains(p)) {

           
            if (this.producto != null) {

                
                this.producto.setCategoria(categoria);
                this.producto.setProducto(producto);
                this.producto.setValor(valor);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Se ha modificado correctamente");
                alert.showAndWait();

            } else {
                

                this.producto = p;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Se ha añadido correctamente");
                alert.showAndWait();

            }

            
            Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("El producto ya existe con el mismo valor y precio");
            alert.showAndWait();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        this.producto = null;
        
        Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
        stage.close();
    }

}
