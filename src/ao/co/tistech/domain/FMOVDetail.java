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
@Table(name="TB_FMOV_DETALHE")
public class FMOVDetail {
	
	@SequenceGenerator(name="seqFmovDetail", sequenceName="SEQ_ID_FMOV_HEA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqFmovDetail")
	@Column(name="ID_FMOV_DET")
	@Getter @Setter
	private long idFmovDetail;
	
	@Column(name="ID_FMOV_HEA")
	@Getter @Setter
	private String idFmovHeader;
	
	@Column(name="NU_TIPO_REGISTRO")
	@Getter @Setter
	private Integer numberTypeRegister;
	
	@Column(name="CD_PROCESSAMENTO")
	@Getter @Setter
	private Long codeProcess;
	
	@Column(name="DT_TRANSACAO_CLIENTE")
	@Getter @Setter
	private LocalDateTime dateTransaction;
	
	@Column(name="VL_MONTANTE_OPERACAO")
	@Getter @Setter
	private BigDecimal valueAmountOperation;
	
	@Column(name="VL_TARIFA_APLICADA")
	@Getter @Setter
	//private BigDecimal valueRate;
	
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
