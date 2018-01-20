package ao.co.tistech.domain;

import java.math.BigDecimal;
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
@Table(name="TB_FMOV_HEADER")
public class FMOVHeader {
	
	@SequenceGenerator(name="seqFmovHeader", sequenceName="SEQ_ID_FMOV_HEA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqFmovHeader")
	@Column(name="ID_FMOV_HEA")
	@Getter @Setter
	private long idFmovHeader;
	
	@Column(name="NM_FICHEIRO_HEADER")
	@Getter @Setter
	private String nameFile;
	
	@Column(name="ID_INSTITUICAO_ORIGEM_HEADER")
	@Getter @Setter
	private Long idInstitutionOrigin;
	
	@Column(name="ID_INSTITUICAO_DESTINO_HEADER")
	@Getter @Setter
	private Long idInstitutionDestiny;
	
	@Column(name="DT_PROCESSAMENTO_HEADER")
	@Getter @Setter
	private Long dateProcess;
	
	@Column(name="ID_ULTIMO_FICHEIRO_HEADER")
	@Getter @Setter
	private Long idLastFile;
	
	@Column(name="NU_ENTIDADE_HEADER")
	@Getter @Setter
	private Integer codeEntity;
	
	@Column(name="CD_MOEDA_HEADER")
	@Getter @Setter
	private String codeCoin;
	
	@Column(name="DS_ESPACO_HEADER")
	@Getter @Setter
	private String descriptionEspaceHeader;
	
	@Column(name="NU_REGISTRO_DETALHE_TRAILER")
	@Getter @Setter
	private Integer numberRegisterDetail;
	
	@Column(name="VL_TOTAL_TRANSACAO_TRAILER")
	@Getter @Setter
	private BigDecimal valueTotalTransaction;
	
	@Column(name="VL_TOTAL_TARIFACAO_TRAILER")
	@Getter @Setter
	private BigDecimal valueTotalRate;
	
	@Column(name="DS_ESPACO_TRAILER")
	@Getter @Setter
	private String descriptionEspaceTrailer;
	
	@Column(name="NU_SITUACAO")
	@Getter @Setter
	private Integer numberSituation;
	
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
