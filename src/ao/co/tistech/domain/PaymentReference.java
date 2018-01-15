package ao.co.tistech.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

public class PaymentReference {
	
	@SequenceGenerator(name="seqPaymentReference", sequenceName="SEQ_ID_REF_PAG")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqPaymentReference")
	@Column(name="ID_REF_PAG")
	@Getter @Setter
	private Long idPaymentReference;
	
	@Column(name="ID_MOEDA")
	@Getter @Setter
	private Long idCoin;
	
	@Column(name="ID_SITUACAO")
	@Getter @Setter
	private Long idSituation;
	
	@Column(name="NU_REF_PAG")
	@Getter @Setter
	private String paymentReference;
	
	@Column(name="NU_NIF")
	@Getter @Setter
	private String nif;
	
	@Column(name="NM_CONTRIBUINTE")
	@Getter @Setter
	private String taxpayerName;
	
	@Column(name="DT_LIMITE_PAGAMENTO")
	@Getter @Setter
	private LocalDateTime paymentDeadline;
	
	@Column(name="NU_IMPOSTO")
	@Getter @Setter
	private String revenueCode;
	
	@Column(name="DS_IMPOSTO")
	@Getter @Setter
	private String revenueDescription;
	
	@Column(name="VL_VALOR")
	@Getter @Setter
	private BigDecimal value;
	
	@Column(name="VL_MINIMO_PAGAMENTO")
	@Getter @Setter
	private BigDecimal valueMax;
	
	@Column(name="VL_MAXIMO_PAGAMENTO")
	@Getter @Setter
	private BigDecimal valueMin;
	
	@Column(name="FL_PAGAMENTO_EMIS")
	@Getter @Setter
	private String flagPaymentEmis;
	
	@Column(name="DT_PAGAMENTO")
	@Getter @Setter
	private LocalDateTime datePayment;
	
	@Column(name="NU_BANCO")
	@Getter @Setter
	private Integer codeBank;
	
	@Column(name="NU_BALCAO")
	@Getter @Setter
	private Integer codeBalcony;
	
	@Column(name="NU_AUTENTICACAO")
	@Getter @Setter
	private String codeAutentication;
	
	@Column(name="DT_CONCILIACAO")
	@Getter @Setter
	private LocalDateTime conciliationDate;
	
	@Column(name="DT_INTEGRACAO")
	@Getter @Setter
	private LocalDateTime integrationDate;
	
	@Column(name="FL_SITUACAO_CONCILIADO_BO")
	@Getter @Setter
	private String flagSituationConciliationPsrm;
	
	@Column(name="DT_SITUACAO_CONCILIADO_BO")
	@Getter @Setter
	private LocalDateTime dateSituationConciliationPsrm;
	
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
