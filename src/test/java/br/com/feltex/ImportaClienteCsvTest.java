package br.com.feltex;


import br.com.feltex.exception.CabecalhoInvalidoException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ImportaClienteCsvTest {

    @Test
    void lerArquivoClientes() throws Exception {

        var inputArquivo = lerArquivoDeTeste("clientes.csv");
        var clientes = ImportaClienteCsv.lerArquivo(inputArquivo);

        assertAll(
                () -> assertEquals(3, clientes.size()),
                () -> assertEquals("José da Silva", clientes.get(0).getNome()),
                () -> assertEquals("jose@feltex.com.br", clientes.get(0).getEmail()),
                () -> assertEquals("219992-5555", clientes.get(0).getTelefone()),
                () -> assertEquals("Joana dos Santos", clientes.get(2).getNome())
        );
        inputArquivo.close();
    }

    @Test
    void arquivoComLinhaInvalida() throws Exception {
        var inputArquivo = lerArquivoDeTeste("clientesComLinhaInvalida.csv");
        var clientes = ImportaClienteCsv.lerArquivo(inputArquivo);

        assertAll(
                () -> assertEquals(2, clientes.size()),
                () -> assertEquals("José da Silva", clientes.get(0).getNome()),
                () -> assertEquals("jose@feltex.com.br", clientes.get(0).getEmail()),
                () -> assertEquals("219992-5555", clientes.get(0).getTelefone()),
                () -> assertEquals("Joana dos Santos", clientes.get(1).getNome())
        );
        inputArquivo.close();
    }

    @Test
    void arquivoSemCabecalho() throws Exception {
        var inputArquivo = lerArquivoDeTeste("clientesSemCabecalho.csv");
        var excecaoEsperada = assertThrows(CabecalhoInvalidoException.class,
                () -> ImportaClienteCsv.lerArquivo(inputArquivo));
        assertEquals("outro nome aqui;email@feltex.com.br;123654", excecaoEsperada.getMessage());
        inputArquivo.close();
    }

    @Test
    void arquivoComSeparadorInvalido() throws Exception {
        var inputArquivo = lerArquivoDeTeste("clientesComSeparadorInvalido.csv");
        var clientes = ImportaClienteCsv.lerArquivo(inputArquivo);

        assertAll(
                () -> assertEquals(1, clientes.size()),
                () -> assertEquals("Maria da Silva", clientes.get(0).getNome()),
                () -> assertEquals("maria@feltex.com.br", clientes.get(0).getEmail()),
               () -> assertEquals("218882-4444", clientes.get(0).getTelefone())

        );
        inputArquivo.close();
    }


    private InputStream lerArquivoDeTeste(final String nomeArquivo) throws Exception {
        var pathArquivo = ClassLoader.getSystemResource(nomeArquivo).toURI().getPath();
        return new FileInputStream(pathArquivo);
    }
}