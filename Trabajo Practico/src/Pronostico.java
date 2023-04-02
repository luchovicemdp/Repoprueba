
public class Pronostico {
	
	private Partido partido;
	private Equipo equipo;
	private EnumResultado resultado;
	
	
	public Pronostico(Partido partido, Equipo equipo, EnumResultado resultado) {
		super();
		this.partido = partido;
		this.equipo = equipo;
		this.resultado = resultado;
	}


	public Partido getPartido() {
		return partido;
	}


	public void setPartido(Partido partido) {
		this.partido = partido;
	}


	public Equipo getEquipo() {
		return equipo;
	}


	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}


	public EnumResultado getResultado() {
		return resultado;
	}


	public void setResultado(EnumResultado resultado) {
		this.resultado = resultado;
	}
	
	
	public int puntos() {
		
		EnumResultado resultadoReal = this.partido.resultado(this.equipo);
		
		if(this.resultado.equals(resultadoReal)) {
			return 1;
		}else {
			return 0;
		}
	}
	

}
