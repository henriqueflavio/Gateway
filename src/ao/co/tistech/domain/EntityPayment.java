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
@Table(name="TB_ENTIDADE")
public class EntityPayment {
	
	@SequenceGenerator(name="seqEntityPayment", sequenceName="SEQ_ID_ENTIDADE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqEntityPayment")
	@Column(name="ID_ENTIDADE")
	@Getter @Setter
	private long idEntityPayment;
	
	@Column(name="NM_ENTIDADE")
	@Getter @Setter
	private long nameEntity;
	
	@Column(name="NU_ENTIDADE_EMIS")
	@Getter @Setter
	private String codeEntityEmis;
	
	@Column(name="DS_EMAIL")
	@Getter @Setter
	private String email;
	
	@Column(name="NU_DLI_INICIAL")
	@Getter @Setter
	private Long numberDliInitial;
	
	@Column(name="NU_DLI_INICIAL")
	@Getter @Setter
	private Long numberDliFinal;
	
	@Column(name="NU_CONTA_CORRENTE")
	@Getter @Setter
	private String currentAccount;
	
	@Column(name="NU_IBAN")
	@Getter @Setter
	private String iban;
	
	@Column(name="NU_DAR_INICIAL")
	@Getter @Setter
	private Long numberDarInitial;
	
	@Column(name="NU_DAR_FINAL")
	@Getter @Setter
	private Long numberDarFinal;
	
	@Column(name="CD_REPARTICAO_FISCAL")
	@Getter @Setter
	private Long codeTaxOffice;
	
	@Column(name="NM_REPARTICAO_FISCAL")
	@Getter @Setter
	private String descriptionTaxOffice;
	
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
