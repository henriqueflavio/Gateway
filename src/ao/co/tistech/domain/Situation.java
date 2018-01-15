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
@Table(name="TB_SITUACAO_GP")
public class Situation {
	
	@SequenceGenerator(name="seqSituation", sequenceName="SEQ_ID_SITUACAO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqSituation")
	@Column(name="ID_SITUATION")
	@Getter @Setter
	private long idSituation;
	
	@Column(name="CD_SITUATION")
	@Getter @Setter
	private String codeSituation;
	
	@Column(name="NM_SITUATION")
	@Getter @Setter
	private String nameSituation;
	
	@Column(name="DT_CRIACAO")
	@Getter @Setter
	private LocalDateTime creationDate;
	
	@Column(name="DT_ALTERACAO")
	@Getter @Setter
	private LocalDateTime changeDate;
	
	@Column(name="DT_EXCLUSAO")
	@Getter @Setter
	private LocalDateTime exclusionDate;
}
