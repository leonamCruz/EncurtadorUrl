# Encurtador de Url's

## Motivação
<p>O motivo desse projeto é <del>(que eu estava desocupado e resolvi fazer)</del> que você hospede o seu próprio encurtador de url's sem depender de um terceiro vigiando os sites que você acessa.</p>
Não é armazenado log's e nem ip's. Apenas conta quantas vezes o site foi acessado, isso de forma anônima.<br>

<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEg_-q9pVNKtBOSzHCxYmd5PEhC35kSRnSBiBWG-73VXUc7MXJfeAKdDLO9aegUBpF6Tc7emA3NyNYXoQux9Ymqv_noquZrQAbKWEx2Bat8i800-v3jRlgMeXgo8j4mHs8Deih1F2LS2sW3JJ0oAB5CLFu2hSL3yDd2DZLR5bbpFh5e6zBmD_u5MESDLNmJ4/s400-rw/encurtador.png">

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

` docker build -t encurtador:latest . 
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

`docker run -d -p 666:666 --env-file arq.env encurtador:latest`
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

# Bug's Conhecidos
<li> O botão copiar não funciona</li>
Solução: Ativar o Http's através de um Proxy reverso. Caso isso não solucione, é o seu navegador que bloqueia essa função por segurança.
