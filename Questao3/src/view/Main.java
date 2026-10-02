import model.Produto;

void main(){
    Produto p = new Produto(1, "Teclado", 150.0, 20);
    p.exibirInfo();

    p.setPreco(-10);
    p.setPreco(129.9);

    p.exibirInfo();
}