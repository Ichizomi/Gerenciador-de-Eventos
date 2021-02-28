# Gerenciador-de-Eventos

Bibliotecas Utilizadas:
- Connector/J (MySQL Community Download) <> Licensa: GNU General Public License Version 2.0, June 1991 <> (Motivo: Persistência dos Dados)

Programas Utilizados no Desenvolvimentos:
- IDE: EclipseIDE
- Sistema Gerenciador de Banco de Dados: MySQL Workbench

Instruções de Compiliação e Execução:
- Certifique-se que você possui o MySQL instalado no computador (Link Oficial: https://dev.mysql.com/downloads/installer/). Aproveite a oportunidade para instalar uma IDE como o ecplise e um SGBD Mysql como o MySQL Workbench, eles não são necessários mas falicitam muito o uso de usuários não tão experientes.
- Realize o download desse repositório do github.
- Instâncie o banco de dados MySQL localmente no seu computador. É recomendado usar uma ferramenta que auxilia no processo como o MySQL Workbench, mas também é possivel por linha de comando. O arquivo CREATE-DATABASE.sql (na pasta src/gerenciador/model) deve ser utilizado para gerar o banco corretamente.
- Configure o acesso ao banco. Abra o arquivo ConnectDB.java (na pasta src/gerenciador/controller) e modifique as variáveis username e password de acordo com as configurações de acesso do seu banco local (escolhidas na hora da instalação do MySQL).
- Realize o download da biblioteca de persistência de dados Connector/J (Link oficial: http://dev.mysql.com/downloads/connector/j/)
- Adicione a biblioteca Connector/J (mysql-connector-java-8.0.23.jar) no class path de seu projeto. (exemplo pelo Eclipse: https://prnt.sc/10934d6)
- Para executar o programa, execute a função main do arquivo EventManager.java (pasta src/gerenciador/controller).

Instruções de Utilização:
- Toda a interface do sistema é acessada por linha de comando. 
- Para navegar siga as instruções mais recentes, em geral toda a navegação é feita através da escolha entre várias opções.
- Funcionalidades do sistema:
- 1) Consultar/Listar Dados: Permite listar e ver mais informações individuais sobre salas de evento, salas de café e pessoas.
- 2) Cadastrar/Alterar/Remover Dados: Permite cadastrar, editar e remover pessoas e salas.
- 3) Organizar Evento: Organiza o evento automaticamente, alocando as pessoas cadastradas em salas disponíveis de acordo com as regras de negócio especificas.
- 4) Ver Cronograma do Evento: Permitir re-vizualizar o cronograma de um evento já organizado.
- 5) Limpar Banco de Dados: Limpa todos os campos de todas as tabelas do banco de dados. Porém não deleta o banco e mantém a estrutura das tabelas.
- 6) Importar Base de Dados de Exemplo: Lê um arquivo de texto (SampleData.txt na pasta src/genrenciador/controller) e popula o banco de dados com exemplos de pessoas e salas.
