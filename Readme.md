# Encurtador de Url's

## Motivação
<p>O motivo desse projeto é <del>(que eu estava desocupado e resolvi fazer)</del> que você hospede o seu próprio encurtador de url's sem depender de um terceiro vigiando os sites que você acessa.</p>
Não é armazenado log's e nem ip's. Apenas conta quantas vezes o site foi acessado, isso de forma anônima.<br>

## Pré requisitos
<ul>
<li>Possuir o Git Instalado</li>
<li>Opcional Ter o Maven instalado</li>
<li>Opcional: Ter o Docker instalado</li>
<li>Opcional: Ter o Java JDK 21 instalado</li>
<li>Obrigatório: Banco de Dados MariaDB</li>
<li>Se rodar através do Docker, precisa de no minimo 300 MB livre de RAM</li>
<li>Opcional: Ter fé.</li>
</ul>

### Explicações:
Caso queria rodar com o Docker, não é necessário ter o Maven, nem o Java JDK 21, caso não opte pelo Docker, ai sim serão necessários as duas opções que ainda pouco falamos.<br>
A máquina onde eu hospedo o Conteiner Docker é um i3320 com 12gb de ram. Então é bem provável que a sua batata rode tranquilamente o projeto.
<br>Caso não queira utilizar o MariaDB, o código é aberto e você se vira para modificar o pom.xml antes de compilar. :) <br>

## Rodando com Docker
<li>Clone o repositório</li>

` git clone https://github.com/leonamCruz/EncurtadorUrl `
<br>
<li>Entre na pasta de arquivos</li>

` cd encurtador `
<li> Com o Docker já instalado rode o seguinte comando: </li>

` docker build -t encurtador:lastest . 
` <br>
Na máquina de testes onde compilei o projeto, levou por volta de 2 minutos para compilar, tenha paciência.

<li>Crie um arquivo .env e insira as seguintes informações</li>

` touch arq.env 
`<br>

As informações necessárias são as seguintes: <br>

` 
DB_URL=jdbc:mariadb://<url do banco de dados>:<porta>/<nome-do-banco-de-dados>
DB_USER=<nome do usuário do MariaDB>
DB_PASS=<sua senha do MariaDB>
`
<br>

<li>Finalmente copie esse comando para rodar:</li>

`docker run -d -p 666:666 --env-file arq.env --name encurtador (numero do conteiner) `
<br>
Você pode alterar a porta do Host para qual desejar.

## Forma Chata de rodar o Projeto
` git clone https://github.com/leonamCruz/EncurtadorUrl `
<br>
<li>Entre na pasta de arquivos</li>

` cd encurtador `

<li>Com o Maven já instalado rode o comando: </li>

` mvn clean package -DskipTests ` <br> Isso pode demorar um pouco, vá tomar um café. <br>

<li> Agora é só rodar o comando: </li>

` java -jar EncurtadorUrl-1.jar &` 
<br> O & é necessário para rodar em segundo plano caso esteja no linux, basta digitar bg assim que iniciar a rodar, senão finja que ele não existe e remova do comando.

#Bug's Conhecidos
<li>Botão de copiar a URL funciona somente 1 vez no Chrome - Não Afeta Firefox</li>
Na próxima att, consertarei. <br>
Descrição do Bug: A primeira vez o botão de copiar funciona majestosamente bem, e ao fechar e gerar uma nova URL encurtada, por algum motivo mirabolante não copia a URL nova modificada.
