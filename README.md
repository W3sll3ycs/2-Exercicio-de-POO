## Questão 1: Getters e setters vs. atributos públicos

### Por que é boa prática usar getters e setters?

Esse conceito está ligado ao **encapsulamento**: a classe esconde seus dados internos e controla como eles são lidos e alterados. Os principais motivos são:

- **Controle de acesso:** com atributos privados, só é possível alterar o estado do objeto pelos métodos que a classe oferece. Com atributos públicos, qualquer parte do código pode mudar qualquer valor, sem nenhuma regra.
- **Validação dos dados:** o setter pode recusar valores inválidos (preço negativo, idade impossível, string vazia) antes de gravar.
- **Flexibilidade para mudar a implementação:** se a forma de armazenar um dado mudar (por exemplo, guardar o preço em centavos como `int`), os métodos `getPreco()` e `setPreco()` continuam com a mesma assinatura e o resto do sistema não precisa ser alterado.
- **Somente leitura quando necessário:** é possível criar só o getter, sem setter, para atributos que não devem mudar depois da criação (como o código de um produto).
- **Facilidade de manutenção e depuração:** como toda alteração passa pelo setter, basta colocar um ponto de parada ou um log ali para descobrir quem está mudando o valor.

### Exemplo: setter garantindo a integridade dos dados

Considere uma classe `Produto`. Se o atributo fosse público, o código abaixo seria possível:

```java
produto.preco = -50; // nada impede um preço negativo
```

Com atributo privado e setter com validação:

```java
public class Produto {
    private double preco;

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }
}
```

Agora, `produto.setPreco(-50)` é rejeitado e o objeto nunca fica em um estado inconsistente. Toda a regra de negócio fica **em um único lugar**, dentro da própria classe.

---

## Questão 2: Sistema de controle de biblioteca

### 2.1 Quais informações são relevantes para representar um livro?

As informações dependem do que o sistema precisa fazer, mas, para um controle de biblioteca, as mais relevantes são:

| Informação | Tipo sugerido | Por que é relevante |
|---|---|---|
| Código / tombo | `int` ou `String` | Identifica cada exemplar de forma única |
| ISBN | `String` | Identifica a edição da obra |
| Título | `String` | Busca e exibição |
| Autor | `String` | Busca e catalogação |
| Editora | `String` | Catalogação |
| Ano de publicação | `int` | Identifica a edição |
| Gênero / categoria | `String` | Organização do acervo |
| Quantidade de exemplares | `int` | Controle de estoque |
| Disponível | `boolean` | Indica se pode ser emprestado |

### 2.2 Por que a classe `Livro` é uma abstração?

Uma **abstração** consiste em representar um objeto do mundo real mantendo **apenas as características e comportamentos importantes para o problema**, ignorando os demais detalhes.

Um livro físico tem cor da capa, peso, número de páginas, cheiro, tipo de papel, estado de conservação e muito mais. Para o sistema da biblioteca, porém, só interessam informações como título, autor, ISBN e se está disponível. A classe `Livro` captura esse **modelo simplificado**, e quem usa a classe não precisa saber como o empréstimo é controlado internamente, apenas chamar métodos como `emprestar()`.

Além disso, o que é relevante muda conforme o contexto: para um sistema de livraria, o preço seria essencial; para a biblioteca, não. Isso mostra que a abstração depende do objetivo do sistema.

### 2.3 Métodos que fariam sentido na classe `Livro`

```java
public class Livro {
    private String isbn;
    private String titulo;
    private String autor;
    private int quantidade;
    private boolean disponivel;

    // 1. Registra o empréstimo, se houver exemplar disponível
    public boolean emprestar() {
        if (quantidade > 0) {
            quantidade--;
            disponivel = quantidade > 0;
            return true;
        }
        return false;
    }

    // 2. Registra a devolução e atualiza a disponibilidade
    public void devolver() {
        quantidade++;
        disponivel = true;
    }

    // 3. Informa se há exemplar disponível
    public boolean estaDisponivel() {
        return disponivel;
    }

    // 4. Exibe os dados do livro
    public void exibirInfo() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("ISBN: " + isbn);
        System.out.println("Exemplares: " + quantidade);
        System.out.println("Disponível: " + (disponivel ? "Sim" : "Não"));
    }
}
```

**Resumo dos métodos:**

1. `emprestar()`: diminui a quantidade de exemplares e atualiza a disponibilidade.
2. `devolver()`: aumenta a quantidade e marca o livro como disponível.
3. `estaDisponivel()`: retorna se o livro pode ser emprestado.
4. `exibirInfo()`: mostra os dados do livro no console.

Outras opções que também fariam sentido: `reservar()`, `atualizarQuantidade(int)` e `toString()`.
