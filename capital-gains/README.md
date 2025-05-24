# Desafio: Cálculo de Imposto sobre Ganho de Capital em Operações de Compra e Venda de Ações

## Descrição

Este projeto implementa a lógica para cálculo do imposto sobre ganho de capital em operações de compra e venda de ações, considerando:

- Preço médio ponderado das ações para cálculo do custo médio.
- Lucro na venda descontando prejuízos acumulados para compensação.
- Isenção para vendas de até R$ 20.000,00 por operação.
- Alíquota de 20% sobre o lucro tributável.
- Compras não geram imposto.
- Venda que exceda a quantidade em carteira não gera prejuízo negativo.

## Estrutura de arquivos para teste

O arquivo de entrada para testes fica dentro da pasta `resources` no projeto, nomeado como `input.json`. 

Você pode editar esse arquivo para alterar as operações de compra e venda e assim testar diferentes cenários.

## Como executar a aplicação

Para rodar a aplicação:

1. Abra a classe `Main` que está dentro do pacote `br.com.ganhos.capitalgains.adapter`.
2. Execute o método `main` (botão play na sua IDE).
3. O resultado do cálculo dos impostos será exibido no console.

## Exemplo de input.json

```json
[
  {
    "type": "BUY",
    "quantity": 10000,
    "unitCost": 10.0
  },
  {
    "type": "SELL",
    "quantity": 5000,
    "unitCost": 16.0
  }
]
```
Observações
Alterar o input.json e executar novamente permite verificar os cálculos com diferentes operações.

O resultado é uma lista de valores de imposto correspondente a cada operação na ordem.

Projeto desenvolvido para o desafio de cálculo de imposto sobre ganhos de capital em operações de ações.