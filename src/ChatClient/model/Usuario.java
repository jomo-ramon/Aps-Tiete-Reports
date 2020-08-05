package ChatClient.model;

/**
 * @author PC
 *
 */
public class Usuario implements java.io.Serializable{

	private String nome;
	private String ra;
	private String industria;
	private String cargo;
	private String email;
	private String cpf;
	
	
	public Usuario(String aNome, String aCpf, String aRa, String aCargo, String aEmail, String aIndustria) {
		this.cargo = aCargo;
		this.nome = aNome;
		this.email = aEmail;
		this.cpf = aCpf;
		this.ra = aRa;
		this.industria = aIndustria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getIndustria() {
		return industria;
	}

	public void setIndustria(String industria) {
		this.industria = industria;
	}

	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean equals(Object obj) {
	    if (obj instanceof Usuario) {
	      Usuario qualquer = (Usuario) obj;
	      return this.ra.equals(qualquer.getRa());
	    }else {
	      return false;
	    }
	}

	@Override
	public String toString() {
		return 
				"nome: " +nome + "\n"
				+ "ra: " + ra + "\n"
				+ "industria: " + industria + "\n"
				+ "cargo: " + cargo + "\n"
				+ "email: " + email + "\n"
				+ "cpf: " + cpf + "\n" + "\n";
	}
}
