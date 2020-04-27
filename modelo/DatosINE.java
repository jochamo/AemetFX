package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DatosINE {

	//Atributos	
	private SortedMap<String, String> municipios;

	//Constructor
	public DatosINE() {

		this.municipios = new TreeMap<String, String>();
	}

	//Métodos	

	public String obtenerCodigoCiudad(String ciudad) {

		return this.municipios.get(ciudad);
	}	

	public ArrayList<String> getMunicipios(){

		String[] info = {"", ""};				

		//
		this.municipios = new TreeMap<String, String>();
		
		try {

			InputStream entrada = getClass().getClassLoader().getResourceAsStream("ficherosINE/municipios.csv");	
			BufferedReader miBuffer = new BufferedReader(new InputStreamReader(entrada, "ISO-8859-1"));

			while (!info[0].equals("fin")) {

				//Separar datos
				info = miBuffer.readLine().split(";");				

				if (!info[0].equals("fin")) {
					
					municipios.put(info[3], info[2]);					
				}				
			}		

			miBuffer.close();

		} catch (IOException e) {

			System.out.println("Archivo no encontrado");
		}

		//Convertir en vector de cadenas para asignar al comboBox
		ArrayList<String> mun = new ArrayList<String>();
		
		for (Map.Entry<String, String> entry : this.municipios.entrySet()) {

			mun.add( entry.getKey() );			
		}

		return mun;
	}

}