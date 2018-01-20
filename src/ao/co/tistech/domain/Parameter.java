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
@Table(name="TB_PARAMETRO_GP")
public class Parameter {
	
	@SequenceGenerator(name="seqParameter", sequenceName="SEQ_ID_PARAMETRO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqParametro")
	@Column(name="ID_PARAMETRO")
	@Getter @Setter
	private Long idParameter;
	
	@Column(name="DS_PARAMETRO")
	@Getter @Setter
	private String descriptionParameter;
	
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
