//package ao.co.tistech.jobs;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Properties;
//import java.util.Set;
//
//import org.apache.commons.io.input.ReversedLinesFileReader;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.HtmlEmail;
//import org.apache.commons.mail.MultiPartEmail;
//
//import ao.co.sigt.bibliotecas.BibliotecaFuncoesEmail;
//import ao.co.sigt.constantes.Constantes;
//import ao.co.sigt.dao.DAOEmisErro;
//import ao.co.sigt.dao.DAOFmovDetalhe;
//import ao.co.sigt.dao.DAOFmovHeader;
//import ao.co.sigt.dao.DAOParametrosGP;
//import ao.co.sigt.dao.DAOReferenciaPagamentoGP;
//import ao.co.sigt.dto.DTOParametrosDiretoriosFMOV;
//import ao.co.sigt.dto.DTOParametrosEmail;
//import ao.co.sigt.valueobjects.VOEmisErro;
//import ao.co.sigt.valueobjects.VOFmovDetalhe;
//import ao.co.sigt.valueobjects.VOFmovHeader;
//import ao.co.sigt.valueobjects.VOParametroGP;
//
//public class JobProcessarArquivoFMOV {
//
//	public static void main(String args[]) throws IOException {
//		FileReader fileReader = null;
//		BufferedReader reader = null;
//		try {
//			Path pathReceived = Paths.get(System.getProperty(arg0))
//			
//			
//			DTOParametrosDiretoriosFMOV dtoParametrosDiretoriosFMOV = consultarParametrosDiretoriosFMOV(
//					Constantes.CD_PARAMETRO_DIRETORIOS_FMOV);
//			
//			File baseFolder = new File(dtoParametrosDiretoriosFMOV.getDiretorioFmov());
//			
//			File[] files = baseFolder.listFiles();
//
//			if (files == null || files.length == 0) {
//				System.out.println(">>Nenhum ficheiro encontrado para processar. ");
//				System.out.println(">>Processo finalizado.");
//			} else {
//				System.out.println(">>Foram encontrados " + files.length + " arquivos para processar. ");
//				Arrays.sort(files);
//				List<VOFmovHeader> sequenciaisFicheiros = new ArrayList<VOFmovHeader>();
//				List<VOEmisErro> ficheirosInvalidos = new ArrayList<VOEmisErro>();
//				HashMap<VOFmovHeader, List<VOFmovDetalhe>> ficheirosValidos = new HashMap<VOFmovHeader, List<VOFmovDetalhe>>();
//				for (int i = 0; i < files.length && ficheirosInvalidos.size() == 0; i++) {
//					File file = files[i];
//
//					System.out.println(">>Iniciando validação do ficheiro " + file.getName());
//
//					fileReader = new FileReader(file);
//					reader = new BufferedReader(fileReader);
//					
//					String data = null;
//					String dataProcessamentoHeader = null;
//					int linhasFicheiro = 1; 
//					int linhasDetail = 0; 
//					BigDecimal somatorioMontanteOperacao = new BigDecimal("0");
//					BigDecimal somatorioValorTarifaAplicadaOperacao = new BigDecimal("0");
//					List<VOFmovDetalhe> listaVOFmovDetalhe = new ArrayList<>();
//					VOFmovHeader voFmovHeader = new VOFmovHeader();
//					VOEmisErro voEmisErro = new VOEmisErro();
//					
//					while ((data = reader.readLine()) != null && ficheirosInvalidos.size() == 0) {
//						VOFmovDetalhe voFmovDetalhe = new VOFmovDetalhe();
//						adicionarFileVo(fileReader, file, voFmovHeader, voEmisErro, dtoParametrosDiretoriosFMOV);
//						
//						if(data.length() == 0)
//							break;
//						
//						String tipoRegistro = data.substring(0, 1);
//						
//						if(traillerValido(file))
//						{
//							voEmisErro.setDsErro("[TRAILLER] - Linha não está iniciando com o número 9.");
//							voEmisErro.setIdMotivo(new Long(1));
//							ficheirosInvalidos.add(voEmisErro);
//							System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//							break;
//						}
//						
//						if(linhasFicheiro == 1 && !tipoRegistro.equals("0")){
//							voEmisErro.setDsErro("[HEADER] - Primeira linha não está iniciando com o número 0.");
//							voEmisErro.setIdMotivo(new Long(1));
//							voEmisErro.setDsLinha(data);
//							ficheirosInvalidos.add(voEmisErro);
//							System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//							break;
//						}
//						
//						switch (tipoRegistro) {
//						case Constantes.TP_REG_HEADER_FMOV:
//							
//							String nomeFicheiro = data.substring(1, 5);
//							
//							if(!nomeFicheiro.equals(Constantes.PREFIXO_FICHEIRO_FMOV)){
//								voEmisErro.setDsErro("[HEADER] - Nome do ficheiro inválido.");
//								voEmisErro.setIdMotivo(new Long(2));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setNmFicheiroHeader(nomeFicheiro);
//							}
//							
//							String instituicaoOrigem = data.substring(5, 13);
//							
//							if(!instituicaoOrigem.equals(Constantes.ID_INSTITUICAO_ORIGEM)) {
//								voEmisErro.setDsErro("[HEADER] - Instituição de origem inválida.");
//								voEmisErro.setIdMotivo(new Long(3));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setIdInstituicaoOrigemHeader(new Long(instituicaoOrigem));
//							}
//							
//							String instituicaDestino = data.substring(13, 21);
//							
//							if(!instituicaDestino.equals(Constantes.ID_INSTITUICAO_DESTINO)) {
//								voEmisErro.setDsErro("[HEADER] - Instituição de destino inválida.");
//								voEmisErro.setIdMotivo(new Long(4));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setIdInstituicaoDestinoHeader(new Long(instituicaDestino));
//							}
//							
//							String dataProcessamento = data.substring(21, 32);
//							String idUltimoFicheiro = data.substring(32, 43);
//							
//							if(!validarData(dataProcessamento))
//							{
//								voEmisErro.setDsErro("[HEADER] - Data de processamento inválida.");
//								voEmisErro.setIdMotivo(new Long(5));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setDtProcessamentoHeader(new Long(dataProcessamento));
//								dataProcessamentoHeader = dataProcessamento.substring(0, dataProcessamento.length() -3);
//							}
//							
//							if(!validarData(idUltimoFicheiro))
//							{
//								voEmisErro.setDsErro("[HEADER] - Data de processamento do último ficheiro inválida.");
//								voEmisErro.setIdMotivo(new Long(6));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							}
//						
//							// Validação da sequência dos ficheiros antes do
//							// processamento
//							if(sequenciaisFicheiros.size() > 0) {
//								
//								VOFmovHeader ficheiroAnterior          = sequenciaisFicheiros.get(sequenciaisFicheiros.size() - 1);
//								
//								Long idUltimoFicheiroProcessado        = Long.parseLong(idUltimoFicheiro);
//								Long dataProcessamentoFicheiroAnterior = ficheiroAnterior.getDtProcessamentoHeader();
//								
//								if(!idUltimoFicheiroProcessado.equals(dataProcessamentoFicheiroAnterior)) 
//								{
//									voEmisErro.setDsErro("[HEADER] - Id de processamento do último ficheiro especificado no " + file.getName() + " é diferente do sequêncial de processamento do ficheiro " + ficheiroAnterior.getFileName());
//									voEmisErro.setIdMotivo(new Long(7));
//									voEmisErro.setDsLinha(data);
//									ficheirosInvalidos.add(voEmisErro);
//									System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//									break;
//								}
//								else
//								{
//									voFmovHeader.setDtProcessamentoHeader(new Long(dataProcessamento));
//									voFmovHeader.setIdUltimoFicheiroHeader(idUltimoFicheiroProcessado);
//								}
//							} 
//							else
//							{
//								Long dataUltimoFicheiroProcessado = DAOFmovHeader.getInstancia().consultarUltimoFicheiro();
//								
//								if(dataUltimoFicheiroProcessado > 0)
//								{
//									if(!idUltimoFicheiro.equals(dataUltimoFicheiroProcessado.toString()))
//									{
//										voEmisErro.setDsErro("[HEADER] - Id de processamento do último ficheiro especificado no " + file.getName() + " é diferente do sequêncial de processamento do ficheiro mais recente na base de dados");
//										voEmisErro.setIdMotivo(new Long(15));
//										voEmisErro.setDsLinha(data);
//										ficheirosInvalidos.add(voEmisErro);
//										System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//										break;
//									}
//									else
//									{
//										voFmovHeader.setIdUltimoFicheiroHeader(new Long(idUltimoFicheiro));
//									}
//								}
//								else
//								{
//									voFmovHeader.setIdUltimoFicheiroHeader(new Long(idUltimoFicheiro));
//								}
//							}
//
//							String nrEntidade = data.substring(43, 48);
//							
//							if(!nrEntidade.equals(Constantes.NR_ENTIDADE)) {
//								voEmisErro.setDsErro("[HEADER] - Número da entidade inválida.");
//								voEmisErro.setIdMotivo(new Long(8));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setNuEntidadeHeader(new Integer(nrEntidade));
//							}
//							
//							if(data.length() >= 51)
//							{
//								String codigoMoeda = data.substring(48, 51);
//								
//								if(!codigoMoeda.equals(Constantes.CODIGO_MOEDA)) {
//									voEmisErro.setDsErro("[HEADER] - código de moeda inválido.");
//									voEmisErro.setIdMotivo(new Long(9));
//									voEmisErro.setDsLinha(data);
//									ficheirosInvalidos.add(voEmisErro);
//									System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//									break;
//								} else {
//									voFmovHeader.setCdMoedaHeader(codigoMoeda);
//								}
//							}
//							else
//							{
//								voEmisErro.setDsErro("[HEADER] - Não foi possível identificar o código da moeda, linha do header com caracteres menor que o esperado.");
//								voEmisErro.setIdMotivo(new Long(18));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							}
//							voFmovHeader.setNuSituacao(Constantes.NU_SITUACAO_PROCESSAMENTO);
//							
//							if(data.length()>=140)
//								voFmovHeader.setDsEspacoHeader(data.substring(51, 140));
//							
//							voFmovHeader.setDtCriacao(getDataAtual());
//							
//							sequenciaisFicheiros.add(voFmovHeader);
//							
//							break;
//
//						case Constantes.TP_REG_DETAIL_FMOV:
//							
//							String codigoProcessamento    = data.substring(1, 3);
//							String montanteOperacao       = data.substring(64, 77);
//							String tarifaAplicadaOperacao = data.substring(77, 89);
//							
//							if(!codigoProcessamento.equals(Constantes.CODIGO_PROCESSAMENTO_DETAIL)) {
//								voEmisErro.setDsErro("[DETAIL] - código de processamento inválido.");
//								voEmisErro.setIdMotivo(new Long(10));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovDetalhe.setCdProcessamento(new Long(codigoProcessamento));
//							}
//							
//							voFmovDetalhe.setNuTipoRegistro(new Integer(tipoRegistro));
//							
//							String dataTransacaoCliente        = data.substring(16, 30);
//							
//							voFmovDetalhe.setDtTransacaoCliente(stringToDate(dataTransacaoCliente));
//							voFmovDetalhe.setVlMontanteOperacao(formataBigDecimal(data.substring(64, 77)));
//							voFmovDetalhe.setVlTarifaAplicada(formataBigDecimal(data.substring(77, 89)));
//							voFmovDetalhe.setNuDocumentoCobranca(new Long (data.substring(89, 104)));
//							voFmovDetalhe.setNuOperacaoPagamento(new Integer(data.substring(113, 114)));
//							voFmovDetalhe.setNuTransaction(new Long(data.substring(8, 16)));
//							voFmovDetalhe.setNuClearingPeriodo(new Integer(data.substring(4, 8)));
//							voFmovDetalhe.setCdProduto(new Integer(data.substring(104, 106)));
//							voFmovDetalhe.setCdParametro(new Integer(data.substring(106, 108)));
//							voFmovDetalhe.setCdRespota(data.substring(127, 128));
//							voFmovDetalhe.setNuTransacaoLocal(data.substring(44, 49));
//							voFmovDetalhe.setDtCriacao(getDataAtual());
//							
//							//Somatorio dos montantes das operações 
//							somatorioMontanteOperacao = somatorioMontanteOperacao.add(new BigDecimal(montanteOperacao));
//							
//							//Somatorio valores das tarifas aplicadas nas operações 
//							somatorioValorTarifaAplicadaOperacao = somatorioValorTarifaAplicadaOperacao.add(new BigDecimal(tarifaAplicadaOperacao));
//							
//							linhasDetail++;
//							listaVOFmovDetalhe.add(voFmovDetalhe);
//							
//							break;
//						case Constantes.TP_REG_TRAILLER_FMOV:
//							
//							String numeroRegistrosDetalhe = data.substring(1, 9);
//							Integer intNumeroRegistrosDetalhe = Integer.valueOf(numeroRegistrosDetalhe);
//							
//							//Valida se o número de registros de datails é igual ao informado no trailler
//							if(intNumeroRegistrosDetalhe != linhasDetail)
//							{
//								voEmisErro.setDsErro("[TRAILLER] - Quantidade de registro de datails é diferente do informado no trailler.");
//								voEmisErro.setIdMotivo(new Long(11));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setNuRegistroDetalheTrailer(new Integer(numeroRegistrosDetalhe));
//							}
//							
//							String montanteTotalTransacoes = data.substring(9, 26);
//							montanteTotalTransacoes = Long.valueOf(montanteTotalTransacoes).toString();
//									
//							//Validar o montante total (monetário) do trailler com o somatório do montante (monetário) dos registos de datail
//							if(!somatorioMontanteOperacao.toString().equals(montanteTotalTransacoes))
//							{
//								voEmisErro.setDsErro("[TRAILLER] - Valor do montante total do trailler diferente do somatório do montante dos registos de datails.");
//								voEmisErro.setIdMotivo(new Long(12));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setVlTotalTransacaoTrailer(formataBigDecimal(somatorioMontanteOperacao.toString()));
//							}
//							
//							String totalTarifacao = data.substring(26, 38);
//							totalTarifacao = Long.valueOf(totalTarifacao).toString();
//							
//							//Validar o valor do campo tarifa com o somatório das tarifas aplicadas ao detalhe de cada linha
//							if(!somatorioValorTarifaAplicadaOperacao.toString().equals(totalTarifacao))
//							{
//								voEmisErro.setDsErro("[TRAILLER] - Valor do campo tarifa diferente do somatório das tarifas aplicadas ao datail de cada linha");
//								voEmisErro.setIdMotivo(new Long(13));
//								voEmisErro.setDsLinha(data);
//								ficheirosInvalidos.add(voEmisErro);
//								System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//								break;
//							} else {
//								voFmovHeader.setVlTotalTarifacaoTrailer(formataBigDecimal(somatorioValorTarifaAplicadaOperacao.toString()));
//							}
//							if(data.length()>=140)
//								voFmovHeader.setDsEspacoTrailer(data.substring(38, 140));
//							break;
//						default:
//							voEmisErro.setDsErro("[ERRO] - Ficheiro com tipo de registro inválido");
//							voEmisErro.setIdMotivo(new Long(14));
//							voEmisErro.setDsLinha(data);
//							ficheirosInvalidos.add(voEmisErro);
//							System.out.println(">>Falha ao validar o ficheiro " + file.getName());
//							break;
//
//						}
//						
//						linhasFicheiro++;	
//						ficheirosValidos.put(voFmovHeader, listaVOFmovDetalhe);
//					}
//				}
//				if(ficheirosInvalidos!= null && ficheirosInvalidos.size() > 0)
//				{
//					inserirFicheirosInvalidos(ficheirosInvalidos);
//				}
//				else
//				{
//					List<VOFmovDetalhe> pagamentos = validarDadosPagamento(ficheirosValidos, dtoParametrosDiretoriosFMOV.getDiretorioErro());
//					
//					if(pagamentos != null && pagamentos.size() > 0)
//					{
//						if(atualizarSituacaoReferenciaDePagamento(pagamentos));
//							inserirFicheirosValidos(ficheirosValidos);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			tratarException(e);
//			System.out.println(">>Processamento interrompido devido a um erro inesperado.");		
//		}
//	}
//
//	private static void adicionarFileVo(FileReader fileReader, File file, VOFmovHeader voFmovHeader,
//			VOEmisErro voEmisErro, DTOParametrosDiretoriosFMOV dtoParametrosDiretoriosFMOV) {
//		voEmisErro.setNmFicheiro(file.getName());
//		voEmisErro.setFile(file);
//		voEmisErro.setFileReader(fileReader);
//		voEmisErro.setDiretorioErro(dtoParametrosDiretoriosFMOV.getDiretorioErro());
//		voFmovHeader.setFileName(file.getName());
//		voFmovHeader.setFile(file);
//		voFmovHeader.setFileReader(fileReader);
//		voFmovHeader.setDiretorioProcessados(dtoParametrosDiretoriosFMOV.getDiretorioProcessados());
//	}
//	
//	private static boolean atualizarSituacaoReferenciaDePagamento(List<VOFmovDetalhe> listaPagamentos)
//			throws Exception {
//		boolean situacaoAtualizada = true;
//
//		Connection conn = DAOFmovHeader.getInstancia().getConexaoGP();
//		conn.setAutoCommit(false);
//
//		try {
//			for (VOFmovDetalhe detail : listaPagamentos) {
//				if (detail.getNuDocumentoCobranca() != null) {
//					DAOReferenciaPagamentoGP.getInstancia().atualizarSituacaoConciliada(detail.getNuDocumentoCobranca(),
//							conn);
//				}
//			}
//		} catch (Exception e) {
//			situacaoAtualizada = false;
//			conn.rollback();
//			throw e;
//		}
//
//		conn.commit();
//		conn.setAutoCommit(true);
//
//		return situacaoAtualizada;
//	}
//	
//	private static List<VOFmovDetalhe> validarDadosPagamento(HashMap<VOFmovHeader, List<VOFmovDetalhe>> ficheirosValidos, String diretorioErro)
//			throws Exception {
//		Boolean pagamentoInvalido = false;
//		List<VOFmovDetalhe> pagamentosValidos = new ArrayList<VOFmovDetalhe>();
//
//		if (ficheirosValidos != null && ficheirosValidos.size() > 0) {
//			
//			for (VOFmovHeader voFmovHeader : ficheirosValidos.keySet()) {
//				if (!pagamentoInvalido) {
//					List<VOFmovDetalhe> listaDatails = ficheirosValidos.get(voFmovHeader);
//
//					System.out.println(">>Iniciando validação dos pagamentos do ficheiro " + voFmovHeader.getFileName());
//					if(listaDatails.size() > 0)
//					{
//						for (int j = 0; j < listaDatails.size() && !pagamentoInvalido; j++) 
//						{
//							VOFmovDetalhe datail = listaDatails.get(j);
//	
//							// validar referência, data e valor do pagamento
//							int qtdRegistrosEncontrado = DAOReferenciaPagamentoGP.getInstancia()
//									.consultaReferenciaDataValorPagamento(
//											new java.sql.Date(datail.getDtTransacaoCliente().getTime()),
//											datail.getNuDocumentoCobranca(),
//											datail.getVlMontanteOperacao());
//	
//							if (qtdRegistrosEncontrado == 0) {
//								VOEmisErro ficheiroInvalido = new VOEmisErro();
//								ficheiroInvalido.setDsErro(
//										"[DETAIL] - A Referência de pagamento: " + datail.getNuDocumentoCobranca()
//												+ ", valor: " + datail.getVlMontanteOperacao() + " e data de pagamento: "
//												+ datail.getDtTransacaoCliente() + " não foram encontrados.");
//								ficheiroInvalido.setIdMotivo(new Long(16));
//								ficheiroInvalido.setIdComunicao(Constantes.ID_COMUNICACAO_MFT);
//								ficheiroInvalido.setDtProcessamento(getDataAtual());
//								ficheiroInvalido.setDtCriacao(getDataAtual());
//								ficheiroInvalido.setFile(voFmovHeader.getFile());
//								ficheiroInvalido.setFileReader(voFmovHeader.getFileReader());
//								ficheiroInvalido.setDiretorioErro(diretorioErro);
//								pagamentoInvalido = true;
//								//Limpa lista 
//								pagamentosValidos = null;
//								System.out
//										.println("[DETAIL] - A Referência de pagamento: " + datail.getNuDocumentoCobranca()
//												+ ", valor: " + datail.getVlMontanteOperacao() + " e data de pagamento: "
//												+ datail.getDtTransacaoCliente() + " não foram encontrados.");
//								inserirFicheirosInvalidos(ficheiroInvalido);
//								break;
//							} 
//							else 
//							{
//								pagamentosValidos.add(datail);
//							}
//						}
//					}
//					else
//					{
//						pagamentosValidos.add(new VOFmovDetalhe());
//					}
//				} else {
//					break;
//				}
//			}
//		}
//		return pagamentosValidos;
//	}
//	
//	
//	private static void tratarException(Exception e) {
//		VOEmisErro ficheiroInvalido = new VOEmisErro();
//		ficheiroInvalido.setDsErro(e.getMessage());
//		ficheiroInvalido.setIdMotivo(new Long(0)); // Erro Desconhecido
//		ficheiroInvalido.setIdComunicao(Constantes.ID_COMUNICACAO_MFT);
//		ficheiroInvalido.setDtProcessamento(getDataAtual());
//		ficheiroInvalido.setDtCriacao(getDataAtual());
//		
//		try {
//			inserirFicheirosInvalidos(ficheiroInvalido);
//		} catch (Exception e1) {
//			System.out.println(">>Erro ao tentar salvar o ficheiro");
//		}
//	}
//	
//	
//	private static void inserirFicheirosInvalidos(List<VOEmisErro> ficheirosInvalidos) throws Exception {
//		for (VOEmisErro voEmisErro : ficheirosInvalidos) {
//			voEmisErro.setIdComunicao(Constantes.ID_COMUNICACAO_MFT);
//			voEmisErro.setDtProcessamento(getDataAtual());
//			voEmisErro.setDtCriacao(getDataAtual());
//
//			DAOEmisErro.getInstancia().inserirEmisErro(voEmisErro);
//
//			enviarEmailErro(voEmisErro);
//
//			if (voEmisErro.getFile() != null && voEmisErro.getFileReader() != null
//					&& voEmisErro.getDiretorioErro() != null)
//				moverFicheiroErro(voEmisErro);
//
//			System.out.println(">>Processamento interrompido.");
//		}
//
//	}
//
//	
//	private static void enviarEmailErro(VOEmisErro voEmisErro) throws Exception, EmailException {
//
//		DTOParametrosEmail dtoParametrosEmail = BibliotecaFuncoesEmail
//				.consultarParametrosEmailJob(Constantes.CD_PARAMETRO_EMAIL_ERRO_JOB_PROCESSAR_FMOV);
//
//		Properties props = new Properties();
//
//		props = System.getProperties();
//		props.put("mail.smtp.host", dtoParametrosEmail.getHostName());
//		props.put("mail.smtp.port", dtoParametrosEmail.getPortaEnvio());
//		props.put("mail.smtp.auth", "false");
//		// props.put("mail.debug", "true");
//		props.put("mail.smtp.ssl.enable", "false");
//
//		MultiPartEmail email = new HtmlEmail();
//		email.setHostName(dtoParametrosEmail.getHostName());
//		email.addTo(dtoParametrosEmail.getDestinatario());
//		if (dtoParametrosEmail.getDestinatarioCopia() != null
//				&& !dtoParametrosEmail.getDestinatarioCopia().equals("")) {
//			email.addCc(dtoParametrosEmail.getDestinatarioCopia());
//		}
//		email.setFrom(dtoParametrosEmail.getRemetente(), dtoParametrosEmail.getNomeRemetente());
//		email.setSubject(dtoParametrosEmail.getAssuntoEmail());
//		email.setSmtpPort(dtoParametrosEmail.getPortaEnvio());
//		email.setMsg(dtoParametrosEmail.getMensagemEmail());
//		if (voEmisErro.getFile() != null)
//			email.attach(voEmisErro.getFile());
//		email.send();
//	}
//	
//	
//	private static void moverFicheiroErro(VOEmisErro voEmisErro) throws IOException {
//		File diretorio = new File(voEmisErro.getDiretorioErro());
//
//		if (!diretorio.exists())
//			diretorio.mkdir();
//
//		File file = voEmisErro.getFile();
//		FileReader fileReader = voEmisErro.getFileReader();
//		fileReader.close();
//		file.renameTo(new File(voEmisErro.getDiretorioErro() + file.getName()));
//	}
//	
//	
//	private static void inserirFicheirosInvalidos(VOEmisErro ficheiroInvalido) throws Exception {
//		ficheiroInvalido.setIdComunicao(Constantes.ID_COMUNICACAO_MFT);
//		ficheiroInvalido.setDtProcessamento(getDataAtual());
//		ficheiroInvalido.setDtCriacao(getDataAtual());
//
//		DAOEmisErro.getInstancia().inserirEmisErro(ficheiroInvalido);
//
//		enviarEmailErro(ficheiroInvalido);
//
//		if (ficheiroInvalido.getFile() != null && ficheiroInvalido.getFileReader() != null
//				&& ficheiroInvalido.getDiretorioErro() != null) {
//			moverFicheiroErro(ficheiroInvalido);
//		}
//		System.out.println(">>Processamento interrompido.");
//	}
//	
//	
//	private static Boolean inserirFicheirosValidos(HashMap<VOFmovHeader, List<VOFmovDetalhe>> ficheirosValidos)
//			throws Exception {
//		Boolean ficheirosInseridos = false;
//		if (ficheirosValidos != null && ficheirosValidos.size() > 0) {
//			Set<VOFmovHeader> ficheiros = ficheirosValidos.keySet();
//			Connection conn = DAOFmovHeader.getInstancia().getConexaoEMIS();
//			conn.setAutoCommit(false);
//
//			try {
//				for (VOFmovHeader ficheiro : ficheiros) {
//
//					Long idFmovHeader = DAOFmovHeader.getInstancia().incluir(ficheiro, conn);
//
//					List<VOFmovDetalhe> listaDatails = ficheirosValidos.get(ficheiro);
//
//					for (VOFmovDetalhe datail : listaDatails) {
//						if (datail.getNuDocumentoCobranca() != null) {
//							datail.setIdFmovHea(idFmovHeader);
//
//							DAOFmovDetalhe.getInstancia().incluir(datail, conn);
//						}
//					}
//				}
//			} catch (Exception e) {
//				conn.rollback();
//				throw e;
//			}
//
//			conn.commit();
//			conn.setAutoCommit(true);
//			ficheirosInseridos = true;
//
//			for (VOFmovHeader ficheiro : ficheiros) {
//				moverFicheirosProcessados(ficheiro);
//			}
//
//			System.out.println(">>Processo concluído com sucesso.");
//
//		}
//		return ficheirosInseridos;
//	}
//	
//	
//	private static void moverFicheirosProcessados(VOFmovHeader ficheiro) throws IOException {
//		File diretorio = new File(ficheiro.getDiretorioProcessados());
//		
//		if(!diretorio.exists())
//			diretorio.mkdir();
//		
//		File file = ficheiro.getFile();
//		FileReader fileReader = ficheiro.getFileReader();
//		fileReader.close();
//		file.renameTo(new File(ficheiro.getDiretorioProcessados() + file.getName()));
//	}
//	
//	
//	private static boolean validarData(String data) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		sdf.setLenient(false);
//		try {
//			sdf.parse(data.substring(0, 8));
//			return true;
//		} catch (ParseException ex) {
//			return false;
//		}
//	}
//	
//	
//	private static Date stringToDate(String data) {
//		Date date = null;
//		try {
//			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(data);
//		} catch (ParseException e) {
//			//
//			e.printStackTrace();
//		}
//
//		return date;
//	}
//	
//
//	public static Date getDataAtual() {
//		Calendar calendar = new GregorianCalendar();
//		Date date = new Date();
//		calendar.setTime(date);
//		return calendar.getTime();
//	}
//	
//	
//	private static BigDecimal formataBigDecimal(String nmMontante) {
//		BigDecimal nmMontanteDecimal = new BigDecimal("0.00");
//		if (!nmMontante.equals("0")) {
//			String decimais = "";
//			String valor = "";
//
//			valor = nmMontante.substring(0, nmMontante.length() - 2);
//			decimais = nmMontante.substring(nmMontante.length() - 2, nmMontante.length());
//			nmMontante = valor + "." + decimais;
//
//			nmMontanteDecimal = new BigDecimal(nmMontante);
//		}
//		return nmMontanteDecimal;
//	}
//	
//
//	public static boolean traillerValido(File file) throws IOException {
//		ReversedLinesFileReader object = null;
//		Boolean traillerInvalido = false;
//		try {
//			object = new ReversedLinesFileReader(file);
//			String ultimaLinha = object.readLine();
//			ultimaLinha = ultimaLinha.substring(0, 1);
//
//			if (!ultimaLinha.equals(Constantes.TP_REG_TRAILLER_FMOV))
//				traillerInvalido = true;
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			object.close();
//		}
//		return traillerInvalido;
//	}
//	
//	
//	public static DTOParametrosDiretoriosFMOV consultarParametrosDiretoriosFMOV(Long codigoParametro) throws Exception {
//
//		DTOParametrosDiretoriosFMOV dtoParametrosDiretoriosFMOV = null;
//
//		try {
//			String parametro = DAOParametrosGP.getInstancia()
//					.consultarPorChavePrimaria(new VOParametroGP(codigoParametro)).getDsParametro();
//
//			String[] parametros;
//
//			if (parametro != null) {
//				parametros = parametro.split("#");
//
//				dtoParametrosDiretoriosFMOV = new DTOParametrosDiretoriosFMOV(parametros[0], parametros[1],
//						parametros[2]);
//
//			}
//		} catch (Exception e) {
//			System.out.println("Erro ao tentar consultar parametro.");
//			throw e;
//		}
//		return dtoParametrosDiretoriosFMOV;
//	}
//}
