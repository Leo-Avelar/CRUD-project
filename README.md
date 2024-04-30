# CRUD-project

## Introdução
Para a realização deste trabalho foi necessário buscar um banco de dados no site kaggle.com.
O arquivo escolhido foi sobre jogos e foi modificado, excluindo algumas colunas e limitando a quantidade inicial de jogos para 2000.

## Funcionamento:
Durante a execução do código o arquivo original (.csv) é transformado em um arquivo (.db) que será usado para todas as outras funcionalidades.

No início do arquivo (.db) foi incluído a quantidade de jogos naquele momento (este valor é atualizado toda vez que um novo jogo é adicionado).


## Estrutura do arquivo:
Cada jogo é composto de:

* Lápide (char) - Serve para sinalizar se o jogo é válido ou não, assume " " para os jogos válidos e “*” para jogos excluídos.

* Tamanho (int) - É o tamanho do registro daquele jogo.

* ID (int) - É um identificador único do jogo, o primeiro jogo recebe o id 1, o segundo recebe id 2 e assim por diante, assim os IDs não se repetem.

* Nome (String) - Armazena o nome do jogo.

* Data (Date/long) - Contém a data de publicação do jogo (No arquivo este dado fica salvo como long e é apenas transformado em Date para algumas operações).

* Tags (String) - as tags contém os tipos daquele jogo, por exemplo: Multiplayer, Action, Adventure, RPG, Casual, etc.

* Tempo Médio (int) - contém a quantidade média de horas jogadas.

* Preço (float) - contém o preço do jogo.


## Funcionalidades:

* CRUD - Criar, Ler, Atualizar ou Deletar um jogo.

* Compressão/Descompressão (LZW) - Utiliza a técnica de compressão LZW para gerar um arquivo menor que o arquivo(.db) sem que ocorra perda de informações. 

* Overwrite - O usuário tem duas opções:

  - Sobrescrever os dados do arquivo(.db) com os arquivos do arquivo original (.csv).

  - Sobrescrever com os dados de um arquivo que foi comprimido anteriormente.

* Pattern Matching - Busca o padrão desejado pelo usuário e retorna os ids dos jogos correspondentes ao padrão buscado (se existirem) além do tempo gasto em milésimos de segundos (ms).

* Criptografia (Columnar Transposition Cipher) - A criptografia por colunas usa uma key pré determinada para alterar a ordem do que está sendo criptografado 

* Ordenação (usando Mergesort) - Ao atualizar as informações de algum jogo, se os novos dados forem menores do que os do jogo anterior, ele será sobrescrito na mesma posição do arquivo, deixando lixo(resto das informações do jogo anterior que não serão lidas) para completar, porém, se as informações desse novo jogo forem maiores, para que não ocorra perda de dados, por sobrescrever os jogos posteriores acidentalmente, é necessário marcar o jogo antigo como excluído e adicionar o novo jogo (com o mesmo id do antigo) ao final do arquivo. A ordenação serve portanto para reconstruir o arquivo(.db) colocando os jogos em suas posições corretas, seguindo o número do id.


## Como salvar e carregar algum arquivo modificado:

Cada vez que o código é executado o arquivo.db é sobrescrito com os dados originais (do arquivo .csv).
Para poder salvar as alterações feitas é necessário fazer a compactação do arquivo .db (desta forma salvando uma versão alterada) que pode ser carregada através do Overwrite.
