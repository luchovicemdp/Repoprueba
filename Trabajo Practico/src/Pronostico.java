
public class Pronostico {
	
	private Partido partido;
	private Equipo equipo;
	private EnumResultado resultado;
	private Participante participante;
	
	
	
	public Pronostico(Partido partido, Equipo equipo, EnumResultado resultado,Participante participante) {
		super();
		this.partido = partido;
		this.equipo = equipo;
		this.resultado = resultado;
		this.participante=participante;
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

	public Participante getParticipante() {
		return participante;
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
	
	
	public void setParticipante(Participante participante) {
		this.participante=participante;
	}
	
	
	public int puntos() {
		
		EnumResultado resultadoReal = this.partido.resultado(this.equipo);
		
		if(this.resultado.equals(resultadoReal)) {
			this.participante.sumarPuntos(1);
			return 1;
		}else {
			return 0;
		}
	}
	

}
