package modelo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TiempoCiudad {

	//Atributos
	private ArrayList<TiempoDia> tiempoDia;
	private String codigo;
	
	//Constructor
	public TiempoCiudad(String codigo) {

		//Inicializar atributos
		this.tiempoDia = new ArrayList<TiempoDia>();
		this.codigo    = codigo;
		
		//Obtener datos meteorológicos de la ciudad		
		this.descargar_crear_XML();
		this.leerInformacionXML();
	}

	//Métodos	
	public void descargar_crear_XML() {

		try {			
			//Inciar URL donde se encuentra el fichero a leer
			URL url   = new URL("http://www.aemet.es/xml/municipios/localidad_" + this.codigo + ".xml");
			
			//Utilizar el scanner con el fichero especificado en la URL
			Scanner s = new Scanner( url.openStream() );

			//Crear fichero local donde guardar la información
			String fichero = "localidad_" + this.codigo + ".xml";
			PrintWriter pw = new PrintWriter(new File(fichero));

			String linea;

			//Leer del scanner
			while(s.hasNext()) {

				//Leer linea del fichero de Internet
				linea = s.nextLine();

				//Escribir linea en el fichero Local
				pw.println( linea );				   
			}

			pw.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

	public void leerInformacionXML() {

		try {

			File fXmlFile = new File("localidad_" + this.codigo + ".xml");			

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			//Recuperar una lista con los elementos DIA
			NodeList listaDias = doc.getElementsByTagName("dia");
									
			//Recorrer lista de los elementos DIA
			for (int i = 0; i < listaDias.getLength(); i++) {

				Node elementoDia = listaDias.item(i);

				//
				TiempoDia dia = new TiempoDia();

				//
				if (elementoDia.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) elementoDia;

					//1.- dia
					dia.setFecha( eElement.getAttribute("fecha") );

					//Estado del cielo
					NodeList cieloLista = doc.getElementsByTagName("estado_cielo");
					Node cieloNodo = cieloLista.item(2);
					Element cieloElement = (Element) cieloNodo;

					//2.- estado cielo
					dia.setEstadoCielo( cieloElement.getAttribute("descripcion") );					

					//Temperatura
					NodeList nTemp =  eElement.getElementsByTagName("temperatura");
					for (int j = 0; j < nTemp.getLength(); j++) {

						Node temperatura = nTemp.item(j);
						Element eTemp= (Element) temperatura;

						//3.- Temperatura máxima y mínima
						dia.setTempMax( eTemp.getElementsByTagName("maxima").item(0).getTextContent() );
						dia.setTempMin( eTemp.getElementsByTagName("minima").item(0).getTextContent() );
					}				
				}
				
				//Añadir el tiempo del dia
				this.tiempoDia.add( dia );
			}			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFecha(int dia) {

		return this.tiempoDia.get(dia).getFecha();
	}

	public String getEstadoCielo(int dia) {

		return this.tiempoDia.get(dia).getEstadoCielo();
	}

	public String getTempMax(int dia) {

		return this.tiempoDia.get(dia).getTempMax();
	}

	public String getTempMin(int dia) {

		return this.tiempoDia.get(dia).getTempMin();
	}	
	
}