import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
		
		
public class Practico {

	public static void main(String[] args) {
		
		Practico.TextConexion();
		
		Collection <Partido> partidos= new ArrayList<Partido>();
			
				Path pathResultados = Paths.get("C:\\Users\\Acer\\Desktop\\Java\\resultados_entrega_2.csv");
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
						Equipo equipo1 = new Equipo(campos[1]);
						Equipo equipo2 = new Equipo(campos[4]);
						Partido partido =new Partido (equipo1,equipo2);
						partido.setGolesEq1(Integer.parseInt(campos[2]));
						partido.setGolesEq2(Integer.parseInt(campos[3]));
						partidos.add(partido);
					}
					}
				
				int puntos1=0;
				int puntos2=0;
				Path pathPronostico = Paths.get("C:\\Users\\Acer\\Desktop\\Java\\pronosticos_entrega_2.csv");
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
						Participante participante1  = new Participante(campos[0]);
						Participante participante2 = new Participante(campos[0]);
						Equipo equipo1Pronostico = new Equipo(campos[1]);
						Equipo equipo2Pronostico = new Equipo(campos[5]);
						Partido partido =null;
						
						for (Partido partidoCol: partidos) {
							if(partidoCol.getEquipo1().getNombreEquipo().equals(equipo1Pronostico.getNombreEquipo())&&
								partidoCol.getEquipo2().getNombreEquipo().equals(equipo2Pronostico.getNombreEquipo())){
								
								partido=partidoCol;
							}
						}
						
						Equipo equipo=null;
						EnumResultado resultado=null;
						
						if("X".equals(campos[2])) {
							if ("Mariana".equals(campos[0])) {
								
								if (partido.getGolesEq1()>partido.getGolesEq2()) {
									participante1.sumarPuntos(1);
									equipo=equipo1Pronostico;
									resultado=EnumResultado.GANADOR;
								}
							}else if ("Pedro".equals(campos[0])) {
								
									if (partido.getGolesEq1()>partido.getGolesEq2()){
										participante2.sumarPuntos(1);
										equipo=equipo1Pronostico;
										resultado=EnumResultado.GANADOR;
									}
							}
						}else if("X".equals(campos[3])) {
								if ("Mariana".equals(campos[0])) {
				
									if (partido.getGolesEq1()==partido.getGolesEq2()){
									participante1.sumarPuntos(1);
									equipo=equipo1Pronostico;
									resultado=EnumResultado.EMPATE;
									}
								}else if ("Pedro".equals(campos[0])) {
									
									if (partido.getGolesEq1()==partido.getGolesEq2()){
									participante2.sumarPuntos(1);
									equipo=equipo1Pronostico;
									resultado=EnumResultado.EMPATE;
									}
								}
						
						}else if("X".equals(campos[4])) {
								if ("Mariana".equals(campos[0])) {
									
									if (partido.getGolesEq1()<partido.getGolesEq2()){
									participante1.sumarPuntos(1);
									equipo=equipo1Pronostico;
									resultado=EnumResultado.PERDEDOR;
									}
									
									}else if ("Pedro".equals(campos[0])) {
										
										if (partido.getGolesEq1()<partido.getGolesEq2()){
										participante2.sumarPuntos(1);
										equipo=equipo1Pronostico;
										resultado=EnumResultado.PERDEDOR;
										}
									}
						}
					
						Pronostico pronostico= new Pronostico(partido,equipo,resultado,participante1);
						Pronostico pronostico2= new Pronostico(partido,equipo,resultado,participante2);
						puntos1+= pronostico.getParticipante().getPuntos();
						puntos2+= pronostico2.getParticipante().getPuntos();
					}
				}	
					System.out.println("Los puntos obtenido por Mariana fue de: " + puntos1);
					System.out.println("Los puntos obtenido por Pedro fue de: " + puntos2);
					
				}

		
		private static void  TextConexion () {
			
			Collection <Partido> partidos= new ArrayList<Partido>();
			
			Conexion conexion= new Conexion ();
			Connection cn =null;
			Statement stm=null;
			ResultSet rs=null;
			
		try {
			cn= conexion.conectar();
			stm=cn.createStatement();
			rs=stm.executeQuery("select pr.idparticipante , p.nombre, pr.idequipo, pr.resultado,  \r\n"
					+ "pa.equipo1, pa.equipo2, pa.goleseq1, pa.goleseq2 , pa.ronda\r\n"
					+ "from pronosticos pr\r\n"
					+ "INNER JOIN participante p ON pr.idparticipante = p.idparticipante\r\n"
					+ "INNER JOIN partido pa ON pr.idPartido= pa.idPartido");
		
			
			while(rs.next()) {
				
				int idparticipante = Integer.parseInt(rs.getString(1));  //rs.getInt(1);
				Equipo equipo1= new Equipo (rs.getString(5));
				Equipo equipo2= new Equipo (rs.getString(6));
				String nombre = rs.getString(2);
				String Idequipo= rs.getString(3);
				EnumResultado resultado = null;
				int goleseq1= rs.getInt(7);
				int goleseq2= rs.getInt(8);
				int ronda= rs.getInt(9);
				
				//System.out.println(idparticipante + "-" + nombre + "-" + Idequipo + "-" + resultado + "-" + equipo1 +
				//		"-" + equipo2 + "-" + goleseq1 + "-" + goleseq2 + "-" + ronda);
				
				Participante p= new Participante (rs.getString(2));
				Partido partido =null;
				
				for (Partido partidoCol: partidos) {
					if(partidoCol.getEquipo1().getNombreEquipo().equals(rs.getString(5))&&
						partidoCol.getEquipo2().getNombreEquipo().equals(rs.getString(6))){
						
						partido=partidoCol;
			
			}
				}
				
				Equipo equipo=null;
				
				
				if("G".equals(rs.getString(4))) {
							equipo=equipo1;
							resultado=EnumResultado.GANADOR;
				
							System.out.println("suma tres puntos: "+ rs.getString(2));			
							
				}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
//		} finally {
//			
//			try {
//				if (rs!= null) {
//					rs.close();
//				}
//				
//				if (stm != null) {
//					stm.close();
//				}
//				
//				if (cn != null) {
//					cn.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
		}
			
		}
		
	}
