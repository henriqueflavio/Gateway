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
@Table(name="TB_OCORRENCIA_GP")
public class Occurrence {
	
	@SequenceGenerator(name="seqOccurrence", sequenceName="SEQ_ID_OCORRENCIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqOccurrence")
	@Column(name="ID_OCORRENCIA")
	@Getter @Setter
	private Long idOccurrence;
	
	@Column(name="NM_SERVICO")
	@Getter @Setter
	private String nameService;
	
	@Column(name="DS_PARAMETRO_ENTRADA")
	@Getter @Setter
	private String descriptonParameterService;
	
	@Column(name="DT_PROCESSAMENTO")
	@Getter @Setter
	private LocalDateTime processingDate;
	
	@Column(name="CD_RETORNO_WEBSERVICE")
	@Getter @Setter
	private Integer codeReturnWs;
	
	@Column(name="DS_RETORNO_WEBSERVICE")
	@Getter @Setter
	private String descriptionReturnWs;
	
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
