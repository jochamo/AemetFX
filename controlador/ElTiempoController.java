package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modelo.DatosINE;
import modelo.TiempoCiudad;

public class ElTiempoController {

	//Componentes de la vista
    @FXML private TextField txt_ciudad;
    @FXML private Label lbl_Fecha;
    @FXML private Label lbl_TempMax;
    @FXML private Label lbl_TempMin;
    @FXML private Label lbl_estadoCielo;

    //Atributos del controlador
    private DatosINE datosINE;
    
    @FXML
    void initialize() {

    	//Leer datos de los ficheros XML
    	this.datosINE = new DatosINE();
    	
    	//Asignar al TextField Municipio la lista de Municipios a buscar...    	
    	TreeSet<String> listaNombresMunicipios = new TreeSet<String>(this.datosINE.getMunicipios());            
    	TextFields.bindAutoCompletion(txt_ciudad, listaNombresMunicipios);
    }

    @FXML
    void actualizarTiempo(KeyEvent event) {

    	//El tiempo se actualiza al pulsar ENTER
    	if (event.getCode() == KeyCode.ENTER){

    		//Obtener el código de la ciudad
    		String ciudad = this.datosINE.obtenerCodigoCiudad( txt_ciudad.getText() );

    		//Si existe la ciudad seleccionada...
    		if (ciudad != null) {
    			
    			//Obtener datos meteorológicos de una ciudad		
        		TiempoCiudad tiempo = new TiempoCiudad(ciudad);

        		//1.- Fecha				
        		String fechaString = tiempo.getFecha(0);

        		//Pasar de String a Date
        		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");		
        		Date fechaUsuario = null;
        		try {
        			fechaUsuario = formatoDelTexto.parse( fechaString );
        		} catch (ParseException e) {	
        			e.printStackTrace();
        		}

        		//Cambiar el formato de le fecha
        		formatoDelTexto = new SimpleDateFormat("EEEE',' dd 'de' MMMM 'de' yyyy", new Locale("ES"));
        		lbl_Fecha.setText(formatoDelTexto.format(fechaUsuario));

        		//2.- Resto de datos meteorologicos
        		lbl_TempMax.setText(temperatura(tiempo.getTempMax(0)));
        		lbl_TempMin.setText(temperatura(tiempo.getTempMin(0)));
        		lbl_estadoCielo.setText(tiempo.getEstadoCielo(0));	
    		}    		
    	}    	
    }

    private String temperatura(String t) {
    	return t + "ºC";
    }

}