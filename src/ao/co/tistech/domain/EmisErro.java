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
@Table(name="TB_EMIS_ERRO")
public class EmisErro {
	
	@SequenceGenerator(name="seqEmisErro", sequenceName="SEQ_ID_EMIS_ERRO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqEmisErro")
	@Column(name="ID_EMIS_ERRO")
	@Getter @Setter
	private long idEmisErro;
	
	@Column(name="ID_MOTIVO")
	@Getter @Setter
	private long idMotive;
	
	@Column(name="ID_COMUNICACAO")
	@Getter @Setter
	private String idComunication;
	
	@Column(name="DS_ERRO")
	@Getter @Setter
	private String descriptionErro;
	
	@Column(name="NM_FICHEIRO")
	@Getter @Setter
	private String nameFile;
	
	@Column(name="DT_PROCESSAMENTO")
	@Getter @Setter
	private LocalDateTime processingDate;
	
	@Column(name="DS_LINHA")
	@Getter @Setter
	private String descriptionLine;
	
	@Column(name="DT_CRIACAO")
	@Getter @Setter
	private LocalDateTime creationDate;
	
	@Column(name="DT_ALTERACAO")
	@Getter @Setter
	private LocalDateTime changeDate;
	
	@Column(name="DT_EXCLUSAO")
	@Getter @Setter
	private LocalDateTime exclusionDate;
	
	@Column(name="DS_MENSAGEM_RESPOSTA")
	@Getter @Setter
	private String descriptionMessageReply;

}
