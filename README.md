# VelhaOnline
Tradicional Jogo da velha porém rodando online, gerenciado por um servidor

Este projeto tens fins didáticos mas implementa adequadamente todas as características do jogo da velha portanto, se você quiser fazer o download do projeto para bricar com seu filho, por exemplo fique à vontade!

A execução do aplicativo se dá da seguinte forma:

Executa-se uma instância do servidor e após isso executa-se uma instância do cliente em cada máquina, desde que estejam na mesma rede.

Para se conectar ao server deve-se infromar o IP da máqina onde o mesmo está sendo executado e clicar no botão conectar. 
Caso a conexão tenha ocorrido com sucesso o texto exibido na label do botão muda de “Conectar” para “Você é o O” ou “Você é o X”.
Foi implementada uma regra que define que o início do jogo será com o usuário com o símbolo X.
Após o primeiro jogo, quem começa a jogar é aquele que tiver vencido o jogo anterior.
O usuário nã pode escolher qual símbolo usar ( X ou O ). O servidor é quem define isto, sendo que
o primeiro a logar jogará com o simbolo “O” e o segundo usuário jogará com o símbolo “X”.
O primeiro usuário deverá aguardar até que o usuário “X” se conecte e inicie o jogo.

*Nesta versão o server somente suporta um jogo por vês ( dois jogadores ).

Sinta-se à vontade para alterar o projeto para uma versão customizada, caso deseje fazê-lo.

Sugestões e críticas são muito bem vindas, pois como eu já disse, este foi um trabalho para fins acadêmicos.
