package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Producto;

public class ProductoController implements Initializable {

    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn colCategoria;
    @FXML
    private TableColumn colProducto;
    @FXML
    private TableColumn colValor;

    private ObservableList<Producto> productos;
    private ObservableList<Producto> filtroProductos;

    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    
    @FXML
    private TextField txtFiltrarCategoria;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productos = FXCollections.observableArrayList();
        filtroProductos = FXCollections.observableArrayList();

        this.tblProductos.setItems(productos);

        this.colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        this.colProducto.setCellValueFactory(new PropertyValueFactory("producto"));
        this.colValor.setCellValueFactory(new PropertyValueFactory("valor"));
    }

    @FXML
    private void agregarProducto(ActionEvent event) {

        try {

           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/compraDialogVista.fxml"));

            
            Parent root = loader.load();

            
            ProductoDialogControlador controlador = loader.getController();
            controlador.initAtributtes(productos);

            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

           
            Producto p = controlador.getProducto();
            if (p != null) {
                productos.add(p);
                if (p.getCategoria().toLowerCase().contains(this.txtFiltrarCategoria.getText().toLowerCase())) {
                    this.filtroProductos.add(p);
                }
                this.tblProductos.refresh();
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes rellenar el formulario");
            alert.showAndWait();
        }

    }

    @FXML
    private void modificar(ActionEvent event) {

        Producto p = this.tblProductos.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un PRODUCTO para modificar");
            alert.showAndWait();
        } else {

            try {

                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/compraDialogVista.fxml"));

                
                Parent root = loader.load();

                
                ProductoDialogControlador controlador = loader.getController();
                controlador.initAtributtes(productos, p);

                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                
                
                Producto pSeleccionado = controlador.getProducto();
                if (pSeleccionado != null) {
                    if (!pSeleccionado.getCategoria().toLowerCase().contains(this.txtFiltrarCategoria.getText().toLowerCase())) {
                        this.filtroProductos.remove(pSeleccionado);
                    }
                    this.tblProductos.refresh();
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }

    }

    @FXML
    private void eliminar(ActionEvent event) {

        Producto p = this.tblProductos.getSelectionModel().getSelectedItem();

        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un PRODUCTO para eliminar");
            alert.showAndWait();
        } else {
            
            this.productos.remove(p);
            this.filtroProductos.remove(p);
            this.tblProductos.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Producto eliminado");
            alert.showAndWait();

        }

    }

    @FXML
    private void filtrarCategoria(KeyEvent event) {

        String filtroCategoria = this.txtFiltrarCategoria.getText();

       
        if (filtroCategoria.isEmpty()) {
            this.tblProductos.setItems(productos);
        } else {

            
            this.filtroProductos.clear();

            for (Producto p : this.productos) {
                if (p.getCategoria().toLowerCase().contains(filtroCategoria.toLowerCase())) {
                    this.filtroProductos.add(p);
                }
            }

            this.tblProductos.setItems(filtroProductos);

        }

    }


    

}