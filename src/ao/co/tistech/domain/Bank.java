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
@Table(name="TB_BANCO")
public class Bank {
	
	@SequenceGenerator(name="seqBank", sequenceName="SEQ_ID_BANCO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqBank")
	@Column(name="ID_BANCO")
	@Getter @Setter
	private Long idBank;
	
	@Column(name="DS_BANCO")
	@Getter @Setter
	private String descriptionBank;
	
	@Column(name="DS_SIGLA_BANCO")
	@Getter @Setter
	private String descriptionInitialsBank;
	
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
