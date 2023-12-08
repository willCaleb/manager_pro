package com.project.pro.enums;

public enum EnumCustomException {
    CPF_INVALIDO("O CPF informado {0} é inválido."),
    CATEGORIA_CATEGORIA_PRINCIPAL_DIFERENTE("A Categoria superior pertence à outra categoria principal."),
    IMGUR_IMPOSSIVEL_GERAR_TOKEN("Não foi possível gerar um Token em Imgur: {0}"),
    IMGUR_IMPOSSIVEL_RECUPERAR_CONTA("Não foi possível recuoerar os dados da conta: {0}"),
    IMGUR_IMPOSSIVEL_FAZER_UPLOAD("Não foi possível fazer o upload da imagem: {0}"),
    IMGUR_IMPOSSIVEL_CONVERTER_ARQUIVO("Não foi possível converter o arquivo: {0}"),

    PEDIDO_NAO_E_POSSIVEL_FINALIZAR("O pedido não pode ser finalizados pois contém itens não finalizados!"),
    PEDIDO_FINALIZADO("Pedido já finalizado"),
    OBJETOS_CLASSES_DIFERENTES("Objetos de classes diferentes!");

    private final String message;

    EnumCustomException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
