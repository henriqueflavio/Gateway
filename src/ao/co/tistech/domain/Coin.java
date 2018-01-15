package ao.co.tistech.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TB_MOEDA_GP")
public class Coin {
	
	@SequenceGenerator(name="seqCoin", sequenceName="SEQ_ID_MOEDA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqCoin")
	@Column(name="ID_MOEDA")
	@Getter @Setter
	private long idCoin;
	
	@Column(name="DS_MOEDA")
	@Getter @Setter
	private String descriptionCoin;
	
	@Column(name="SG_MOEDA")
	@Getter @Setter
	private String initialsCoin;
	
	@Column(name="DT_CRIACAO")
	@Getter @Setter
	private LocalDateTime creationDate;
	
	@Column(name="DT_ALTERACAO")
	@Getter @Setter
	private LocalDateTime changeDate;
	
	@Column(name="DT_EXCLUSAO")
	@Getter @Setter
	private LocalDateTime exclusionDate;
	
	@Column(name="FL_ATIVO")
	@Getter @Setter
	private Integer flagActive;

}
