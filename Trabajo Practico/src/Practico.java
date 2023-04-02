import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
		
		
public class Practico {

	public static void main(String[] args) {
		
		Collection <Partido> partidos= new ArrayList<Partido>();
			
				Path pathResultados = Paths.get("C:\\Users\\Acer\\Desktop\\Java\\resultados.csv");
				List <String> lineasResultados=null; 
				try {
					
					lineasResultados = Files.readAllLines(pathResultados);
					
				}catch (IOException e) {
					System.out.println("archivo sin permiso o inexistente");
					System.exit(1);
					
				}
				boolean primera=true;
				for (String lineaResultado : lineasResultados) {
					
				if (primera) {
						primera=false;
					}else {
					
					String[] campos=lineaResultado.split(";");
						Equipo equipo1 = new Equipo(campos[0]);
						Equipo equipo2 = new Equipo(campos[3]);
						Partido partido =new Partido (equipo1,equipo2);
						partido.setGolesEq1(Integer.parseInt(campos[1]));
						partido.setGolesEq2(Integer.parseInt(campos[2]));
						partidos.add(partido);
					}
					}
				
				int puntos=0;
				
				Path pathPronostico = Paths.get("C:\\Users\\Acer\\Desktop\\Java\\pronosticos.csv");
				List <String> lineasPronostico=null;
			
				try {
					
					lineasPronostico = Files.readAllLines(pathPronostico);
					
				}catch (IOException e) {
					System.out.println("archivo sin permiso o inexistente" + e.getMessage());
					System.exit(1);
					
				}
				primera=true;
				
				for (String lineaPronostico: lineasPronostico) {
				if (primera) {
						primera=false;
					}else {
				
						String[] campos=lineaPronostico.split(";");
						Equipo equipo1 = new Equipo(campos[0]);
						Equipo equipo2 = new Equipo(campos[4]);
						Partido partido =null;
						
						for (Partido partidoCol: partidos) {
							if(partidoCol.getEquipo1().getNombreEquipo().equals(equipo1.getNombreEquipo())&&
								partidoCol.getEquipo2().getNombreEquipo().equals(equipo2.getNombreEquipo())){
								
								partido=partidoCol;
							}
						}
						Equipo equipo=null;
						EnumResultado resultado=null;
						if("X".equals(campos[1])) {
							equipo=equipo1;
							resultado=EnumResultado.GANADOR;
    					}
						if("X".equals(campos[2])) {
							equipo=equipo1;
							resultado=EnumResultado.EMPATE;
						}
						if("X".equals(campos[3])) {
							equipo=equipo1;
							resultado=EnumResultado.PERDEDOR;
						}
						Pronostico pronostico= new Pronostico(partido, equipo,resultado);
						puntos += pronostico.puntos();
						
				}
				
			}
				System.out.println("Los puntos obtenido por el usuario fueron:");
				System.out.println(puntos);
				

}}